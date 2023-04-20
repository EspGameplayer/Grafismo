import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStaffMember } from '../staff-member.model';
import { StaffMemberService } from '../service/staff-member.service';

@Component({
  templateUrl: './staff-member-delete-dialog.component.html',
})
export class StaffMemberDeleteDialogComponent {
  staffMember?: IStaffMember;

  constructor(protected staffMemberService: StaffMemberService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.staffMemberService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
