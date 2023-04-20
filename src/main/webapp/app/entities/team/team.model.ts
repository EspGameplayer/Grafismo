import { IFormation } from 'app/entities/formation/formation.model';
import { ILocation } from 'app/entities/location/location.model';
import { IVenue } from 'app/entities/venue/venue.model';
import { ICompetition } from 'app/entities/competition/competition.model';

export interface ITeam {
  id?: number;
  name?: string;
  abb?: string;
  graphicsName?: string | null;
  shortName?: string | null;
  nicknames?: string | null;
  establishmentDate?: string | null;
  badgeContentType?: string | null;
  badge?: string | null;
  monocBadgeContentType?: string | null;
  monocBadge?: string | null;
  details?: string | null;
  miscData?: string | null;
  parent?: ITeam | null;
  preferredFormation?: IFormation | null;
  location?: ILocation | null;
  venues?: IVenue[] | null;
  children?: ITeam[] | null;
  competitions?: ICompetition[] | null;
}

export class Team implements ITeam {
  constructor(
    public id?: number,
    public name?: string,
    public abb?: string,
    public graphicsName?: string | null,
    public shortName?: string | null,
    public nicknames?: string | null,
    public establishmentDate?: string | null,
    public badgeContentType?: string | null,
    public badge?: string | null,
    public monocBadgeContentType?: string | null,
    public monocBadge?: string | null,
    public details?: string | null,
    public miscData?: string | null,
    public parent?: ITeam | null,
    public preferredFormation?: IFormation | null,
    public location?: ILocation | null,
    public venues?: IVenue[] | null,
    public children?: ITeam[] | null,
    public competitions?: ICompetition[] | null
  ) {}
}

export function getTeamIdentifier(team: ITeam): number | undefined {
  return team.id;
}
