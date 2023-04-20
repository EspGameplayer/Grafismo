import { ICountry } from 'app/entities/country/country.model';
import { ITeam } from 'app/entities/team/team.model';
import { IReferee } from 'app/entities/referee/referee.model';
import { CompetitionType } from 'app/entities/enumerations/competition-type.model';

export interface ICompetition {
  id?: number;
  name?: string;
  graphicsName?: string | null;
  type?: CompetitionType;
  colour?: string | null;
  regulations?: string | null;
  details?: string | null;
  miscData?: string | null;
  parent?: ICompetition | null;
  country?: ICountry | null;
  teams?: ITeam[] | null;
  referees?: IReferee[] | null;
  children?: ICompetition[] | null;
}

export class Competition implements ICompetition {
  constructor(
    public id?: number,
    public name?: string,
    public graphicsName?: string | null,
    public type?: CompetitionType,
    public colour?: string | null,
    public regulations?: string | null,
    public details?: string | null,
    public miscData?: string | null,
    public parent?: ICompetition | null,
    public country?: ICountry | null,
    public teams?: ITeam[] | null,
    public referees?: IReferee[] | null,
    public children?: ICompetition[] | null
  ) {}
}

export function getCompetitionIdentifier(competition: ICompetition): number | undefined {
  return competition.id;
}
