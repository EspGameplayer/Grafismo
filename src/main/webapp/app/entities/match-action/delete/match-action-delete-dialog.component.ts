import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMatchAction } from '../match-action.model';
import { MatchActionService } from '../service/match-action.service';

@Component({
  templateUrl: './match-action-delete-dialog.component.html',
})
export class MatchActionDeleteDialogComponent {
  matchAction?: IMatchAction;

  constructor(protected matchActionService: MatchActionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.matchActionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
