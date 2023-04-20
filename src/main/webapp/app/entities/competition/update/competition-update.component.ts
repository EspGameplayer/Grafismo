import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICompetition, Competition } from '../competition.model';
import { CompetitionService } from '../service/competition.service';
import { ICountry } from 'app/entities/country/country.model';
import { CountryService } from 'app/entities/country/service/country.service';
import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';
import { IReferee } from 'app/entities/referee/referee.model';
import { RefereeService } from 'app/entities/referee/service/referee.service';
import { CompetitionType } from 'app/entities/enumerations/competition-type.model';

@Component({
  selector: 'jhi-competition-update',
  templateUrl: './competition-update.component.html',
})
export class CompetitionUpdateComponent implements OnInit {
  isSaving = false;
  competitionTypeValues = Object.keys(CompetitionType);

  competitionsSharedCollection: ICompetition[] = [];
  countriesSharedCollection: ICountry[] = [];
  teamsSharedCollection: ITeam[] = [];
  refereesSharedCollection: IReferee[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    graphicsName: [],
    type: [null, [Validators.required]],
    colour: [null, [Validators.pattern('^[0-9a-fA-F]{6}$|^$')]],
    regulations: [],
    details: [],
    miscData: [],
    parent: [],
    country: [],
    teams: [],
    referees: [],
  });

  constructor(
    protected competitionService: CompetitionService,
    protected countryService: CountryService,
    protected teamService: TeamService,
    protected refereeService: RefereeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competition }) => {
      this.updateForm(competition);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const competition = this.createFromForm();
    if (competition.id !== undefined) {
      this.subscribeToSaveResponse(this.competitionService.update(competition));
    } else {
      this.subscribeToSaveResponse(this.competitionService.create(competition));
    }
  }

  trackCompetitionById(_index: number, item: ICompetition): number {
    return item.id!;
  }

  trackCountryById(_index: number, item: ICountry): number {
    return item.id!;
  }

  trackTeamById(_index: number, item: ITeam): number {
    return item.id!;
  }

  trackRefereeById(_index: number, item: IReferee): number {
    return item.id!;
  }

  getSelectedTeam(option: ITeam, selectedVals?: ITeam[]): ITeam {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  getSelectedReferee(option: IReferee, selectedVals?: IReferee[]): IReferee {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetition>>): void {
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

  protected updateForm(competition: ICompetition): void {
    this.editForm.patchValue({
      id: competition.id,
      name: competition.name,
      graphicsName: competition.graphicsName,
      type: competition.type,
      colour: competition.colour,
      regulations: competition.regulations,
      details: competition.details,
      miscData: competition.miscData,
      parent: competition.parent,
      country: competition.country,
      teams: competition.teams,
      referees: competition.referees,
    });

    this.competitionsSharedCollection = this.competitionService.addCompetitionToCollectionIfMissing(
      this.competitionsSharedCollection,
      competition.parent
    );
    this.countriesSharedCollection = this.countryService.addCountryToCollectionIfMissing(
      this.countriesSharedCollection,
      competition.country
    );
    this.teamsSharedCollection = this.teamService.addTeamToCollectionIfMissing(this.teamsSharedCollection, ...(competition.teams ?? []));
    this.refereesSharedCollection = this.refereeService.addRefereeToCollectionIfMissing(
      this.refereesSharedCollection,
      ...(competition.referees ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.competitionService
      .query()
      .pipe(map((res: HttpResponse<ICompetition[]>) => res.body ?? []))
      .pipe(
        map((competitions: ICompetition[]) =>
          this.competitionService.addCompetitionToCollectionIfMissing(competitions, this.editForm.get('parent')!.value)
        )
      )
      .subscribe((competitions: ICompetition[]) => (this.competitionsSharedCollection = competitions));

    this.countryService
      .query()
      .pipe(map((res: HttpResponse<ICountry[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountry[]) => this.countryService.addCountryToCollectionIfMissing(countries, this.editForm.get('country')!.value))
      )
      .subscribe((countries: ICountry[]) => (this.countriesSharedCollection = countries));

    this.teamService
      .query()
      .pipe(map((res: HttpResponse<ITeam[]>) => res.body ?? []))
      .pipe(map((teams: ITeam[]) => this.teamService.addTeamToCollectionIfMissing(teams, ...(this.editForm.get('teams')!.value ?? []))))
      .subscribe((teams: ITeam[]) => (this.teamsSharedCollection = teams));

    this.refereeService
      .query()
      .pipe(map((res: HttpResponse<IReferee[]>) => res.body ?? []))
      .pipe(
        map((referees: IReferee[]) =>
          this.refereeService.addRefereeToCollectionIfMissing(referees, ...(this.editForm.get('referees')!.value ?? []))
        )
      )
      .subscribe((referees: IReferee[]) => (this.refereesSharedCollection = referees));
  }

  protected createFromForm(): ICompetition {
    return {
      ...new Competition(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      graphicsName: this.editForm.get(['graphicsName'])!.value,
      type: this.editForm.get(['type'])!.value,
      colour: this.editForm.get(['colour'])!.value,
      regulations: this.editForm.get(['regulations'])!.value,
      details: this.editForm.get(['details'])!.value,
      miscData: this.editForm.get(['miscData'])!.value,
      parent: this.editForm.get(['parent'])!.value,
      country: this.editForm.get(['country'])!.value,
      teams: this.editForm.get(['teams'])!.value,
      referees: this.editForm.get(['referees'])!.value,
    };
  }
}
