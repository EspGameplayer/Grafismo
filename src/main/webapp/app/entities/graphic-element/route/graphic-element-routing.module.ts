import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GraphicElementComponent } from '../list/graphic-element.component';
import { GraphicElementDetailComponent } from '../detail/graphic-element-detail.component';
import { GraphicElementUpdateComponent } from '../update/graphic-element-update.component';
import { GraphicElementRoutingResolveService } from './graphic-element-routing-resolve.service';

const graphicElementRoute: Routes = [
  {
    path: '',
    component: GraphicElementComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GraphicElementDetailComponent,
    resolve: {
      graphicElement: GraphicElementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GraphicElementUpdateComponent,
    resolve: {
      graphicElement: GraphicElementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GraphicElementUpdateComponent,
    resolve: {
      graphicElement: GraphicElementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(graphicElementRoute)],
  exports: [RouterModule],
})
export class GraphicElementRoutingModule {}
