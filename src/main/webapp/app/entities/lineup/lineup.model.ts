import { IMatchPlayer } from 'app/entities/match-player/match-player.model';
import { ITeamStaffMember } from 'app/entities/team-staff-member/team-staff-member.model';
import { IFormation } from 'app/entities/formation/formation.model';
import { IMatch } from 'app/entities/match/match.model';

export interface ILineup {
  id?: number;
  details?: string | null;
  miscData?: string | null;
  captain?: IMatchPlayer | null;
  dt?: ITeamStaffMember | null;
  dt2?: ITeamStaffMember | null;
  teamDelegate?: ITeamStaffMember | null;
  formation?: IFormation | null;
  matchPlayers?: IMatchPlayer[] | null;
  homeMatch?: IMatch;
  awayMatch?: IMatch;
}

export class Lineup implements ILineup {
  constructor(
    public id?: number,
    public details?: string | null,
    public miscData?: string | null,
    public captain?: IMatchPlayer | null,
    public dt?: ITeamStaffMember | null,
    public dt2?: ITeamStaffMember | null,
    public teamDelegate?: ITeamStaffMember | null,
    public formation?: IFormation | null,
    public matchPlayers?: IMatchPlayer[] | null,
    public homeMatch?: IMatch,
    public awayMatch?: IMatch
  ) {}
}

export function getLineupIdentifier(lineup: ILineup): number | undefined {
  return lineup.id;
}
