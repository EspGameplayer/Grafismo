import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITeamPlayer } from '../team-player.model';
import { TeamPlayerService } from '../service/team-player.service';

@Component({
  templateUrl: './team-player-delete-dialog.component.html',
})
export class TeamPlayerDeleteDialogComponent {
  teamPlayer?: ITeamPlayer;

  constructor(protected teamPlayerService: TeamPlayerService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.teamPlayerService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
