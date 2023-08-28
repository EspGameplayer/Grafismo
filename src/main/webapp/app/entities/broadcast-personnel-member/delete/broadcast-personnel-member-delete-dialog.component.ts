import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBroadcastPersonnelMember } from '../broadcast-personnel-member.model';
import { BroadcastPersonnelMemberService } from '../service/broadcast-personnel-member.service';

@Component({
  templateUrl: './broadcast-personnel-member-delete-dialog.component.html',
})
export class BroadcastPersonnelMemberDeleteDialogComponent {
  broadcastPersonnelMember?: IBroadcastPersonnelMember;

  constructor(protected broadcastPersonnelMemberService: BroadcastPersonnelMemberService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.broadcastPersonnelMemberService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
