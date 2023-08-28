import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TemplateFormationComponent } from './list/template-formation.component';
import { TemplateFormationDetailComponent } from './detail/template-formation-detail.component';
import { TemplateFormationUpdateComponent } from './update/template-formation-update.component';
import { TemplateFormationDeleteDialogComponent } from './delete/template-formation-delete-dialog.component';
import { TemplateFormationRoutingModule } from './route/template-formation-routing.module';

@NgModule({
  imports: [SharedModule, TemplateFormationRoutingModule],
  declarations: [
    TemplateFormationComponent,
    TemplateFormationDetailComponent,
    TemplateFormationUpdateComponent,
    TemplateFormationDeleteDialogComponent,
  ],
  entryComponents: [TemplateFormationDeleteDialogComponent],
})
export class TemplateFormationModule {}
