import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IGraphicElement, GraphicElement } from '../graphic-element.model';
import { GraphicElementService } from '../service/graphic-element.service';

@Component({
  selector: 'jhi-graphic-element-update',
  templateUrl: './graphic-element-update.component.html',
})
export class GraphicElementUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    code: [],
  });

  constructor(
    protected graphicElementService: GraphicElementService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ graphicElement }) => {
      this.updateForm(graphicElement);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const graphicElement = this.createFromForm();
    if (graphicElement.id !== undefined) {
      this.subscribeToSaveResponse(this.graphicElementService.update(graphicElement));
    } else {
      this.subscribeToSaveResponse(this.graphicElementService.create(graphicElement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGraphicElement>>): void {
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

  protected updateForm(graphicElement: IGraphicElement): void {
    this.editForm.patchValue({
      id: graphicElement.id,
      name: graphicElement.name,
      code: graphicElement.code,
    });
  }

  protected createFromForm(): IGraphicElement {
    return {
      ...new GraphicElement(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
    };
  }
}
