import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITemplateFormation } from '../template-formation.model';
import { TemplateFormationService } from '../service/template-formation.service';

@Component({
  templateUrl: './template-formation-delete-dialog.component.html',
})
export class TemplateFormationDeleteDialogComponent {
  templateFormation?: ITemplateFormation;

  constructor(protected templateFormationService: TemplateFormationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.templateFormationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
