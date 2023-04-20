import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BroadcastPersonnelMemberComponent } from '../list/broadcast-personnel-member.component';
import { BroadcastPersonnelMemberDetailComponent } from '../detail/broadcast-personnel-member-detail.component';
import { BroadcastPersonnelMemberUpdateComponent } from '../update/broadcast-personnel-member-update.component';
import { BroadcastPersonnelMemberRoutingResolveService } from './broadcast-personnel-member-routing-resolve.service';

const broadcastPersonnelMemberRoute: Routes = [
  {
    path: '',
    component: BroadcastPersonnelMemberComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BroadcastPersonnelMemberDetailComponent,
    resolve: {
      broadcastPersonnelMember: BroadcastPersonnelMemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BroadcastPersonnelMemberUpdateComponent,
    resolve: {
      broadcastPersonnelMember: BroadcastPersonnelMemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BroadcastPersonnelMemberUpdateComponent,
    resolve: {
      broadcastPersonnelMember: BroadcastPersonnelMemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(broadcastPersonnelMemberRoute)],
  exports: [RouterModule],
})
export class BroadcastPersonnelMemberRoutingModule {}
