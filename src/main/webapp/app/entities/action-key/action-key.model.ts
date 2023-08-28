import { IGraphicElement } from 'app/entities/graphic-element/graphic-element.model';

export interface IActionKey {
  id?: number;
  action?: string;
  keys?: string;
  graphicElement?: IGraphicElement;
}

export class ActionKey implements IActionKey {
  constructor(public id?: number, public action?: string, public keys?: string, public graphicElement?: IGraphicElement) {}
}

export function getActionKeyIdentifier(actionKey: IActionKey): number | undefined {
  return actionKey.id;
}
