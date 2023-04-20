import dayjs from 'dayjs/esm';
import { IPlayer } from 'app/entities/player/player.model';
import { IMatch } from 'app/entities/match/match.model';

export interface IInjury {
  id?: number;
  moment?: dayjs.Dayjs | null;
  estHealingTime?: string | null;
  estComebackDate?: dayjs.Dayjs | null;
  reason?: string | null;
  player?: IPlayer;
  match?: IMatch | null;
}

export class Injury implements IInjury {
  constructor(
    public id?: number,
    public moment?: dayjs.Dayjs | null,
    public estHealingTime?: string | null,
    public estComebackDate?: dayjs.Dayjs | null,
    public reason?: string | null,
    public player?: IPlayer,
    public match?: IMatch | null
  ) {}
}

export function getInjuryIdentifier(injury: IInjury): number | undefined {
  return injury.id;
}
