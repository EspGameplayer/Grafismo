import dayjs from 'dayjs/esm';
import { ITeam } from 'app/entities/team/team.model';
import { IPlayer } from 'app/entities/player/player.model';

export interface ITeamPlayer {
  id?: number;
  preferredShirtNumber?: number | null;
  isYouth?: number | null;
  sinceDate?: dayjs.Dayjs | null;
  untilDate?: dayjs.Dayjs | null;
  miscData?: string | null;
  team?: ITeam | null;
  player?: IPlayer;
}

export class TeamPlayer implements ITeamPlayer {
  constructor(
    public id?: number,
    public preferredShirtNumber?: number | null,
    public isYouth?: number | null,
    public sinceDate?: dayjs.Dayjs | null,
    public untilDate?: dayjs.Dayjs | null,
    public miscData?: string | null,
    public team?: ITeam | null,
    public player?: IPlayer
  ) {}
}

export function getTeamPlayerIdentifier(teamPlayer: ITeamPlayer): number | undefined {
  return teamPlayer.id;
}
