import dayjs from 'dayjs/esm';
import { ITeam } from 'app/entities/team/team.model';
import { IStaffMember } from 'app/entities/staff-member/staff-member.model';
import { StaffMemberRole } from 'app/entities/enumerations/staff-member-role.model';

export interface ITeamStaffMember {
  id?: number;
  role?: StaffMemberRole | null;
  sinceDate?: dayjs.Dayjs | null;
  untilDate?: dayjs.Dayjs | null;
  miscData?: string | null;
  team?: ITeam | null;
  staffMember?: IStaffMember;
}

export class TeamStaffMember implements ITeamStaffMember {
  constructor(
    public id?: number,
    public role?: StaffMemberRole | null,
    public sinceDate?: dayjs.Dayjs | null,
    public untilDate?: dayjs.Dayjs | null,
    public miscData?: string | null,
    public team?: ITeam | null,
    public staffMember?: IStaffMember
  ) {}
}

export function getTeamStaffMemberIdentifier(teamStaffMember: ITeamStaffMember): number | undefined {
  return teamStaffMember.id;
}
