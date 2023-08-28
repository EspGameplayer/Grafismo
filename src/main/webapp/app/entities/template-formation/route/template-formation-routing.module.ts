import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TemplateFormationComponent } from '../list/template-formation.component';
import { TemplateFormationDetailComponent } from '../detail/template-formation-detail.component';
import { TemplateFormationUpdateComponent } from '../update/template-formation-update.component';
import { TemplateFormationRoutingResolveService } from './template-formation-routing-resolve.service';

const templateFormationRoute: Routes = [
  {
    path: '',
    component: TemplateFormationComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TemplateFormationDetailComponent,
    resolve: {
      templateFormation: TemplateFormationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TemplateFormationUpdateComponent,
    resolve: {
      templateFormation: TemplateFormationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TemplateFormationUpdateComponent,
    resolve: {
      templateFormation: TemplateFormationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(templateFormationRoute)],
  exports: [RouterModule],
})
export class TemplateFormationRoutingModule {}
