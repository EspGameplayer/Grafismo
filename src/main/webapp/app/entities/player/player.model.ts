import { IPerson } from 'app/entities/person/person.model';
import { IPosition } from 'app/entities/position/position.model';
import { Side } from 'app/entities/enumerations/side.model';

export interface IPlayer {
  id?: number;
  graphicsName?: string;
  longGraphicsName?: string | null;
  shirtName?: string | null;
  height?: number | null;
  weight?: number | null;
  strongerFoot?: Side | null;
  preferredSide?: Side | null;
  contractUntil?: string | null;
  retirementDate?: string | null;
  miscData?: string | null;
  person?: IPerson;
  positions?: IPosition[] | null;
}

export class Player implements IPlayer {
  constructor(
    public id?: number,
    public graphicsName?: string,
    public longGraphicsName?: string | null,
    public shirtName?: string | null,
    public height?: number | null,
    public weight?: number | null,
    public strongerFoot?: Side | null,
    public preferredSide?: Side | null,
    public contractUntil?: string | null,
    public retirementDate?: string | null,
    public miscData?: string | null,
    public person?: IPerson,
    public positions?: IPosition[] | null
  ) {}
}

export function getPlayerIdentifier(player: IPlayer): number | undefined {
  return player.id;
}
