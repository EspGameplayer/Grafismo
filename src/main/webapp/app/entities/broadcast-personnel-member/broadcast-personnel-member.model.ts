import { IPerson } from 'app/entities/person/person.model';
import { BroadcastPersonnelMemberRole } from 'app/entities/enumerations/broadcast-personnel-member-role.model';

export interface IBroadcastPersonnelMember {
  id?: number;
  role?: BroadcastPersonnelMemberRole | null;
  person?: IPerson;
}

export class BroadcastPersonnelMember implements IBroadcastPersonnelMember {
  constructor(public id?: number, public role?: BroadcastPersonnelMemberRole | null, public person?: IPerson) {}
}

export function getBroadcastPersonnelMemberIdentifier(broadcastPersonnelMember: IBroadcastPersonnelMember): number | undefined {
  return broadcastPersonnelMember.id;
}
