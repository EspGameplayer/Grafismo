import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITeam, Team } from '../team.model';
import { TeamService } from '../service/team.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';
import { IVenue } from 'app/entities/venue/venue.model';
import { VenueService } from 'app/entities/venue/service/venue.service';

@Component({
  selector: 'jhi-team-update',
  templateUrl: './team-update.component.html',
})
export class TeamUpdateComponent implements OnInit {
  isSaving = false;

  teamsSharedCollection: ITeam[] = [];
  formationsSharedCollection: IFormation[] = [];
  locationsSharedCollection: ILocation[] = [];
  venuesSharedCollection: IVenue[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    abb: [null, [Validators.required]],
    graphicsName: [],
    shortName: [],
    nicknames: [],
    establishmentDate: [],
    badge: [],
    badgeContentType: [],
    monocBadge: [],
    monocBadgeContentType: [],
    details: [],
    miscData: [],
    parent: [],
    preferredFormation: [],
    location: [],
    venues: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected teamService: TeamService,
    protected formationService: FormationService,
    protected locationService: LocationService,
    protected venueService: VenueService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ team }) => {
      this.updateForm(team);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('grafismoApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const team = this.createFromForm();
    if (team.id !== undefined) {
      this.subscribeToSaveResponse(this.teamService.update(team));
    } else {
      this.subscribeToSaveResponse(this.teamService.create(team));
    }
  }

  trackTeamById(_index: number, item: ITeam): number {
    return item.id!;
  }

  trackFormationById(_index: number, item: IFormation): number {
    return item.id!;
  }

  trackLocationById(_index: number, item: ILocation): number {
    return item.id!;
  }

  trackVenueById(_index: number, item: IVenue): number {
    return item.id!;
  }

  getSelectedVenue(option: IVenue, selectedVals?: IVenue[]): IVenue {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeam>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(team: ITeam): void {
    this.editForm.patchValue({
      id: team.id,
      name: team.name,
      abb: team.abb,
      graphicsName: team.graphicsName,
      shortName: team.shortName,
      nicknames: team.nicknames,
      establishmentDate: team.establishmentDate,
      badge: team.badge,
      badgeContentType: team.badgeContentType,
      monocBadge: team.monocBadge,
      monocBadgeContentType: team.monocBadgeContentType,
      details: team.details,
      miscData: team.miscData,
      parent: team.parent,
      preferredFormation: team.preferredFormation,
      location: team.location,
      venues: team.venues,
    });

    this.teamsSharedCollection = this.teamService.addTeamToCollectionIfMissing(this.teamsSharedCollection, team.parent);
    this.formationsSharedCollection = this.formationService.addFormationToCollectionIfMissing(
      this.formationsSharedCollection,
      team.preferredFormation
    );
    this.locationsSharedCollection = this.locationService.addLocationToCollectionIfMissing(this.locationsSharedCollection, team.location);
    this.venuesSharedCollection = this.venueService.addVenueToCollectionIfMissing(this.venuesSharedCollection, ...(team.venues ?? []));
  }

  protected loadRelationshipsOptions(): void {
    this.teamService
      .query()
      .pipe(map((res: HttpResponse<ITeam[]>) => res.body ?? []))
      .pipe(map((teams: ITeam[]) => this.teamService.addTeamToCollectionIfMissing(teams, this.editForm.get('parent')!.value)))
      .subscribe((teams: ITeam[]) => (this.teamsSharedCollection = teams));

    this.formationService
      .query()
      .pipe(map((res: HttpResponse<IFormation[]>) => res.body ?? []))
      .pipe(
        map((formations: IFormation[]) =>
          this.formationService.addFormationToCollectionIfMissing(formations, this.editForm.get('preferredFormation')!.value)
        )
      )
      .subscribe((formations: IFormation[]) => (this.formationsSharedCollection = formations));

    this.locationService
      .query()
      .pipe(map((res: HttpResponse<ILocation[]>) => res.body ?? []))
      .pipe(
        map((locations: ILocation[]) =>
          this.locationService.addLocationToCollectionIfMissing(locations, this.editForm.get('location')!.value)
        )
      )
      .subscribe((locations: ILocation[]) => (this.locationsSharedCollection = locations));

    this.venueService
      .query()
      .pipe(map((res: HttpResponse<IVenue[]>) => res.body ?? []))
      .pipe(
        map((venues: IVenue[]) => this.venueService.addVenueToCollectionIfMissing(venues, ...(this.editForm.get('venues')!.value ?? [])))
      )
      .subscribe((venues: IVenue[]) => (this.venuesSharedCollection = venues));
  }

  protected createFromForm(): ITeam {
    return {
      ...new Team(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      abb: this.editForm.get(['abb'])!.value,
      graphicsName: this.editForm.get(['graphicsName'])!.value,
      shortName: this.editForm.get(['shortName'])!.value,
      nicknames: this.editForm.get(['nicknames'])!.value,
      establishmentDate: this.editForm.get(['establishmentDate'])!.value,
      badgeContentType: this.editForm.get(['badgeContentType'])!.value,
      badge: this.editForm.get(['badge'])!.value,
      monocBadgeContentType: this.editForm.get(['monocBadgeContentType'])!.value,
      monocBadge: this.editForm.get(['monocBadge'])!.value,
      details: this.editForm.get(['details'])!.value,
      miscData: this.editForm.get(['miscData'])!.value,
      parent: this.editForm.get(['parent'])!.value,
      preferredFormation: this.editForm.get(['preferredFormation'])!.value,
      location: this.editForm.get(['location'])!.value,
      venues: this.editForm.get(['venues'])!.value,
    };
  }
}
