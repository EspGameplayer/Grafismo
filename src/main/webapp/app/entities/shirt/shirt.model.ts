import { ITeam } from 'app/entities/team/team.model';
import { ISeason } from 'app/entities/season/season.model';

export interface IShirt {
  id?: number;
  colour1?: string | null;
  colour2?: string | null;
  type?: number | null;
  modelContentType?: string | null;
  model?: string | null;
  photoContentType?: string | null;
  photo?: string | null;
  team?: ITeam | null;
  season?: ISeason | null;
}

export class Shirt implements IShirt {
  constructor(
    public id?: number,
    public colour1?: string | null,
    public colour2?: string | null,
    public type?: number | null,
    public modelContentType?: string | null,
    public model?: string | null,
    public photoContentType?: string | null,
    public photo?: string | null,
    public team?: ITeam | null,
    public season?: ISeason | null
  ) {}
}

export function getShirtIdentifier(shirt: IShirt): number | undefined {
  return shirt.id;
}
