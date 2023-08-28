import dayjs from 'dayjs/esm';
import { IMatchPlayer } from 'app/entities/match-player/match-player.model';
import { ILineup } from 'app/entities/lineup/lineup.model';
import { ITeam } from 'app/entities/team/team.model';
import { IVenue } from 'app/entities/venue/venue.model';
import { ITeamStaffMember } from 'app/entities/team-staff-member/team-staff-member.model';
import { IShirt } from 'app/entities/shirt/shirt.model';
import { IMatchday } from 'app/entities/matchday/matchday.model';
import { IReferee } from 'app/entities/referee/referee.model';
import { IDeduction } from 'app/entities/deduction/deduction.model';
import { ISuspension } from 'app/entities/suspension/suspension.model';
import { IInjury } from 'app/entities/injury/injury.model';

export interface IMatch {
  id?: number;
  moment?: dayjs.Dayjs | null;
  attendance?: number | null;
  hashtag?: string | null;
  details?: string | null;
  miscData?: string | null;
  motm?: IMatchPlayer | null;
  homeLineup?: ILineup | null;
  awayLineup?: ILineup | null;
  homeTeam?: ITeam;
  awayTeam?: ITeam;
  venue?: IVenue | null;
  matchDelegate?: ITeamStaffMember | null;
  homeShirt?: IShirt | null;
  awayShirt?: IShirt | null;
  matchday?: IMatchday | null;
  referees?: IReferee[] | null;
  deductions?: IDeduction[] | null;
  suspensions?: ISuspension[] | null;
  injuries?: IInjury[] | null;
}

export class Match implements IMatch {
  constructor(
    public id?: number,
    public moment?: dayjs.Dayjs | null,
    public attendance?: number | null,
    public hashtag?: string | null,
    public details?: string | null,
    public miscData?: string | null,
    public motm?: IMatchPlayer | null,
    public homeLineup?: ILineup | null,
    public awayLineup?: ILineup | null,
    public homeTeam?: ITeam,
    public awayTeam?: ITeam,
    public venue?: IVenue | null,
    public matchDelegate?: ITeamStaffMember | null,
    public homeShirt?: IShirt | null,
    public awayShirt?: IShirt | null,
    public matchday?: IMatchday | null,
    public referees?: IReferee[] | null,
    public deductions?: IDeduction[] | null,
    public suspensions?: ISuspension[] | null,
    public injuries?: IInjury[] | null
  ) {}
}

export function getMatchIdentifier(match: IMatch): number | undefined {
  return match.id;
}
