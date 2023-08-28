import dayjs from 'dayjs/esm';
import { ITeam } from 'app/entities/team/team.model';
import { ICompetition } from 'app/entities/competition/competition.model';
import { IMatch } from 'app/entities/match/match.model';

export interface IDeduction {
  id?: number;
  points?: number | null;
  moment?: dayjs.Dayjs | null;
  reason?: string | null;
  team?: ITeam;
  competition?: ICompetition;
  match?: IMatch | null;
}

export class Deduction implements IDeduction {
  constructor(
    public id?: number,
    public points?: number | null,
    public moment?: dayjs.Dayjs | null,
    public reason?: string | null,
    public team?: ITeam,
    public competition?: ICompetition,
    public match?: IMatch | null
  ) {}
}

export function getDeductionIdentifier(deduction: IDeduction): number | undefined {
  return deduction.id;
}
