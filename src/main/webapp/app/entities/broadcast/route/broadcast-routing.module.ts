import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BroadcastComponent } from '../list/broadcast.component';
import { BroadcastDetailComponent } from '../detail/broadcast-detail.component';
import { BroadcastUpdateComponent } from '../update/broadcast-update.component';
import { BroadcastRoutingResolveService } from './broadcast-routing-resolve.service';

const broadcastRoute: Routes = [
  {
    path: '',
    component: BroadcastComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BroadcastDetailComponent,
    resolve: {
      broadcast: BroadcastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BroadcastUpdateComponent,
    resolve: {
      broadcast: BroadcastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BroadcastUpdateComponent,
    resolve: {
      broadcast: BroadcastRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(broadcastRoute)],
  exports: [RouterModule],
})
export class BroadcastRoutingModule {}
