import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGraphicElement } from '../graphic-element.model';
import { GraphicElementService } from '../service/graphic-element.service';

@Component({
  templateUrl: './graphic-element-delete-dialog.component.html',
})
export class GraphicElementDeleteDialogComponent {
  graphicElement?: IGraphicElement;

  constructor(protected graphicElementService: GraphicElementService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.graphicElementService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
