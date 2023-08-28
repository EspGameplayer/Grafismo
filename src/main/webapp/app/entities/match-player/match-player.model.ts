import { ITeamPlayer } from 'app/entities/team-player/team-player.model';
import { IPosition } from 'app/entities/position/position.model';
import { IMatch } from 'app/entities/match/match.model';
import { ILineup } from 'app/entities/lineup/lineup.model';
import { IMatchAction } from 'app/entities/match-action/match-action.model';

export interface IMatchPlayer {
  id?: number;
  shirtNumber?: number | null;
  isYouth?: number | null;
  isWarned?: number | null;
  miscData?: string | null;
  teamPlayer?: ITeamPlayer;
  position?: IPosition | null;
  motmMatch?: IMatch;
  captainLineup?: ILineup | null;
  lineups?: ILineup[] | null;
  actions?: IMatchAction[] | null;
}

export class MatchPlayer implements IMatchPlayer {
  constructor(
    public id?: number,
    public shirtNumber?: number | null,
    public isYouth?: number | null,
    public isWarned?: number | null,
    public miscData?: string | null,
    public teamPlayer?: ITeamPlayer,
    public position?: IPosition | null,
    public motmMatch?: IMatch,
    public captainLineup?: ILineup | null,
    public lineups?: ILineup[] | null,
    public actions?: IMatchAction[] | null
  ) {}
}

export function getMatchPlayerIdentifier(matchPlayer: IMatchPlayer): number | undefined {
  return matchPlayer.id;
}
