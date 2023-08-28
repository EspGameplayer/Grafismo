import { IPerson } from 'app/entities/person/person.model';
import { IBroadcast } from 'app/entities/broadcast/broadcast.model';
import { BroadcastPersonnelMemberRole } from 'app/entities/enumerations/broadcast-personnel-member-role.model';

export interface IBroadcastPersonnelMember {
  id?: number;
  graphicsName?: string;
  longGraphicsName?: string | null;
  role?: BroadcastPersonnelMemberRole | null;
  person?: IPerson;
  broadcasts?: IBroadcast[] | null;
}

export class BroadcastPersonnelMember implements IBroadcastPersonnelMember {
  constructor(
    public id?: number,
    public graphicsName?: string,
    public longGraphicsName?: string | null,
    public role?: BroadcastPersonnelMemberRole | null,
    public person?: IPerson,
    public broadcasts?: IBroadcast[] | null
  ) {}
}

export function getBroadcastPersonnelMemberIdentifier(broadcastPersonnelMember: IBroadcastPersonnelMember): number | undefined {
  return broadcastPersonnelMember.id;
}
