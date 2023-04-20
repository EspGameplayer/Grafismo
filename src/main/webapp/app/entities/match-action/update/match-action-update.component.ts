import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMatchAction, MatchAction } from '../match-action.model';
import { MatchActionService } from '../service/match-action.service';
import { IMatch } from 'app/entities/match/match.model';
import { MatchService } from 'app/entities/match/service/match.service';
import { IMatchPlayer } from 'app/entities/match-player/match-player.model';
import { MatchPlayerService } from 'app/entities/match-player/service/match-player.service';

@Component({
  selector: 'jhi-match-action-update',
  templateUrl: './match-action-update.component.html',
})
export class MatchActionUpdateComponent implements OnInit {
  isSaving = false;

  matchesSharedCollection: IMatch[] = [];
  matchPlayersSharedCollection: IMatchPlayer[] = [];

  editForm = this.fb.group({
    id: [],
    timestamp: [],
    details: [],
    miscData: [],
    match: [null, Validators.required],
    matchPlayers: [],
  });

  constructor(
    protected matchActionService: MatchActionService,
    protected matchService: MatchService,
    protected matchPlayerService: MatchPlayerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matchAction }) => {
      this.updateForm(matchAction);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const matchAction = this.createFromForm();
    if (matchAction.id !== undefined) {
      this.subscribeToSaveResponse(this.matchActionService.update(matchAction));
    } else {
      this.subscribeToSaveResponse(this.matchActionService.create(matchAction));
    }
  }

  trackMatchById(_index: number, item: IMatch): number {
    return item.id!;
  }

  trackMatchPlayerById(_index: number, item: IMatchPlayer): number {
    return item.id!;
  }

  getSelectedMatchPlayer(option: IMatchPlayer, selectedVals?: IMatchPlayer[]): IMatchPlayer {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatchAction>>): void {
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

  protected updateForm(matchAction: IMatchAction): void {
    this.editForm.patchValue({
      id: matchAction.id,
      timestamp: matchAction.timestamp,
      details: matchAction.details,
      miscData: matchAction.miscData,
      match: matchAction.match,
      matchPlayers: matchAction.matchPlayers,
    });

    this.matchesSharedCollection = this.matchService.addMatchToCollectionIfMissing(this.matchesSharedCollection, matchAction.match);
    this.matchPlayersSharedCollection = this.matchPlayerService.addMatchPlayerToCollectionIfMissing(
      this.matchPlayersSharedCollection,
      ...(matchAction.matchPlayers ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.matchService
      .query()
      .pipe(map((res: HttpResponse<IMatch[]>) => res.body ?? []))
      .pipe(map((matches: IMatch[]) => this.matchService.addMatchToCollectionIfMissing(matches, this.editForm.get('match')!.value)))
      .subscribe((matches: IMatch[]) => (this.matchesSharedCollection = matches));

    this.matchPlayerService
      .query()
      .pipe(map((res: HttpResponse<IMatchPlayer[]>) => res.body ?? []))
      .pipe(
        map((matchPlayers: IMatchPlayer[]) =>
          this.matchPlayerService.addMatchPlayerToCollectionIfMissing(matchPlayers, ...(this.editForm.get('matchPlayers')!.value ?? []))
        )
      )
      .subscribe((matchPlayers: IMatchPlayer[]) => (this.matchPlayersSharedCollection = matchPlayers));
  }

  protected createFromForm(): IMatchAction {
    return {
      ...new MatchAction(),
      id: this.editForm.get(['id'])!.value,
      timestamp: this.editForm.get(['timestamp'])!.value,
      details: this.editForm.get(['details'])!.value,
      miscData: this.editForm.get(['miscData'])!.value,
      match: this.editForm.get(['match'])!.value,
      matchPlayers: this.editForm.get(['matchPlayers'])!.value,
    };
  }
}
