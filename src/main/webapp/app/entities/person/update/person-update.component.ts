import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPerson, Person } from '../person.model';
import { PersonService } from '../service/person.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICountry } from 'app/entities/country/country.model';
import { CountryService } from 'app/entities/country/service/country.service';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';

@Component({
  selector: 'jhi-person-update',
  templateUrl: './person-update.component.html',
})
export class PersonUpdateComponent implements OnInit {
  isSaving = false;

  countriesSharedCollection: ICountry[] = [];
  locationsSharedCollection: ILocation[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    middleName: [],
    surname1: [],
    surname2: [],
    nicknames: [],
    graphicsName: [null, [Validators.required]],
    longGraphicsName: [],
    callnames: [],
    birthDate: [],
    deathDate: [],
    mediumShotPhoto: [],
    mediumShotPhotoContentType: [],
    fullShotPhoto: [],
    fullShotPhotoContentType: [],
    socialMedia: [],
    details: [],
    miscData: [],
    nationality: [],
    birthplace: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected personService: PersonService,
    protected countryService: CountryService,
    protected locationService: LocationService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ person }) => {
      this.updateForm(person);

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
    const person = this.createFromForm();
    if (person.id !== undefined) {
      this.subscribeToSaveResponse(this.personService.update(person));
    } else {
      this.subscribeToSaveResponse(this.personService.create(person));
    }
  }

  trackCountryById(_index: number, item: ICountry): number {
    return item.id!;
  }

  trackLocationById(_index: number, item: ILocation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerson>>): void {
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

  protected updateForm(person: IPerson): void {
    this.editForm.patchValue({
      id: person.id,
      name: person.name,
      middleName: person.middleName,
      surname1: person.surname1,
      surname2: person.surname2,
      nicknames: person.nicknames,
      graphicsName: person.graphicsName,
      longGraphicsName: person.longGraphicsName,
      callnames: person.callnames,
      birthDate: person.birthDate,
      deathDate: person.deathDate,
      mediumShotPhoto: person.mediumShotPhoto,
      mediumShotPhotoContentType: person.mediumShotPhotoContentType,
      fullShotPhoto: person.fullShotPhoto,
      fullShotPhotoContentType: person.fullShotPhotoContentType,
      socialMedia: person.socialMedia,
      details: person.details,
      miscData: person.miscData,
      nationality: person.nationality,
      birthplace: person.birthplace,
    });

    this.countriesSharedCollection = this.countryService.addCountryToCollectionIfMissing(
      this.countriesSharedCollection,
      person.nationality
    );
    this.locationsSharedCollection = this.locationService.addLocationToCollectionIfMissing(
      this.locationsSharedCollection,
      person.birthplace
    );
  }

  protected loadRelationshipsOptions(): void {
    this.countryService
      .query()
      .pipe(map((res: HttpResponse<ICountry[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountry[]) =>
          this.countryService.addCountryToCollectionIfMissing(countries, this.editForm.get('nationality')!.value)
        )
      )
      .subscribe((countries: ICountry[]) => (this.countriesSharedCollection = countries));

    this.locationService
      .query()
      .pipe(map((res: HttpResponse<ILocation[]>) => res.body ?? []))
      .pipe(
        map((locations: ILocation[]) =>
          this.locationService.addLocationToCollectionIfMissing(locations, this.editForm.get('birthplace')!.value)
        )
      )
      .subscribe((locations: ILocation[]) => (this.locationsSharedCollection = locations));
  }

  protected createFromForm(): IPerson {
    return {
      ...new Person(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      surname1: this.editForm.get(['surname1'])!.value,
      surname2: this.editForm.get(['surname2'])!.value,
      nicknames: this.editForm.get(['nicknames'])!.value,
      graphicsName: this.editForm.get(['graphicsName'])!.value,
      longGraphicsName: this.editForm.get(['longGraphicsName'])!.value,
      callnames: this.editForm.get(['callnames'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value,
      deathDate: this.editForm.get(['deathDate'])!.value,
      mediumShotPhotoContentType: this.editForm.get(['mediumShotPhotoContentType'])!.value,
      mediumShotPhoto: this.editForm.get(['mediumShotPhoto'])!.value,
      fullShotPhotoContentType: this.editForm.get(['fullShotPhotoContentType'])!.value,
      fullShotPhoto: this.editForm.get(['fullShotPhoto'])!.value,
      socialMedia: this.editForm.get(['socialMedia'])!.value,
      details: this.editForm.get(['details'])!.value,
      miscData: this.editForm.get(['miscData'])!.value,
      nationality: this.editForm.get(['nationality'])!.value,
      birthplace: this.editForm.get(['birthplace'])!.value,
    };
  }
}
