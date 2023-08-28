import { ICountry } from 'app/entities/country/country.model';

export interface ILocation {
  id?: number;
  name?: string;
  graphicsName?: string | null;
  population?: number | null;
  censusYear?: number | null;
  denonym?: string | null;
  country?: ICountry | null;
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public name?: string,
    public graphicsName?: string | null,
    public population?: number | null,
    public censusYear?: number | null,
    public denonym?: string | null,
    public country?: ICountry | null
  ) {}
}

export function getLocationIdentifier(location: ILocation): number | undefined {
  return location.id;
}
