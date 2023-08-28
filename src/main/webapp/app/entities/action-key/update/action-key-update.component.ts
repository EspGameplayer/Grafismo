import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IActionKey, ActionKey } from '../action-key.model';
import { ActionKeyService } from '../service/action-key.service';
import { IGraphicElement } from 'app/entities/graphic-element/graphic-element.model';
import { GraphicElementService } from 'app/entities/graphic-element/service/graphic-element.service';

@Component({
  selector: 'jhi-action-key-update',
  templateUrl: './action-key-update.component.html',
})
export class ActionKeyUpdateComponent implements OnInit {
  isSaving = false;

  graphicElementsCollection: IGraphicElement[] = [];

  editForm = this.fb.group({
    id: [],
    action: [null, [Validators.required]],
    keys: [null, [Validators.required]],
    graphicElement: [null, Validators.required],
  });

  constructor(
    protected actionKeyService: ActionKeyService,
    protected graphicElementService: GraphicElementService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actionKey }) => {
      this.updateForm(actionKey);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const actionKey = this.createFromForm();
    if (actionKey.id !== undefined) {
      this.subscribeToSaveResponse(this.actionKeyService.update(actionKey));
    } else {
      this.subscribeToSaveResponse(this.actionKeyService.create(actionKey));
    }
  }

  trackGraphicElementById(_index: number, item: IGraphicElement): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActionKey>>): void {
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

  protected updateForm(actionKey: IActionKey): void {
    this.editForm.patchValue({
      id: actionKey.id,
      action: actionKey.action,
      keys: actionKey.keys,
      graphicElement: actionKey.graphicElement,
    });

    this.graphicElementsCollection = this.graphicElementService.addGraphicElementToCollectionIfMissing(
      this.graphicElementsCollection,
      actionKey.graphicElement
    );
  }

  protected loadRelationshipsOptions(): void {
    this.graphicElementService
      .query({ filter: 'keys-is-null' })
      .pipe(map((res: HttpResponse<IGraphicElement[]>) => res.body ?? []))
      .pipe(
        map((graphicElements: IGraphicElement[]) =>
          this.graphicElementService.addGraphicElementToCollectionIfMissing(graphicElements, this.editForm.get('graphicElement')!.value)
        )
      )
      .subscribe((graphicElements: IGraphicElement[]) => (this.graphicElementsCollection = graphicElements));
  }

  protected createFromForm(): IActionKey {
    return {
      ...new ActionKey(),
      id: this.editForm.get(['id'])!.value,
      action: this.editForm.get(['action'])!.value,
      keys: this.editForm.get(['keys'])!.value,
      graphicElement: this.editForm.get(['graphicElement'])!.value,
    };
  }
}
