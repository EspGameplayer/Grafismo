import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MatchActionComponent } from './list/match-action.component';
import { MatchActionDetailComponent } from './detail/match-action-detail.component';
import { MatchActionUpdateComponent } from './update/match-action-update.component';
import { MatchActionDeleteDialogComponent } from './delete/match-action-delete-dialog.component';
import { MatchActionRoutingModule } from './route/match-action-routing.module';

@NgModule({
  imports: [SharedModule, MatchActionRoutingModule],
  declarations: [MatchActionComponent, MatchActionDetailComponent, MatchActionUpdateComponent, MatchActionDeleteDialogComponent],
  entryComponents: [MatchActionDeleteDialogComponent],
})
export class MatchActionModule {}
