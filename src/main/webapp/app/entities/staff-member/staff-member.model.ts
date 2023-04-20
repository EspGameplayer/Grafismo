import { IPerson } from 'app/entities/person/person.model';
import { StaffMemberRole } from 'app/entities/enumerations/staff-member-role.model';

export interface IStaffMember {
  id?: number;
  defaultRole?: StaffMemberRole | null;
  contractUntil?: string | null;
  retirementDate?: string | null;
  miscData?: string | null;
  person?: IPerson;
}

export class StaffMember implements IStaffMember {
  constructor(
    public id?: number,
    public defaultRole?: StaffMemberRole | null,
    public contractUntil?: string | null,
    public retirementDate?: string | null,
    public miscData?: string | null,
    public person?: IPerson
  ) {}
}

export function getStaffMemberIdentifier(staffMember: IStaffMember): number | undefined {
  return staffMember.id;
}
