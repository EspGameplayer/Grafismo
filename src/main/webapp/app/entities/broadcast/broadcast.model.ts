import { IMatch } from 'app/entities/match/match.model';
import { ISystemConfiguration } from 'app/entities/system-configuration/system-configuration.model';
import { IBroadcastPersonnelMember } from 'app/entities/broadcast-personnel-member/broadcast-personnel-member.model';

export interface IBroadcast {
  id?: number;
  miscData?: string | null;
  match?: IMatch;
  systemConfiguration?: ISystemConfiguration;
  broadcastPersonnelMembers?: IBroadcastPersonnelMember[] | null;
}

export class Broadcast implements IBroadcast {
  constructor(
    public id?: number,
    public miscData?: string | null,
    public match?: IMatch,
    public systemConfiguration?: ISystemConfiguration,
    public broadcastPersonnelMembers?: IBroadcastPersonnelMember[] | null
  ) {}
}

export function getBroadcastIdentifier(broadcast: IBroadcast): number | undefined {
  return broadcast.id;
}
