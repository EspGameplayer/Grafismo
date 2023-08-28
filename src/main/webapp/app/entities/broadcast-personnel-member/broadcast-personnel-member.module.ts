import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BroadcastPersonnelMemberComponent } from './list/broadcast-personnel-member.component';
import { BroadcastPersonnelMemberDetailComponent } from './detail/broadcast-personnel-member-detail.component';
import { BroadcastPersonnelMemberUpdateComponent } from './update/broadcast-personnel-member-update.component';
import { BroadcastPersonnelMemberDeleteDialogComponent } from './delete/broadcast-personnel-member-delete-dialog.component';
import { BroadcastPersonnelMemberRoutingModule } from './route/broadcast-personnel-member-routing.module';

@NgModule({
  imports: [SharedModule, BroadcastPersonnelMemberRoutingModule],
  declarations: [
    BroadcastPersonnelMemberComponent,
    BroadcastPersonnelMemberDetailComponent,
    BroadcastPersonnelMemberUpdateComponent,
    BroadcastPersonnelMemberDeleteDialogComponent,
  ],
  entryComponents: [BroadcastPersonnelMemberDeleteDialogComponent],
})
export class BroadcastPersonnelMemberModule {}
