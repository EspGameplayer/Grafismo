import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MatchActionComponent } from '../list/match-action.component';
import { MatchActionDetailComponent } from '../detail/match-action-detail.component';
import { MatchActionUpdateComponent } from '../update/match-action-update.component';
import { MatchActionRoutingResolveService } from './match-action-routing-resolve.service';

const matchActionRoute: Routes = [
  {
    path: '',
    component: MatchActionComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MatchActionDetailComponent,
    resolve: {
      matchAction: MatchActionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MatchActionUpdateComponent,
    resolve: {
      matchAction: MatchActionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MatchActionUpdateComponent,
    resolve: {
      matchAction: MatchActionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(matchActionRoute)],
  exports: [RouterModule],
})
export class MatchActionRoutingModule {}
