import { IPlayer } from 'app/entities/player/player.model';

export interface IPosition {
  id?: number;
  name?: string;
  abb?: string;
  parents?: IPosition[] | null;
  children?: IPosition[] | null;
  players?: IPlayer[] | null;
}

export class Position implements IPosition {
  constructor(
    public id?: number,
    public name?: string,
    public abb?: string,
    public parents?: IPosition[] | null,
    public children?: IPosition[] | null,
    public players?: IPlayer[] | null
  ) {}
}

export function getPositionIdentifier(position: IPosition): number | undefined {
  return position.id;
}
