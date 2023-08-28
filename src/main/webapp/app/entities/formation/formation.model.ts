export interface IFormation {
  id?: number;
  graphicsName?: string;
  detailedName?: string | null;
  distribution?: string | null;
}

export class Formation implements IFormation {
  constructor(public id?: number, public graphicsName?: string, public detailedName?: string | null, public distribution?: string | null) {}
}

export function getFormationIdentifier(formation: IFormation): number | undefined {
  return formation.id;
}
