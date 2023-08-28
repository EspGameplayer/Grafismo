import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BroadcastComponent } from './list/broadcast.component';
import { BroadcastDetailComponent } from './detail/broadcast-detail.component';
import { BroadcastUpdateComponent } from './update/broadcast-update.component';
import { BroadcastDeleteDialogComponent } from './delete/broadcast-delete-dialog.component';
import { BroadcastRoutingModule } from './route/broadcast-routing.module';

@NgModule({
  imports: [SharedModule, BroadcastRoutingModule],
  declarations: [BroadcastComponent, BroadcastDetailComponent, BroadcastUpdateComponent, BroadcastDeleteDialogComponent],
  entryComponents: [BroadcastDeleteDialogComponent],
})
export class BroadcastModule {}
