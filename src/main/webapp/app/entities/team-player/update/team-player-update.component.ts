import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITeamPlayer, TeamPlayer } from '../team-player.model';
import { TeamPlayerService } from '../service/team-player.service';
import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';
import { IPlayer } from 'app/entities/player/player.model';
import { PlayerService } from 'app/entities/player/service/player.service';

@Component({
  selector: 'jhi-team-player-update',
  templateUrl: './team-player-update.component.html',
})
export class TeamPlayerUpdateComponent implements OnInit {
  isSaving = false;

  teamsSharedCollection: ITeam[] = [];
  playersSharedCollection: IPlayer[] = [];

  editForm = this.fb.group({
    id: [],
    preferredShirtNumber: [],
    isYouth: [],
    sinceDate: [],
    untilDate: [],
    miscData: [],
    team: [],
    player: [null, Validators.required],
  });

  constructor(
    protected teamPlayerService: TeamPlayerService,
    protected teamService: TeamService,
    protected playerService: PlayerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teamPlayer }) => {
      this.updateForm(teamPlayer);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const teamPlayer = this.createFromForm();
    if (teamPlayer.id !== undefined) {
      this.subscribeToSaveResponse(this.teamPlayerService.update(teamPlayer));
    } else {
      this.subscribeToSaveResponse(this.teamPlayerService.create(teamPlayer));
    }
  }

  trackTeamById(_index: number, item: ITeam): number {
    return item.id!;
  }

  trackPlayerById(_index: number, item: IPlayer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeamPlayer>>): void {
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

  protected updateForm(teamPlayer: ITeamPlayer): void {
    this.editForm.patchValue({
      id: teamPlayer.id,
      preferredShirtNumber: teamPlayer.preferredShirtNumber,
      isYouth: teamPlayer.isYouth,
      sinceDate: teamPlayer.sinceDate,
      untilDate: teamPlayer.untilDate,
      miscData: teamPlayer.miscData,
      team: teamPlayer.team,
      player: teamPlayer.player,
    });

    this.teamsSharedCollection = this.teamService.addTeamToCollectionIfMissing(this.teamsSharedCollection, teamPlayer.team);
    this.playersSharedCollection = this.playerService.addPlayerToCollectionIfMissing(this.playersSharedCollection, teamPlayer.player);
  }

  protected loadRelationshipsOptions(): void {
    this.teamService
      .query()
      .pipe(map((res: HttpResponse<ITeam[]>) => res.body ?? []))
      .pipe(map((teams: ITeam[]) => this.teamService.addTeamToCollectionIfMissing(teams, this.editForm.get('team')!.value)))
      .subscribe((teams: ITeam[]) => (this.teamsSharedCollection = teams));

    this.playerService
      .query()
      .pipe(map((res: HttpResponse<IPlayer[]>) => res.body ?? []))
      .pipe(map((players: IPlayer[]) => this.playerService.addPlayerToCollectionIfMissing(players, this.editForm.get('player')!.value)))
      .subscribe((players: IPlayer[]) => (this.playersSharedCollection = players));
  }

  protected createFromForm(): ITeamPlayer {
    return {
      ...new TeamPlayer(),
      id: this.editForm.get(['id'])!.value,
      preferredShirtNumber: this.editForm.get(['preferredShirtNumber'])!.value,
      isYouth: this.editForm.get(['isYouth'])!.value,
      sinceDate: this.editForm.get(['sinceDate'])!.value,
      untilDate: this.editForm.get(['untilDate'])!.value,
      miscData: this.editForm.get(['miscData'])!.value,
      team: this.editForm.get(['team'])!.value,
      player: this.editForm.get(['player'])!.value,
    };
  }
}
