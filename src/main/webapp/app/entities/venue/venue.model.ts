import { ILocation } from 'app/entities/location/location.model';
import { ITeam } from 'app/entities/team/team.model';

export interface IVenue {
  id?: number;
  name?: string;
  graphicsName?: string | null;
  longGraphicsName?: string | null;
  capacity?: number | null;
  openingDate?: string | null;
  fieldSize?: string | null;
  isArtificialGrass?: number | null;
  address?: string | null;
  geographicLocation?: string | null;
  details?: string | null;
  miscData?: string | null;
  location?: ILocation | null;
  teams?: ITeam[] | null;
}

export class Venue implements IVenue {
  constructor(
    public id?: number,
    public name?: string,
    public graphicsName?: string | null,
    public longGraphicsName?: string | null,
    public capacity?: number | null,
    public openingDate?: string | null,
    public fieldSize?: string | null,
    public isArtificialGrass?: number | null,
    public address?: string | null,
    public geographicLocation?: string | null,
    public details?: string | null,
    public miscData?: string | null,
    public location?: ILocation | null,
    public teams?: ITeam[] | null
  ) {}
}

export function getVenueIdentifier(venue: IVenue): number | undefined {
  return venue.id;
}
