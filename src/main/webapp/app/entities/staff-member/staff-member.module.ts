import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StaffMemberComponent } from './list/staff-member.component';
import { StaffMemberDetailComponent } from './detail/staff-member-detail.component';
import { StaffMemberUpdateComponent } from './update/staff-member-update.component';
import { StaffMemberDeleteDialogComponent } from './delete/staff-member-delete-dialog.component';
import { StaffMemberRoutingModule } from './route/staff-member-routing.module';

@NgModule({
  imports: [SharedModule, StaffMemberRoutingModule],
  declarations: [StaffMemberComponent, StaffMemberDetailComponent, StaffMemberUpdateComponent, StaffMemberDeleteDialogComponent],
  entryComponents: [StaffMemberDeleteDialogComponent],
})
export class StaffMemberModule {}
