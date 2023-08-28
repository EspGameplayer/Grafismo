import { IMatch } from 'app/entities/match/match.model';
import { IMatchPlayer } from 'app/entities/match-player/match-player.model';

export interface IMatchAction {
  id?: number;
  timestamp?: string | null;
  details?: string | null;
  miscData?: string | null;
  match?: IMatch;
  matchPlayers?: IMatchPlayer[] | null;
}

export class MatchAction implements IMatchAction {
  constructor(
    public id?: number,
    public timestamp?: string | null,
    public details?: string | null,
    public miscData?: string | null,
    public match?: IMatch,
    public matchPlayers?: IMatchPlayer[] | null
  ) {}
}

export function getMatchActionIdentifier(matchAction: IMatchAction): number | undefined {
  return matchAction.id;
}
