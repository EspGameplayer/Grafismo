import { IFormation } from 'app/entities/formation/formation.model';

export interface ITemplateFormation {
  id?: number;
  name?: string;
  formation?: IFormation;
}

export class TemplateFormation implements ITemplateFormation {
  constructor(public id?: number, public name?: string, public formation?: IFormation) {}
}

export function getTemplateFormationIdentifier(templateFormation: ITemplateFormation): number | undefined {
  return templateFormation.id;
}
