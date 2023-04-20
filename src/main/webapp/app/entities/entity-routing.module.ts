import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'team',
        data: { pageTitle: 'grafismoApp.team.home.title' },
        loadChildren: () => import('./team/team.module').then(m => m.TeamModule),
      },
      {
        path: 'person',
        data: { pageTitle: 'grafismoApp.person.home.title' },
        loadChildren: () => import('./person/person.module').then(m => m.PersonModule),
      },
      {
        path: 'player',
        data: { pageTitle: 'grafismoApp.player.home.title' },
        loadChildren: () => import('./player/player.module').then(m => m.PlayerModule),
      },
      {
        path: 'staff-member',
        data: { pageTitle: 'grafismoApp.staffMember.home.title' },
        loadChildren: () => import('./staff-member/staff-member.module').then(m => m.StaffMemberModule),
      },
      {
        path: 'referee',
        data: { pageTitle: 'grafismoApp.referee.home.title' },
        loadChildren: () => import('./referee/referee.module').then(m => m.RefereeModule),
      },
      {
        path: 'broadcast-personnel-member',
        data: { pageTitle: 'grafismoApp.broadcastPersonnelMember.home.title' },
        loadChildren: () =>
          import('./broadcast-personnel-member/broadcast-personnel-member.module').then(m => m.BroadcastPersonnelMemberModule),
      },
      {
        path: 'association',
        data: { pageTitle: 'grafismoApp.association.home.title' },
        loadChildren: () => import('./association/association.module').then(m => m.AssociationModule),
      },
      {
        path: 'venue',
        data: { pageTitle: 'grafismoApp.venue.home.title' },
        loadChildren: () => import('./venue/venue.module').then(m => m.VenueModule),
      },
      {
        path: 'location',
        data: { pageTitle: 'grafismoApp.location.home.title' },
        loadChildren: () => import('./location/location.module').then(m => m.LocationModule),
      },
      {
        path: 'shirt',
        data: { pageTitle: 'grafismoApp.shirt.home.title' },
        loadChildren: () => import('./shirt/shirt.module').then(m => m.ShirtModule),
      },
      {
        path: 'match',
        data: { pageTitle: 'grafismoApp.match.home.title' },
        loadChildren: () => import('./match/match.module').then(m => m.MatchModule),
      },
      {
        path: 'match-player',
        data: { pageTitle: 'grafismoApp.matchPlayer.home.title' },
        loadChildren: () => import('./match-player/match-player.module').then(m => m.MatchPlayerModule),
      },
      {
        path: 'lineup',
        data: { pageTitle: 'grafismoApp.lineup.home.title' },
        loadChildren: () => import('./lineup/lineup.module').then(m => m.LineupModule),
      },
      {
        path: 'formation',
        data: { pageTitle: 'grafismoApp.formation.home.title' },
        loadChildren: () => import('./formation/formation.module').then(m => m.FormationModule),
      },
      {
        path: 'template-formation',
        data: { pageTitle: 'grafismoApp.templateFormation.home.title' },
        loadChildren: () => import('./template-formation/template-formation.module').then(m => m.TemplateFormationModule),
      },
      {
        path: 'team-player',
        data: { pageTitle: 'grafismoApp.teamPlayer.home.title' },
        loadChildren: () => import('./team-player/team-player.module').then(m => m.TeamPlayerModule),
      },
      {
        path: 'team-staff-member',
        data: { pageTitle: 'grafismoApp.teamStaffMember.home.title' },
        loadChildren: () => import('./team-staff-member/team-staff-member.module').then(m => m.TeamStaffMemberModule),
      },
      {
        path: 'match-action',
        data: { pageTitle: 'grafismoApp.matchAction.home.title' },
        loadChildren: () => import('./match-action/match-action.module').then(m => m.MatchActionModule),
      },
      {
        path: 'sponsor',
        data: { pageTitle: 'grafismoApp.sponsor.home.title' },
        loadChildren: () => import('./sponsor/sponsor.module').then(m => m.SponsorModule),
      },
      {
        path: 'competition',
        data: { pageTitle: 'grafismoApp.competition.home.title' },
        loadChildren: () => import('./competition/competition.module').then(m => m.CompetitionModule),
      },
      {
        path: 'matchday',
        data: { pageTitle: 'grafismoApp.matchday.home.title' },
        loadChildren: () => import('./matchday/matchday.module').then(m => m.MatchdayModule),
      },
      {
        path: 'deduction',
        data: { pageTitle: 'grafismoApp.deduction.home.title' },
        loadChildren: () => import('./deduction/deduction.module').then(m => m.DeductionModule),
      },
      {
        path: 'suspension',
        data: { pageTitle: 'grafismoApp.suspension.home.title' },
        loadChildren: () => import('./suspension/suspension.module').then(m => m.SuspensionModule),
      },
      {
        path: 'injury',
        data: { pageTitle: 'grafismoApp.injury.home.title' },
        loadChildren: () => import('./injury/injury.module').then(m => m.InjuryModule),
      },
      {
        path: 'season',
        data: { pageTitle: 'grafismoApp.season.home.title' },
        loadChildren: () => import('./season/season.module').then(m => m.SeasonModule),
      },
      {
        path: 'system-configuration',
        data: { pageTitle: 'grafismoApp.systemConfiguration.home.title' },
        loadChildren: () => import('./system-configuration/system-configuration.module').then(m => m.SystemConfigurationModule),
      },
      {
        path: 'action-key',
        data: { pageTitle: 'grafismoApp.actionKey.home.title' },
        loadChildren: () => import('./action-key/action-key.module').then(m => m.ActionKeyModule),
      },
      {
        path: 'position',
        data: { pageTitle: 'grafismoApp.position.home.title' },
        loadChildren: () => import('./position/position.module').then(m => m.PositionModule),
      },
      {
        path: 'country',
        data: { pageTitle: 'grafismoApp.country.home.title' },
        loadChildren: () => import('./country/country.module').then(m => m.CountryModule),
      },
      {
        path: 'graphic-element',
        data: { pageTitle: 'grafismoApp.graphicElement.home.title' },
        loadChildren: () => import('./graphic-element/graphic-element.module').then(m => m.GraphicElementModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
