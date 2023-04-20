import { ISeason } from 'app/entities/season/season.model';

export interface ISystemConfiguration {
  id?: number;
  miscData?: string | null;
  currentSeason?: ISeason | null;
}

export class SystemConfiguration implements ISystemConfiguration {
  constructor(public id?: number, public miscData?: string | null, public currentSeason?: ISeason | null) {}
}

export function getSystemConfigurationIdentifier(systemConfiguration: ISystemConfiguration): number | undefined {
  return systemConfiguration.id;
}
