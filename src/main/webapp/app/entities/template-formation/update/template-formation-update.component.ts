import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITemplateFormation, TemplateFormation } from '../template-formation.model';
import { TemplateFormationService } from '../service/template-formation.service';
import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';

@Component({
  selector: 'jhi-template-formation-update',
  templateUrl: './template-formation-update.component.html',
})
export class TemplateFormationUpdateComponent implements OnInit {
  isSaving = false;

  formationsCollection: IFormation[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    formation: [null, Validators.required],
  });

  constructor(
    protected templateFormationService: TemplateFormationService,
    protected formationService: FormationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ templateFormation }) => {
      this.updateForm(templateFormation);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const templateFormation = this.createFromForm();
    if (templateFormation.id !== undefined) {
      this.subscribeToSaveResponse(this.templateFormationService.update(templateFormation));
    } else {
      this.subscribeToSaveResponse(this.templateFormationService.create(templateFormation));
    }
  }

  trackFormationById(_index: number, item: IFormation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITemplateFormation>>): void {
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

  protected updateForm(templateFormation: ITemplateFormation): void {
    this.editForm.patchValue({
      id: templateFormation.id,
      name: templateFormation.name,
      formation: templateFormation.formation,
    });

    this.formationsCollection = this.formationService.addFormationToCollectionIfMissing(
      this.formationsCollection,
      templateFormation.formation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.formationService
      .query({ filter: 'templateformation-is-null' })
      .pipe(map((res: HttpResponse<IFormation[]>) => res.body ?? []))
      .pipe(
        map((formations: IFormation[]) =>
          this.formationService.addFormationToCollectionIfMissing(formations, this.editForm.get('formation')!.value)
        )
      )
      .subscribe((formations: IFormation[]) => (this.formationsCollection = formations));
  }

  protected createFromForm(): ITemplateFormation {
    return {
      ...new TemplateFormation(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      formation: this.editForm.get(['formation'])!.value,
    };
  }
}
