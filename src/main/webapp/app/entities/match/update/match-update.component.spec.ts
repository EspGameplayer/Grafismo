import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MatchService } from '../service/match.service';
import { IMatch, Match } from '../match.model';
import { IMatchPlayer } from 'app/entities/match-player/match-player.model';
import { MatchPlayerService } from 'app/entities/match-player/service/match-player.service';
import { ILineup } from 'app/entities/lineup/lineup.model';
import { LineupService } from 'app/entities/lineup/service/lineup.service';
import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';
import { IVenue } from 'app/entities/venue/venue.model';
import { VenueService } from 'app/entities/venue/service/venue.service';
import { ITeamStaffMember } from 'app/entities/team-staff-member/team-staff-member.model';
import { TeamStaffMemberService } from 'app/entities/team-staff-member/service/team-staff-member.service';
import { IShirt } from 'app/entities/shirt/shirt.model';
import { ShirtService } from 'app/entities/shirt/service/shirt.service';
import { IMatchday } from 'app/entities/matchday/matchday.model';
import { MatchdayService } from 'app/entities/matchday/service/matchday.service';
import { IReferee } from 'app/entities/referee/referee.model';
import { RefereeService } from 'app/entities/referee/service/referee.service';

import { MatchUpdateComponent } from './match-update.component';

describe('Match Management Update Component', () => {
  let comp: MatchUpdateComponent;
  let fixture: ComponentFixture<MatchUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let matchService: MatchService;
  let matchPlayerService: MatchPlayerService;
  let lineupService: LineupService;
  let teamService: TeamService;
  let venueService: VenueService;
  let teamStaffMemberService: TeamStaffMemberService;
  let shirtService: ShirtService;
  let matchdayService: MatchdayService;
  let refereeService: RefereeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MatchUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(MatchUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MatchUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    matchService = TestBed.inject(MatchService);
    matchPlayerService = TestBed.inject(MatchPlayerService);
    lineupService = TestBed.inject(LineupService);
    teamService = TestBed.inject(TeamService);
    venueService = TestBed.inject(VenueService);
    teamStaffMemberService = TestBed.inject(TeamStaffMemberService);
    shirtService = TestBed.inject(ShirtService);
    matchdayService = TestBed.inject(MatchdayService);
    refereeService = TestBed.inject(RefereeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call motm query and add missing value', () => {
      const match: IMatch = { id: 456 };
      const motm: IMatchPlayer = { id: 57751 };
      match.motm = motm;

      const motmCollection: IMatchPlayer[] = [{ id: 28966 }];
      jest.spyOn(matchPlayerService, 'query').mockReturnValue(of(new HttpResponse({ body: motmCollection })));
      const expectedCollection: IMatchPlayer[] = [motm, ...motmCollection];
      jest.spyOn(matchPlayerService, 'addMatchPlayerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ match });
      comp.ngOnInit();

      expect(matchPlayerService.query).toHaveBeenCalled();
      expect(matchPlayerService.addMatchPlayerToCollectionIfMissing).toHaveBeenCalledWith(motmCollection, motm);
      expect(comp.motmsCollection).toEqual(expectedCollection);
    });

    it('Should call homeLineup query and add missing value', () => {
      const match: IMatch = { id: 456 };
      const homeLineup: ILineup = { id: 99665 };
      match.homeLineup = homeLineup;

      const homeLineupCollection: ILineup[] = [{ id: 51240 }];
      jest.spyOn(lineupService, 'query').mockReturnValue(of(new HttpResponse({ body: homeLineupCollection })));
      const expectedCollection: ILineup[] = [homeLineup, ...homeLineupCollection];
      jest.spyOn(lineupService, 'addLineupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ match });
      comp.ngOnInit();

      expect(lineupService.query).toHaveBeenCalled();
      expect(lineupService.addLineupToCollectionIfMissing).toHaveBeenCalledWith(homeLineupCollection, homeLineup);
      expect(comp.homeLineupsCollection).toEqual(expectedCollection);
    });

    it('Should call awayLineup query and add missing value', () => {
      const match: IMatch = { id: 456 };
      const awayLineup: ILineup = { id: 33441 };
      match.awayLineup = awayLineup;

      const awayLineupCollection: ILineup[] = [{ id: 64214 }];
      jest.spyOn(lineupService, 'query').mockReturnValue(of(new HttpResponse({ body: awayLineupCollection })));
      const expectedCollection: ILineup[] = [awayLineup, ...awayLineupCollection];
      jest.spyOn(lineupService, 'addLineupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ match });
      comp.ngOnInit();

      expect(lineupService.query).toHaveBeenCalled();
      expect(lineupService.addLineupToCollectionIfMissing).toHaveBeenCalledWith(awayLineupCollection, awayLineup);
      expect(comp.awayLineupsCollection).toEqual(expectedCollection);
    });

    it('Should call Team query and add missing value', () => {
      const match: IMatch = { id: 456 };
      const homeTeam: ITeam = { id: 18651 };
      match.homeTeam = homeTeam;
      const awayTeam: ITeam = { id: 56131 };
      match.awayTeam = awayTeam;

      const teamCollection: ITeam[] = [{ id: 5252 }];
      jest.spyOn(teamService, 'query').mockReturnValue(of(new HttpResponse({ body: teamCollection })));
      const additionalTeams = [homeTeam, awayTeam];
      const expectedCollection: ITeam[] = [...additionalTeams, ...teamCollection];
      jest.spyOn(teamService, 'addTeamToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ match });
      comp.ngOnInit();

      expect(teamService.query).toHaveBeenCalled();
      expect(teamService.addTeamToCollectionIfMissing).toHaveBeenCalledWith(teamCollection, ...additionalTeams);
      expect(comp.teamsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Venue query and add missing value', () => {
      const match: IMatch = { id: 456 };
      const venue: IVenue = { id: 38762 };
      match.venue = venue;

      const venueCollection: IVenue[] = [{ id: 46354 }];
      jest.spyOn(venueService, 'query').mockReturnValue(of(new HttpResponse({ body: venueCollection })));
      const additionalVenues = [venue];
      const expectedCollection: IVenue[] = [...additionalVenues, ...venueCollection];
      jest.spyOn(venueService, 'addVenueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ match });
      comp.ngOnInit();

      expect(venueService.query).toHaveBeenCalled();
      expect(venueService.addVenueToCollectionIfMissing).toHaveBeenCalledWith(venueCollection, ...additionalVenues);
      expect(comp.venuesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TeamStaffMember query and add missing value', () => {
      const match: IMatch = { id: 456 };
      const matchDelegate: ITeamStaffMember = { id: 62078 };
      match.matchDelegate = matchDelegate;

      const teamStaffMemberCollection: ITeamStaffMember[] = [{ id: 93671 }];
      jest.spyOn(teamStaffMemberService, 'query').mockReturnValue(of(new HttpResponse({ body: teamStaffMemberCollection })));
      const additionalTeamStaffMembers = [matchDelegate];
      const expectedCollection: ITeamStaffMember[] = [...additionalTeamStaffMembers, ...teamStaffMemberCollection];
      jest.spyOn(teamStaffMemberService, 'addTeamStaffMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ match });
      comp.ngOnInit();

      expect(teamStaffMemberService.query).toHaveBeenCalled();
      expect(teamStaffMemberService.addTeamStaffMemberToCollectionIfMissing).toHaveBeenCalledWith(
        teamStaffMemberCollection,
        ...additionalTeamStaffMembers
      );
      expect(comp.teamStaffMembersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Shirt query and add missing value', () => {
      const match: IMatch = { id: 456 };
      const homeShirt: IShirt = { id: 21576 };
      match.homeShirt = homeShirt;
      const awayShirt: IShirt = { id: 14356 };
      match.awayShirt = awayShirt;

      const shirtCollection: IShirt[] = [{ id: 94436 }];
      jest.spyOn(shirtService, 'query').mockReturnValue(of(new HttpResponse({ body: shirtCollection })));
      const additionalShirts = [homeShirt, awayShirt];
      const expectedCollection: IShirt[] = [...additionalShirts, ...shirtCollection];
      jest.spyOn(shirtService, 'addShirtToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ match });
      comp.ngOnInit();

      expect(shirtService.query).toHaveBeenCalled();
      expect(shirtService.addShirtToCollectionIfMissing).toHaveBeenCalledWith(shirtCollection, ...additionalShirts);
      expect(comp.shirtsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Matchday query and add missing value', () => {
      const match: IMatch = { id: 456 };
      const matchday: IMatchday = { id: 29656 };
      match.matchday = matchday;

      const matchdayCollection: IMatchday[] = [{ id: 32993 }];
      jest.spyOn(matchdayService, 'query').mockReturnValue(of(new HttpResponse({ body: matchdayCollection })));
      const additionalMatchdays = [matchday];
      const expectedCollection: IMatchday[] = [...additionalMatchdays, ...matchdayCollection];
      jest.spyOn(matchdayService, 'addMatchdayToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ match });
      comp.ngOnInit();

      expect(matchdayService.query).toHaveBeenCalled();
      expect(matchdayService.addMatchdayToCollectionIfMissing).toHaveBeenCalledWith(matchdayCollection, ...additionalMatchdays);
      expect(comp.matchdaysSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Referee query and add missing value', () => {
      const match: IMatch = { id: 456 };
      const referees: IReferee[] = [{ id: 84544 }];
      match.referees = referees;

      const refereeCollection: IReferee[] = [{ id: 5724 }];
      jest.spyOn(refereeService, 'query').mockReturnValue(of(new HttpResponse({ body: refereeCollection })));
      const additionalReferees = [...referees];
      const expectedCollection: IReferee[] = [...additionalReferees, ...refereeCollection];
      jest.spyOn(refereeService, 'addRefereeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ match });
      comp.ngOnInit();

      expect(refereeService.query).toHaveBeenCalled();
      expect(refereeService.addRefereeToCollectionIfMissing).toHaveBeenCalledWith(refereeCollection, ...additionalReferees);
      expect(comp.refereesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const match: IMatch = { id: 456 };
      const motm: IMatchPlayer = { id: 1857 };
      match.motm = motm;
      const homeLineup: ILineup = { id: 25185 };
      match.homeLineup = homeLineup;
      const awayLineup: ILineup = { id: 16231 };
      match.awayLineup = awayLineup;
      const homeTeam: ITeam = { id: 49369 };
      match.homeTeam = homeTeam;
      const awayTeam: ITeam = { id: 36215 };
      match.awayTeam = awayTeam;
      const venue: IVenue = { id: 16088 };
      match.venue = venue;
      const matchDelegate: ITeamStaffMember = { id: 8013 };
      match.matchDelegate = matchDelegate;
      const homeShirt: IShirt = { id: 68436 };
      match.homeShirt = homeShirt;
      const awayShirt: IShirt = { id: 36509 };
      match.awayShirt = awayShirt;
      const matchday: IMatchday = { id: 46006 };
      match.matchday = matchday;
      const referees: IReferee = { id: 97221 };
      match.referees = [referees];

      activatedRoute.data = of({ match });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(match));
      expect(comp.motmsCollection).toContain(motm);
      expect(comp.homeLineupsCollection).toContain(homeLineup);
      expect(comp.awayLineupsCollection).toContain(awayLineup);
      expect(comp.teamsSharedCollection).toContain(homeTeam);
      expect(comp.teamsSharedCollection).toContain(awayTeam);
      expect(comp.venuesSharedCollection).toContain(venue);
      expect(comp.teamStaffMembersSharedCollection).toContain(matchDelegate);
      expect(comp.shirtsSharedCollection).toContain(homeShirt);
      expect(comp.shirtsSharedCollection).toContain(awayShirt);
      expect(comp.matchdaysSharedCollection).toContain(matchday);
      expect(comp.refereesSharedCollection).toContain(referees);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Match>>();
      const match = { id: 123 };
      jest.spyOn(matchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ match });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: match }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(matchService.update).toHaveBeenCalledWith(match);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Match>>();
      const match = new Match();
      jest.spyOn(matchService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ match });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: match }));
      saveSubject.complete();

      // THEN
      expect(matchService.create).toHaveBeenCalledWith(match);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Match>>();
      const match = { id: 123 };
      jest.spyOn(matchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ match });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(matchService.update).toHaveBeenCalledWith(match);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackMatchPlayerById', () => {
      it('Should return tracked MatchPlayer primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackMatchPlayerById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackLineupById', () => {
      it('Should return tracked Lineup primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLineupById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTeamById', () => {
      it('Should return tracked Team primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTeamById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackVenueById', () => {
      it('Should return tracked Venue primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackVenueById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTeamStaffMemberById', () => {
      it('Should return tracked TeamStaffMember primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTeamStaffMemberById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackShirtById', () => {
      it('Should return tracked Shirt primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackShirtById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackMatchdayById', () => {
      it('Should return tracked Matchday primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackMatchdayById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackRefereeById', () => {
      it('Should return tracked Referee primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRefereeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedReferee', () => {
      it('Should return option if no Referee is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedReferee(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected Referee for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedReferee(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this Referee is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedReferee(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
