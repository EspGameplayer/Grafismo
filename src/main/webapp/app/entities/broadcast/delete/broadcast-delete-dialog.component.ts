import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBroadcast } from '../broadcast.model';
import { BroadcastService } from '../service/broadcast.service';

@Component({
  templateUrl: './broadcast-delete-dialog.component.html',
})
export class BroadcastDeleteDialogComponent {
  broadcast?: IBroadcast;

  constructor(protected broadcastService: BroadcastService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.broadcastService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
