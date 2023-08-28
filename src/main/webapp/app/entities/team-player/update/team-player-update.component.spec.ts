import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TeamPlayerService } from '../service/team-player.service';
import { ITeamPlayer, TeamPlayer } from '../team-player.model';
import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';
import { IPlayer } from 'app/entities/player/player.model';
import { PlayerService } from 'app/entities/player/service/player.service';

import { TeamPlayerUpdateComponent } from './team-player-update.component';

describe('TeamPlayer Management Update Component', () => {
  let comp: TeamPlayerUpdateComponent;
  let fixture: ComponentFixture<TeamPlayerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let teamPlayerService: TeamPlayerService;
  let teamService: TeamService;
  let playerService: PlayerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TeamPlayerUpdateComponent],
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
      .overrideTemplate(TeamPlayerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TeamPlayerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    teamPlayerService = TestBed.inject(TeamPlayerService);
    teamService = TestBed.inject(TeamService);
    playerService = TestBed.inject(PlayerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Team query and add missing value', () => {
      const teamPlayer: ITeamPlayer = { id: 456 };
      const team: ITeam = { id: 82137 };
      teamPlayer.team = team;

      const teamCollection: ITeam[] = [{ id: 68048 }];
      jest.spyOn(teamService, 'query').mockReturnValue(of(new HttpResponse({ body: teamCollection })));
      const additionalTeams = [team];
      const expectedCollection: ITeam[] = [...additionalTeams, ...teamCollection];
      jest.spyOn(teamService, 'addTeamToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ teamPlayer });
      comp.ngOnInit();

      expect(teamService.query).toHaveBeenCalled();
      expect(teamService.addTeamToCollectionIfMissing).toHaveBeenCalledWith(teamCollection, ...additionalTeams);
      expect(comp.teamsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Player query and add missing value', () => {
      const teamPlayer: ITeamPlayer = { id: 456 };
      const player: IPlayer = { id: 28864 };
      teamPlayer.player = player;

      const playerCollection: IPlayer[] = [{ id: 53817 }];
      jest.spyOn(playerService, 'query').mockReturnValue(of(new HttpResponse({ body: playerCollection })));
      const additionalPlayers = [player];
      const expectedCollection: IPlayer[] = [...additionalPlayers, ...playerCollection];
      jest.spyOn(playerService, 'addPlayerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ teamPlayer });
      comp.ngOnInit();

      expect(playerService.query).toHaveBeenCalled();
      expect(playerService.addPlayerToCollectionIfMissing).toHaveBeenCalledWith(playerCollection, ...additionalPlayers);
      expect(comp.playersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const teamPlayer: ITeamPlayer = { id: 456 };
      const team: ITeam = { id: 71220 };
      teamPlayer.team = team;
      const player: IPlayer = { id: 95280 };
      teamPlayer.player = player;

      activatedRoute.data = of({ teamPlayer });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(teamPlayer));
      expect(comp.teamsSharedCollection).toContain(team);
      expect(comp.playersSharedCollection).toContain(player);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TeamPlayer>>();
      const teamPlayer = { id: 123 };
      jest.spyOn(teamPlayerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teamPlayer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: teamPlayer }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(teamPlayerService.update).toHaveBeenCalledWith(teamPlayer);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TeamPlayer>>();
      const teamPlayer = new TeamPlayer();
      jest.spyOn(teamPlayerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teamPlayer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: teamPlayer }));
      saveSubject.complete();

      // THEN
      expect(teamPlayerService.create).toHaveBeenCalledWith(teamPlayer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TeamPlayer>>();
      const teamPlayer = { id: 123 };
      jest.spyOn(teamPlayerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teamPlayer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(teamPlayerService.update).toHaveBeenCalledWith(teamPlayer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTeamById', () => {
      it('Should return tracked Team primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTeamById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPlayerById', () => {
      it('Should return tracked Player primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPlayerById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
