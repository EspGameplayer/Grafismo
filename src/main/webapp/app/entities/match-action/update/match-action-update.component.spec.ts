import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MatchActionService } from '../service/match-action.service';
import { IMatchAction, MatchAction } from '../match-action.model';
import { IMatch } from 'app/entities/match/match.model';
import { MatchService } from 'app/entities/match/service/match.service';
import { IMatchPlayer } from 'app/entities/match-player/match-player.model';
import { MatchPlayerService } from 'app/entities/match-player/service/match-player.service';

import { MatchActionUpdateComponent } from './match-action-update.component';

describe('MatchAction Management Update Component', () => {
  let comp: MatchActionUpdateComponent;
  let fixture: ComponentFixture<MatchActionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let matchActionService: MatchActionService;
  let matchService: MatchService;
  let matchPlayerService: MatchPlayerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MatchActionUpdateComponent],
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
      .overrideTemplate(MatchActionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MatchActionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    matchActionService = TestBed.inject(MatchActionService);
    matchService = TestBed.inject(MatchService);
    matchPlayerService = TestBed.inject(MatchPlayerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Match query and add missing value', () => {
      const matchAction: IMatchAction = { id: 456 };
      const match: IMatch = { id: 33350 };
      matchAction.match = match;

      const matchCollection: IMatch[] = [{ id: 89796 }];
      jest.spyOn(matchService, 'query').mockReturnValue(of(new HttpResponse({ body: matchCollection })));
      const additionalMatches = [match];
      const expectedCollection: IMatch[] = [...additionalMatches, ...matchCollection];
      jest.spyOn(matchService, 'addMatchToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matchAction });
      comp.ngOnInit();

      expect(matchService.query).toHaveBeenCalled();
      expect(matchService.addMatchToCollectionIfMissing).toHaveBeenCalledWith(matchCollection, ...additionalMatches);
      expect(comp.matchesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call MatchPlayer query and add missing value', () => {
      const matchAction: IMatchAction = { id: 456 };
      const matchPlayers: IMatchPlayer[] = [{ id: 74774 }];
      matchAction.matchPlayers = matchPlayers;

      const matchPlayerCollection: IMatchPlayer[] = [{ id: 38722 }];
      jest.spyOn(matchPlayerService, 'query').mockReturnValue(of(new HttpResponse({ body: matchPlayerCollection })));
      const additionalMatchPlayers = [...matchPlayers];
      const expectedCollection: IMatchPlayer[] = [...additionalMatchPlayers, ...matchPlayerCollection];
      jest.spyOn(matchPlayerService, 'addMatchPlayerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matchAction });
      comp.ngOnInit();

      expect(matchPlayerService.query).toHaveBeenCalled();
      expect(matchPlayerService.addMatchPlayerToCollectionIfMissing).toHaveBeenCalledWith(matchPlayerCollection, ...additionalMatchPlayers);
      expect(comp.matchPlayersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const matchAction: IMatchAction = { id: 456 };
      const match: IMatch = { id: 30301 };
      matchAction.match = match;
      const matchPlayers: IMatchPlayer = { id: 8252 };
      matchAction.matchPlayers = [matchPlayers];

      activatedRoute.data = of({ matchAction });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(matchAction));
      expect(comp.matchesSharedCollection).toContain(match);
      expect(comp.matchPlayersSharedCollection).toContain(matchPlayers);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MatchAction>>();
      const matchAction = { id: 123 };
      jest.spyOn(matchActionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matchAction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: matchAction }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(matchActionService.update).toHaveBeenCalledWith(matchAction);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MatchAction>>();
      const matchAction = new MatchAction();
      jest.spyOn(matchActionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matchAction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: matchAction }));
      saveSubject.complete();

      // THEN
      expect(matchActionService.create).toHaveBeenCalledWith(matchAction);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MatchAction>>();
      const matchAction = { id: 123 };
      jest.spyOn(matchActionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matchAction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(matchActionService.update).toHaveBeenCalledWith(matchAction);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackMatchById', () => {
      it('Should return tracked Match primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackMatchById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackMatchPlayerById', () => {
      it('Should return tracked MatchPlayer primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackMatchPlayerById(0, entity);
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
