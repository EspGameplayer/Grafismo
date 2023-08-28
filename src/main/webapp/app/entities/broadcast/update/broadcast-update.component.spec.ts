import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BroadcastService } from '../service/broadcast.service';
import { IBroadcast, Broadcast } from '../broadcast.model';
import { IMatch } from 'app/entities/match/match.model';
import { MatchService } from 'app/entities/match/service/match.service';
import { ISystemConfiguration } from 'app/entities/system-configuration/system-configuration.model';
import { SystemConfigurationService } from 'app/entities/system-configuration/service/system-configuration.service';
import { IBroadcastPersonnelMember } from 'app/entities/broadcast-personnel-member/broadcast-personnel-member.model';
import { BroadcastPersonnelMemberService } from 'app/entities/broadcast-personnel-member/service/broadcast-personnel-member.service';

import { BroadcastUpdateComponent } from './broadcast-update.component';

describe('Broadcast Management Update Component', () => {
  let comp: BroadcastUpdateComponent;
  let fixture: ComponentFixture<BroadcastUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let broadcastService: BroadcastService;
  let matchService: MatchService;
  let systemConfigurationService: SystemConfigurationService;
  let broadcastPersonnelMemberService: BroadcastPersonnelMemberService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BroadcastUpdateComponent],
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
      .overrideTemplate(BroadcastUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BroadcastUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    broadcastService = TestBed.inject(BroadcastService);
    matchService = TestBed.inject(MatchService);
    systemConfigurationService = TestBed.inject(SystemConfigurationService);
    broadcastPersonnelMemberService = TestBed.inject(BroadcastPersonnelMemberService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call match query and add missing value', () => {
      const broadcast: IBroadcast = { id: 456 };
      const match: IMatch = { id: 80497 };
      broadcast.match = match;

      const matchCollection: IMatch[] = [{ id: 47905 }];
      jest.spyOn(matchService, 'query').mockReturnValue(of(new HttpResponse({ body: matchCollection })));
      const expectedCollection: IMatch[] = [match, ...matchCollection];
      jest.spyOn(matchService, 'addMatchToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ broadcast });
      comp.ngOnInit();

      expect(matchService.query).toHaveBeenCalled();
      expect(matchService.addMatchToCollectionIfMissing).toHaveBeenCalledWith(matchCollection, match);
      expect(comp.matchesCollection).toEqual(expectedCollection);
    });

    it('Should call SystemConfiguration query and add missing value', () => {
      const broadcast: IBroadcast = { id: 456 };
      const systemConfiguration: ISystemConfiguration = { id: 90551 };
      broadcast.systemConfiguration = systemConfiguration;

      const systemConfigurationCollection: ISystemConfiguration[] = [{ id: 14529 }];
      jest.spyOn(systemConfigurationService, 'query').mockReturnValue(of(new HttpResponse({ body: systemConfigurationCollection })));
      const additionalSystemConfigurations = [systemConfiguration];
      const expectedCollection: ISystemConfiguration[] = [...additionalSystemConfigurations, ...systemConfigurationCollection];
      jest.spyOn(systemConfigurationService, 'addSystemConfigurationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ broadcast });
      comp.ngOnInit();

      expect(systemConfigurationService.query).toHaveBeenCalled();
      expect(systemConfigurationService.addSystemConfigurationToCollectionIfMissing).toHaveBeenCalledWith(
        systemConfigurationCollection,
        ...additionalSystemConfigurations
      );
      expect(comp.systemConfigurationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call BroadcastPersonnelMember query and add missing value', () => {
      const broadcast: IBroadcast = { id: 456 };
      const broadcastPersonnelMembers: IBroadcastPersonnelMember[] = [{ id: 19475 }];
      broadcast.broadcastPersonnelMembers = broadcastPersonnelMembers;

      const broadcastPersonnelMemberCollection: IBroadcastPersonnelMember[] = [{ id: 75666 }];
      jest
        .spyOn(broadcastPersonnelMemberService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: broadcastPersonnelMemberCollection })));
      const additionalBroadcastPersonnelMembers = [...broadcastPersonnelMembers];
      const expectedCollection: IBroadcastPersonnelMember[] = [
        ...additionalBroadcastPersonnelMembers,
        ...broadcastPersonnelMemberCollection,
      ];
      jest.spyOn(broadcastPersonnelMemberService, 'addBroadcastPersonnelMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ broadcast });
      comp.ngOnInit();

      expect(broadcastPersonnelMemberService.query).toHaveBeenCalled();
      expect(broadcastPersonnelMemberService.addBroadcastPersonnelMemberToCollectionIfMissing).toHaveBeenCalledWith(
        broadcastPersonnelMemberCollection,
        ...additionalBroadcastPersonnelMembers
      );
      expect(comp.broadcastPersonnelMembersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const broadcast: IBroadcast = { id: 456 };
      const match: IMatch = { id: 64554 };
      broadcast.match = match;
      const systemConfiguration: ISystemConfiguration = { id: 18827 };
      broadcast.systemConfiguration = systemConfiguration;
      const broadcastPersonnelMembers: IBroadcastPersonnelMember = { id: 95230 };
      broadcast.broadcastPersonnelMembers = [broadcastPersonnelMembers];

      activatedRoute.data = of({ broadcast });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(broadcast));
      expect(comp.matchesCollection).toContain(match);
      expect(comp.systemConfigurationsSharedCollection).toContain(systemConfiguration);
      expect(comp.broadcastPersonnelMembersSharedCollection).toContain(broadcastPersonnelMembers);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Broadcast>>();
      const broadcast = { id: 123 };
      jest.spyOn(broadcastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ broadcast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: broadcast }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(broadcastService.update).toHaveBeenCalledWith(broadcast);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Broadcast>>();
      const broadcast = new Broadcast();
      jest.spyOn(broadcastService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ broadcast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: broadcast }));
      saveSubject.complete();

      // THEN
      expect(broadcastService.create).toHaveBeenCalledWith(broadcast);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Broadcast>>();
      const broadcast = { id: 123 };
      jest.spyOn(broadcastService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ broadcast });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(broadcastService.update).toHaveBeenCalledWith(broadcast);
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

    describe('trackSystemConfigurationById', () => {
      it('Should return tracked SystemConfiguration primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSystemConfigurationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackBroadcastPersonnelMemberById', () => {
      it('Should return tracked BroadcastPersonnelMember primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackBroadcastPersonnelMemberById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedBroadcastPersonnelMember', () => {
      it('Should return option if no BroadcastPersonnelMember is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedBroadcastPersonnelMember(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected BroadcastPersonnelMember for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedBroadcastPersonnelMember(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this BroadcastPersonnelMember is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedBroadcastPersonnelMember(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
