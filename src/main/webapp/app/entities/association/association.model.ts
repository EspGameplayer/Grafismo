export interface IAssociation {
  id?: number;
  info?: string;
}

export class Association implements IAssociation {
  constructor(public id?: number, public info?: string) {}
}

export function getAssociationIdentifier(association: IAssociation): number | undefined {
  return association.id;
}
