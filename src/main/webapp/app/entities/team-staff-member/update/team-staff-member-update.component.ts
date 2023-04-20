import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITeamStaffMember, TeamStaffMember } from '../team-staff-member.model';
import { TeamStaffMemberService } from '../service/team-staff-member.service';
import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';
import { IStaffMember } from 'app/entities/staff-member/staff-member.model';
import { StaffMemberService } from 'app/entities/staff-member/service/staff-member.service';
import { StaffMemberRole } from 'app/entities/enumerations/staff-member-role.model';

@Component({
  selector: 'jhi-team-staff-member-update',
  templateUrl: './team-staff-member-update.component.html',
})
export class TeamStaffMemberUpdateComponent implements OnInit {
  isSaving = false;
  staffMemberRoleValues = Object.keys(StaffMemberRole);

  teamsSharedCollection: ITeam[] = [];
  staffMembersSharedCollection: IStaffMember[] = [];

  editForm = this.fb.group({
    id: [],
    role: [],
    sinceDate: [],
    untilDate: [],
    miscData: [],
    team: [],
    staffMember: [null, Validators.required],
  });

  constructor(
    protected teamStaffMemberService: TeamStaffMemberService,
    protected teamService: TeamService,
    protected staffMemberService: StaffMemberService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teamStaffMember }) => {
      this.updateForm(teamStaffMember);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const teamStaffMember = this.createFromForm();
    if (teamStaffMember.id !== undefined) {
      this.subscribeToSaveResponse(this.teamStaffMemberService.update(teamStaffMember));
    } else {
      this.subscribeToSaveResponse(this.teamStaffMemberService.create(teamStaffMember));
    }
  }

  trackTeamById(_index: number, item: ITeam): number {
    return item.id!;
  }

  trackStaffMemberById(_index: number, item: IStaffMember): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeamStaffMember>>): void {
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

  protected updateForm(teamStaffMember: ITeamStaffMember): void {
    this.editForm.patchValue({
      id: teamStaffMember.id,
      role: teamStaffMember.role,
      sinceDate: teamStaffMember.sinceDate,
      untilDate: teamStaffMember.untilDate,
      miscData: teamStaffMember.miscData,
      team: teamStaffMember.team,
      staffMember: teamStaffMember.staffMember,
    });

    this.teamsSharedCollection = this.teamService.addTeamToCollectionIfMissing(this.teamsSharedCollection, teamStaffMember.team);
    this.staffMembersSharedCollection = this.staffMemberService.addStaffMemberToCollectionIfMissing(
      this.staffMembersSharedCollection,
      teamStaffMember.staffMember
    );
  }

  protected loadRelationshipsOptions(): void {
    this.teamService
      .query()
      .pipe(map((res: HttpResponse<ITeam[]>) => res.body ?? []))
      .pipe(map((teams: ITeam[]) => this.teamService.addTeamToCollectionIfMissing(teams, this.editForm.get('team')!.value)))
      .subscribe((teams: ITeam[]) => (this.teamsSharedCollection = teams));

    this.staffMemberService
      .query()
      .pipe(map((res: HttpResponse<IStaffMember[]>) => res.body ?? []))
      .pipe(
        map((staffMembers: IStaffMember[]) =>
          this.staffMemberService.addStaffMemberToCollectionIfMissing(staffMembers, this.editForm.get('staffMember')!.value)
        )
      )
      .subscribe((staffMembers: IStaffMember[]) => (this.staffMembersSharedCollection = staffMembers));
  }

  protected createFromForm(): ITeamStaffMember {
    return {
      ...new TeamStaffMember(),
      id: this.editForm.get(['id'])!.value,
      role: this.editForm.get(['role'])!.value,
      sinceDate: this.editForm.get(['sinceDate'])!.value,
      untilDate: this.editForm.get(['untilDate'])!.value,
      miscData: this.editForm.get(['miscData'])!.value,
      team: this.editForm.get(['team'])!.value,
      staffMember: this.editForm.get(['staffMember'])!.value,
    };
  }
}
