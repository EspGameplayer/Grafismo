import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LineupService } from '../service/lineup.service';
import { ILineup, Lineup } from '../lineup.model';
import { IMatchPlayer } from 'app/entities/match-player/match-player.model';
import { MatchPlayerService } from 'app/entities/match-player/service/match-player.service';
import { ITeamStaffMember } from 'app/entities/team-staff-member/team-staff-member.model';
import { TeamStaffMemberService } from 'app/entities/team-staff-member/service/team-staff-member.service';
import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';

import { LineupUpdateComponent } from './lineup-update.component';

describe('Lineup Management Update Component', () => {
  let comp: LineupUpdateComponent;
  let fixture: ComponentFixture<LineupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let lineupService: LineupService;
  let matchPlayerService: MatchPlayerService;
  let teamStaffMemberService: TeamStaffMemberService;
  let formationService: FormationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LineupUpdateComponent],
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
      .overrideTemplate(LineupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LineupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    lineupService = TestBed.inject(LineupService);
    matchPlayerService = TestBed.inject(MatchPlayerService);
    teamStaffMemberService = TestBed.inject(TeamStaffMemberService);
    formationService = TestBed.inject(FormationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MatchPlayer query and add missing value', () => {
      const lineup: ILineup = { id: 456 };
      const matchPlayers: IMatchPlayer[] = [{ id: 20342 }];
      lineup.matchPlayers = matchPlayers;

      const matchPlayerCollection: IMatchPlayer[] = [{ id: 35014 }];
      jest.spyOn(matchPlayerService, 'query').mockReturnValue(of(new HttpResponse({ body: matchPlayerCollection })));
      const additionalMatchPlayers = [...matchPlayers];
      const expectedCollection: IMatchPlayer[] = [...additionalMatchPlayers, ...matchPlayerCollection];
      jest.spyOn(matchPlayerService, 'addMatchPlayerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ lineup });
      comp.ngOnInit();

      expect(matchPlayerService.query).toHaveBeenCalled();
      expect(matchPlayerService.addMatchPlayerToCollectionIfMissing).toHaveBeenCalledWith(matchPlayerCollection, ...additionalMatchPlayers);
      expect(comp.matchPlayersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call captain query and add missing value', () => {
      const lineup: ILineup = { id: 456 };
      const captain: IMatchPlayer = { id: 20874 };
      lineup.captain = captain;

      const captainCollection: IMatchPlayer[] = [{ id: 66307 }];
      jest.spyOn(matchPlayerService, 'query').mockReturnValue(of(new HttpResponse({ body: captainCollection })));
      const expectedCollection: IMatchPlayer[] = [captain, ...captainCollection];
      jest.spyOn(matchPlayerService, 'addMatchPlayerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ lineup });
      comp.ngOnInit();

      expect(matchPlayerService.query).toHaveBeenCalled();
      expect(matchPlayerService.addMatchPlayerToCollectionIfMissing).toHaveBeenCalledWith(captainCollection, captain);
      expect(comp.captainsCollection).toEqual(expectedCollection);
    });

    it('Should call TeamStaffMember query and add missing value', () => {
      const lineup: ILineup = { id: 456 };
      const dt: ITeamStaffMember = { id: 64221 };
      lineup.dt = dt;
      const dt2: ITeamStaffMember = { id: 89830 };
      lineup.dt2 = dt2;
      const teamDelegate: ITeamStaffMember = { id: 81656 };
      lineup.teamDelegate = teamDelegate;

      const teamStaffMemberCollection: ITeamStaffMember[] = [{ id: 82013 }];
      jest.spyOn(teamStaffMemberService, 'query').mockReturnValue(of(new HttpResponse({ body: teamStaffMemberCollection })));
      const additionalTeamStaffMembers = [dt, dt2, teamDelegate];
      const expectedCollection: ITeamStaffMember[] = [...additionalTeamStaffMembers, ...teamStaffMemberCollection];
      jest.spyOn(teamStaffMemberService, 'addTeamStaffMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ lineup });
      comp.ngOnInit();

      expect(teamStaffMemberService.query).toHaveBeenCalled();
      expect(teamStaffMemberService.addTeamStaffMemberToCollectionIfMissing).toHaveBeenCalledWith(
        teamStaffMemberCollection,
        ...additionalTeamStaffMembers
      );
      expect(comp.teamStaffMembersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Formation query and add missing value', () => {
      const lineup: ILineup = { id: 456 };
      const formation: IFormation = { id: 21405 };
      lineup.formation = formation;

      const formationCollection: IFormation[] = [{ id: 24807 }];
      jest.spyOn(formationService, 'query').mockReturnValue(of(new HttpResponse({ body: formationCollection })));
      const additionalFormations = [formation];
      const expectedCollection: IFormation[] = [...additionalFormations, ...formationCollection];
      jest.spyOn(formationService, 'addFormationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ lineup });
      comp.ngOnInit();

      expect(formationService.query).toHaveBeenCalled();
      expect(formationService.addFormationToCollectionIfMissing).toHaveBeenCalledWith(formationCollection, ...additionalFormations);
      expect(comp.formationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const lineup: ILineup = { id: 456 };
      const captain: IMatchPlayer = { id: 40976 };
      lineup.captain = captain;
      const matchPlayers: IMatchPlayer = { id: 28014 };
      lineup.matchPlayers = [matchPlayers];
      const dt: ITeamStaffMember = { id: 25070 };
      lineup.dt = dt;
      const dt2: ITeamStaffMember = { id: 15357 };
      lineup.dt2 = dt2;
      const teamDelegate: ITeamStaffMember = { id: 39289 };
      lineup.teamDelegate = teamDelegate;
      const formation: IFormation = { id: 92626 };
      lineup.formation = formation;

      activatedRoute.data = of({ lineup });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(lineup));
      expect(comp.captainsCollection).toContain(captain);
      expect(comp.matchPlayersSharedCollection).toContain(matchPlayers);
      expect(comp.teamStaffMembersSharedCollection).toContain(dt);
      expect(comp.teamStaffMembersSharedCollection).toContain(dt2);
      expect(comp.teamStaffMembersSharedCollection).toContain(teamDelegate);
      expect(comp.formationsSharedCollection).toContain(formation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Lineup>>();
      const lineup = { id: 123 };
      jest.spyOn(lineupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lineup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: lineup }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(lineupService.update).toHaveBeenCalledWith(lineup);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Lineup>>();
      const lineup = new Lineup();
      jest.spyOn(lineupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lineup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: lineup }));
      saveSubject.complete();

      // THEN
      expect(lineupService.create).toHaveBeenCalledWith(lineup);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Lineup>>();
      const lineup = { id: 123 };
      jest.spyOn(lineupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lineup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(lineupService.update).toHaveBeenCalledWith(lineup);
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

    describe('trackTeamStaffMemberById', () => {
      it('Should return tracked TeamStaffMember primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTeamStaffMemberById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackFormationById', () => {
      it('Should return tracked Formation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFormationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedMatchPlayer', () => {
      it('Should return option if no MatchPlayer is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedMatchPlayer(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected MatchPlayer for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedMatchPlayer(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this MatchPlayer is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedMatchPlayer(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
