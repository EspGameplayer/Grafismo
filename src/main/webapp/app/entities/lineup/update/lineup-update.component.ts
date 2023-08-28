import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILineup, Lineup } from '../lineup.model';
import { LineupService } from '../service/lineup.service';
import { IMatchPlayer } from 'app/entities/match-player/match-player.model';
import { MatchPlayerService } from 'app/entities/match-player/service/match-player.service';
import { ITeamStaffMember } from 'app/entities/team-staff-member/team-staff-member.model';
import { TeamStaffMemberService } from 'app/entities/team-staff-member/service/team-staff-member.service';
import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';

@Component({
  selector: 'jhi-lineup-update',
  templateUrl: './lineup-update.component.html',
})
export class LineupUpdateComponent implements OnInit {
  isSaving = false;

  matchPlayersSharedCollection: IMatchPlayer[] = [];
  captainsCollection: IMatchPlayer[] = [];
  teamStaffMembersSharedCollection: ITeamStaffMember[] = [];
  formationsSharedCollection: IFormation[] = [];

  editForm = this.fb.group({
    id: [],
    details: [],
    miscData: [],
    captain: [],
    dt: [],
    dt2: [],
    teamDelegate: [],
    formation: [],
    matchPlayers: [],
  });

  constructor(
    protected lineupService: LineupService,
    protected matchPlayerService: MatchPlayerService,
    protected teamStaffMemberService: TeamStaffMemberService,
    protected formationService: FormationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lineup }) => {
      this.updateForm(lineup);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lineup = this.createFromForm();
    if (lineup.id !== undefined) {
      this.subscribeToSaveResponse(this.lineupService.update(lineup));
    } else {
      this.subscribeToSaveResponse(this.lineupService.create(lineup));
    }
  }

  trackMatchPlayerById(_index: number, item: IMatchPlayer): number {
    return item.id!;
  }

  trackTeamStaffMemberById(_index: number, item: ITeamStaffMember): number {
    return item.id!;
  }

  trackFormationById(_index: number, item: IFormation): number {
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILineup>>): void {
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

  protected updateForm(lineup: ILineup): void {
    this.editForm.patchValue({
      id: lineup.id,
      details: lineup.details,
      miscData: lineup.miscData,
      captain: lineup.captain,
      dt: lineup.dt,
      dt2: lineup.dt2,
      teamDelegate: lineup.teamDelegate,
      formation: lineup.formation,
      matchPlayers: lineup.matchPlayers,
    });

    this.matchPlayersSharedCollection = this.matchPlayerService.addMatchPlayerToCollectionIfMissing(
      this.matchPlayersSharedCollection,
      ...(lineup.matchPlayers ?? [])
    );
    this.captainsCollection = this.matchPlayerService.addMatchPlayerToCollectionIfMissing(this.captainsCollection, lineup.captain);
    this.teamStaffMembersSharedCollection = this.teamStaffMemberService.addTeamStaffMemberToCollectionIfMissing(
      this.teamStaffMembersSharedCollection,
      lineup.dt,
      lineup.dt2,
      lineup.teamDelegate
    );
    this.formationsSharedCollection = this.formationService.addFormationToCollectionIfMissing(
      this.formationsSharedCollection,
      lineup.formation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.matchPlayerService
      .query()
      .pipe(map((res: HttpResponse<IMatchPlayer[]>) => res.body ?? []))
      .pipe(
        map((matchPlayers: IMatchPlayer[]) =>
          this.matchPlayerService.addMatchPlayerToCollectionIfMissing(matchPlayers, ...(this.editForm.get('matchPlayers')!.value ?? []))
        )
      )
      .subscribe((matchPlayers: IMatchPlayer[]) => (this.matchPlayersSharedCollection = matchPlayers));

    this.matchPlayerService
      .query({ filter: 'captainlineup-is-null' })
      .pipe(map((res: HttpResponse<IMatchPlayer[]>) => res.body ?? []))
      .pipe(
        map((matchPlayers: IMatchPlayer[]) =>
          this.matchPlayerService.addMatchPlayerToCollectionIfMissing(matchPlayers, this.editForm.get('captain')!.value)
        )
      )
      .subscribe((matchPlayers: IMatchPlayer[]) => (this.captainsCollection = matchPlayers));

    this.teamStaffMemberService
      .query()
      .pipe(map((res: HttpResponse<ITeamStaffMember[]>) => res.body ?? []))
      .pipe(
        map((teamStaffMembers: ITeamStaffMember[]) =>
          this.teamStaffMemberService.addTeamStaffMemberToCollectionIfMissing(
            teamStaffMembers,
            this.editForm.get('dt')!.value,
            this.editForm.get('dt2')!.value,
            this.editForm.get('teamDelegate')!.value
          )
        )
      )
      .subscribe((teamStaffMembers: ITeamStaffMember[]) => (this.teamStaffMembersSharedCollection = teamStaffMembers));

    this.formationService
      .query()
      .pipe(map((res: HttpResponse<IFormation[]>) => res.body ?? []))
      .pipe(
        map((formations: IFormation[]) =>
          this.formationService.addFormationToCollectionIfMissing(formations, this.editForm.get('formation')!.value)
        )
      )
      .subscribe((formations: IFormation[]) => (this.formationsSharedCollection = formations));
  }

  protected createFromForm(): ILineup {
    return {
      ...new Lineup(),
      id: this.editForm.get(['id'])!.value,
      details: this.editForm.get(['details'])!.value,
      miscData: this.editForm.get(['miscData'])!.value,
      captain: this.editForm.get(['captain'])!.value,
      dt: this.editForm.get(['dt'])!.value,
      dt2: this.editForm.get(['dt2'])!.value,
      teamDelegate: this.editForm.get(['teamDelegate'])!.value,
      formation: this.editForm.get(['formation'])!.value,
      matchPlayers: this.editForm.get(['matchPlayers'])!.value,
    };
  }
}
