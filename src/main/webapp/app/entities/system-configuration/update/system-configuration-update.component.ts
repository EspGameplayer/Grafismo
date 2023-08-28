import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISystemConfiguration, SystemConfiguration } from '../system-configuration.model';
import { SystemConfigurationService } from '../service/system-configuration.service';
import { ISeason } from 'app/entities/season/season.model';
import { SeasonService } from 'app/entities/season/service/season.service';

@Component({
  selector: 'jhi-system-configuration-update',
  templateUrl: './system-configuration-update.component.html',
})
export class SystemConfigurationUpdateComponent implements OnInit {
  isSaving = false;

  currentSeasonsCollection: ISeason[] = [];

  editForm = this.fb.group({
    id: [],
    miscData: [],
    currentSeason: [],
  });

  constructor(
    protected systemConfigurationService: SystemConfigurationService,
    protected seasonService: SeasonService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ systemConfiguration }) => {
      this.updateForm(systemConfiguration);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const systemConfiguration = this.createFromForm();
    if (systemConfiguration.id !== undefined) {
      this.subscribeToSaveResponse(this.systemConfigurationService.update(systemConfiguration));
    } else {
      this.subscribeToSaveResponse(this.systemConfigurationService.create(systemConfiguration));
    }
  }

  trackSeasonById(_index: number, item: ISeason): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISystemConfiguration>>): void {
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

  protected updateForm(systemConfiguration: ISystemConfiguration): void {
    this.editForm.patchValue({
      id: systemConfiguration.id,
      miscData: systemConfiguration.miscData,
      currentSeason: systemConfiguration.currentSeason,
    });

    this.currentSeasonsCollection = this.seasonService.addSeasonToCollectionIfMissing(
      this.currentSeasonsCollection,
      systemConfiguration.currentSeason
    );
  }

  protected loadRelationshipsOptions(): void {
    this.seasonService
      .query({ filter: 'systemconfiguration-is-null' })
      .pipe(map((res: HttpResponse<ISeason[]>) => res.body ?? []))
      .pipe(
        map((seasons: ISeason[]) => this.seasonService.addSeasonToCollectionIfMissing(seasons, this.editForm.get('currentSeason')!.value))
      )
      .subscribe((seasons: ISeason[]) => (this.currentSeasonsCollection = seasons));
  }

  protected createFromForm(): ISystemConfiguration {
    return {
      ...new SystemConfiguration(),
      id: this.editForm.get(['id'])!.value,
      miscData: this.editForm.get(['miscData'])!.value,
      currentSeason: this.editForm.get(['currentSeason'])!.value,
    };
  }
}
