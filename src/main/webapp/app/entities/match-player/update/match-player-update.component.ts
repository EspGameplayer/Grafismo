import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMatchPlayer, MatchPlayer } from '../match-player.model';
import { MatchPlayerService } from '../service/match-player.service';
import { ITeamPlayer } from 'app/entities/team-player/team-player.model';
import { TeamPlayerService } from 'app/entities/team-player/service/team-player.service';
import { IPosition } from 'app/entities/position/position.model';
import { PositionService } from 'app/entities/position/service/position.service';

@Component({
  selector: 'jhi-match-player-update',
  templateUrl: './match-player-update.component.html',
})
export class MatchPlayerUpdateComponent implements OnInit {
  isSaving = false;

  teamPlayersSharedCollection: ITeamPlayer[] = [];
  positionsSharedCollection: IPosition[] = [];

  editForm = this.fb.group({
    id: [],
    shirtNumber: [],
    isYouth: [],
    isWarned: [],
    miscData: [],
    teamPlayer: [null, Validators.required],
    position: [],
  });

  constructor(
    protected matchPlayerService: MatchPlayerService,
    protected teamPlayerService: TeamPlayerService,
    protected positionService: PositionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matchPlayer }) => {
      this.updateForm(matchPlayer);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const matchPlayer = this.createFromForm();
    if (matchPlayer.id !== undefined) {
      this.subscribeToSaveResponse(this.matchPlayerService.update(matchPlayer));
    } else {
      this.subscribeToSaveResponse(this.matchPlayerService.create(matchPlayer));
    }
  }

  trackTeamPlayerById(_index: number, item: ITeamPlayer): number {
    return item.id!;
  }

  trackPositionById(_index: number, item: IPosition): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatchPlayer>>): void {
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

  protected updateForm(matchPlayer: IMatchPlayer): void {
    this.editForm.patchValue({
      id: matchPlayer.id,
      shirtNumber: matchPlayer.shirtNumber,
      isYouth: matchPlayer.isYouth,
      isWarned: matchPlayer.isWarned,
      miscData: matchPlayer.miscData,
      teamPlayer: matchPlayer.teamPlayer,
      position: matchPlayer.position,
    });

    this.teamPlayersSharedCollection = this.teamPlayerService.addTeamPlayerToCollectionIfMissing(
      this.teamPlayersSharedCollection,
      matchPlayer.teamPlayer
    );
    this.positionsSharedCollection = this.positionService.addPositionToCollectionIfMissing(
      this.positionsSharedCollection,
      matchPlayer.position
    );
  }

  protected loadRelationshipsOptions(): void {
    this.teamPlayerService
      .query()
      .pipe(map((res: HttpResponse<ITeamPlayer[]>) => res.body ?? []))
      .pipe(
        map((teamPlayers: ITeamPlayer[]) =>
          this.teamPlayerService.addTeamPlayerToCollectionIfMissing(teamPlayers, this.editForm.get('teamPlayer')!.value)
        )
      )
      .subscribe((teamPlayers: ITeamPlayer[]) => (this.teamPlayersSharedCollection = teamPlayers));

    this.positionService
      .query()
      .pipe(map((res: HttpResponse<IPosition[]>) => res.body ?? []))
      .pipe(
        map((positions: IPosition[]) =>
          this.positionService.addPositionToCollectionIfMissing(positions, this.editForm.get('position')!.value)
        )
      )
      .subscribe((positions: IPosition[]) => (this.positionsSharedCollection = positions));
  }

  protected createFromForm(): IMatchPlayer {
    return {
      ...new MatchPlayer(),
      id: this.editForm.get(['id'])!.value,
      shirtNumber: this.editForm.get(['shirtNumber'])!.value,
      isYouth: this.editForm.get(['isYouth'])!.value,
      isWarned: this.editForm.get(['isWarned'])!.value,
      miscData: this.editForm.get(['miscData'])!.value,
      teamPlayer: this.editForm.get(['teamPlayer'])!.value,
      position: this.editForm.get(['position'])!.value,
    };
  }
}
