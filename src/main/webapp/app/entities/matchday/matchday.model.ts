import dayjs from 'dayjs/esm';
import { ICompetition } from 'app/entities/competition/competition.model';

export interface IMatchday {
  id?: number;
  name?: string;
  graphicsName?: string | null;
  moment?: dayjs.Dayjs | null;
  details?: string | null;
  miscData?: string | null;
  competition?: ICompetition | null;
}

export class Matchday implements IMatchday {
  constructor(
    public id?: number,
    public name?: string,
    public graphicsName?: string | null,
    public moment?: dayjs.Dayjs | null,
    public details?: string | null,
    public miscData?: string | null,
    public competition?: ICompetition | null
  ) {}
}

export function getMatchdayIdentifier(matchday: IMatchday): number | undefined {
  return matchday.id;
}
