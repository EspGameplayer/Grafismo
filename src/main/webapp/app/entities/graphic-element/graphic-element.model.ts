import { IActionKey } from 'app/entities/action-key/action-key.model';

export interface IGraphicElement {
  id?: number;
  name?: string;
  code?: string | null;
  keys?: IActionKey | null;
}

export class GraphicElement implements IGraphicElement {
  constructor(public id?: number, public name?: string, public code?: string | null, public keys?: IActionKey | null) {}
}

export function getGraphicElementIdentifier(graphicElement: IGraphicElement): number | undefined {
  return graphicElement.id;
}
