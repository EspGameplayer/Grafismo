import { IPerson } from 'app/entities/person/person.model';
import { IAssociation } from 'app/entities/association/association.model';
import { IMatch } from 'app/entities/match/match.model';
import { ICompetition } from 'app/entities/competition/competition.model';

export interface IReferee {
  id?: number;
  retirementDate?: string | null;
  miscData?: string | null;
  person?: IPerson;
  association?: IAssociation | null;
  matches?: IMatch[] | null;
  competitions?: ICompetition[] | null;
}

export class Referee implements IReferee {
  constructor(
    public id?: number,
    public retirementDate?: string | null,
    public miscData?: string | null,
    public person?: IPerson,
    public association?: IAssociation | null,
    public matches?: IMatch[] | null,
    public competitions?: ICompetition[] | null
  ) {}
}

export function getRefereeIdentifier(referee: IReferee): number | undefined {
  return referee.id;
}
