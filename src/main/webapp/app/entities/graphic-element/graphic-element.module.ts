import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GraphicElementComponent } from './list/graphic-element.component';
import { GraphicElementDetailComponent } from './detail/graphic-element-detail.component';
import { GraphicElementUpdateComponent } from './update/graphic-element-update.component';
import { GraphicElementDeleteDialogComponent } from './delete/graphic-element-delete-dialog.component';
import { GraphicElementRoutingModule } from './route/graphic-element-routing.module';

@NgModule({
  imports: [SharedModule, GraphicElementRoutingModule],
  declarations: [
    GraphicElementComponent,
    GraphicElementDetailComponent,
    GraphicElementUpdateComponent,
    GraphicElementDeleteDialogComponent,
  ],
  entryComponents: [GraphicElementDeleteDialogComponent],
})
export class GraphicElementModule {}
