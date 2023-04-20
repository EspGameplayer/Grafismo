import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StaffMemberComponent } from '../list/staff-member.component';
import { StaffMemberDetailComponent } from '../detail/staff-member-detail.component';
import { StaffMemberUpdateComponent } from '../update/staff-member-update.component';
import { StaffMemberRoutingResolveService } from './staff-member-routing-resolve.service';

const staffMemberRoute: Routes = [
  {
    path: '',
    component: StaffMemberComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StaffMemberDetailComponent,
    resolve: {
      staffMember: StaffMemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StaffMemberUpdateComponent,
    resolve: {
      staffMember: StaffMemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StaffMemberUpdateComponent,
    resolve: {
      staffMember: StaffMemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(staffMemberRoute)],
  exports: [RouterModule],
})
export class StaffMemberRoutingModule {}
