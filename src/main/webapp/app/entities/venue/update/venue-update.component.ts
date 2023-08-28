import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVenue, Venue } from '../venue.model';
import { VenueService } from '../service/venue.service';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';

@Component({
  selector: 'jhi-venue-update',
  templateUrl: './venue-update.component.html',
})
export class VenueUpdateComponent implements OnInit {
  isSaving = false;

  locationsSharedCollection: ILocation[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    graphicsName: [],
    longGraphicsName: [],
    capacity: [],
    openingDate: [],
    fieldSize: [],
    isArtificialGrass: [],
    address: [],
    geographicLocation: [],
    details: [],
    miscData: [],
    location: [],
  });

  constructor(
    protected venueService: VenueService,
    protected locationService: LocationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ venue }) => {
      this.updateForm(venue);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const venue = this.createFromForm();
    if (venue.id !== undefined) {
      this.subscribeToSaveResponse(this.venueService.update(venue));
    } else {
      this.subscribeToSaveResponse(this.venueService.create(venue));
    }
  }

  trackLocationById(_index: number, item: ILocation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenue>>): void {
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

  protected updateForm(venue: IVenue): void {
    this.editForm.patchValue({
      id: venue.id,
      name: venue.name,
      graphicsName: venue.graphicsName,
      longGraphicsName: venue.longGraphicsName,
      capacity: venue.capacity,
      openingDate: venue.openingDate,
      fieldSize: venue.fieldSize,
      isArtificialGrass: venue.isArtificialGrass,
      address: venue.address,
      geographicLocation: venue.geographicLocation,
      details: venue.details,
      miscData: venue.miscData,
      location: venue.location,
    });

    this.locationsSharedCollection = this.locationService.addLocationToCollectionIfMissing(this.locationsSharedCollection, venue.location);
  }

  protected loadRelationshipsOptions(): void {
    this.locationService
      .query()
      .pipe(map((res: HttpResponse<ILocation[]>) => res.body ?? []))
      .pipe(
        map((locations: ILocation[]) =>
          this.locationService.addLocationToCollectionIfMissing(locations, this.editForm.get('location')!.value)
        )
      )
      .subscribe((locations: ILocation[]) => (this.locationsSharedCollection = locations));
  }

  protected createFromForm(): IVenue {
    return {
      ...new Venue(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      graphicsName: this.editForm.get(['graphicsName'])!.value,
      longGraphicsName: this.editForm.get(['longGraphicsName'])!.value,
      capacity: this.editForm.get(['capacity'])!.value,
      openingDate: this.editForm.get(['openingDate'])!.value,
      fieldSize: this.editForm.get(['fieldSize'])!.value,
      isArtificialGrass: this.editForm.get(['isArtificialGrass'])!.value,
      address: this.editForm.get(['address'])!.value,
      geographicLocation: this.editForm.get(['geographicLocation'])!.value,
      details: this.editForm.get(['details'])!.value,
      miscData: this.editForm.get(['miscData'])!.value,
      location: this.editForm.get(['location'])!.value,
    };
  }
}
