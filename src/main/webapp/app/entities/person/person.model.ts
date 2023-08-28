import { ICountry } from 'app/entities/country/country.model';
import { ILocation } from 'app/entities/location/location.model';

export interface IPerson {
  id?: number;
  name?: string | null;
  middleName?: string | null;
  surname1?: string | null;
  surname2?: string | null;
  nicknames?: string | null;
  graphicsName?: string;
  longGraphicsName?: string | null;
  callnames?: string | null;
  birthDate?: string | null;
  deathDate?: string | null;
  mediumShotPhotoContentType?: string | null;
  mediumShotPhoto?: string | null;
  fullShotPhotoContentType?: string | null;
  fullShotPhoto?: string | null;
  socialMedia?: string | null;
  details?: string | null;
  miscData?: string | null;
  nationality?: ICountry | null;
  birthplace?: ILocation | null;
}

export class Person implements IPerson {
  constructor(
    public id?: number,
    public name?: string | null,
    public middleName?: string | null,
    public surname1?: string | null,
    public surname2?: string | null,
    public nicknames?: string | null,
    public graphicsName?: string,
    public longGraphicsName?: string | null,
    public callnames?: string | null,
    public birthDate?: string | null,
    public deathDate?: string | null,
    public mediumShotPhotoContentType?: string | null,
    public mediumShotPhoto?: string | null,
    public fullShotPhotoContentType?: string | null,
    public fullShotPhoto?: string | null,
    public socialMedia?: string | null,
    public details?: string | null,
    public miscData?: string | null,
    public nationality?: ICountry | null,
    public birthplace?: ILocation | null
  ) {}
}

export function getPersonIdentifier(person: IPerson): number | undefined {
  return person.id;
}
