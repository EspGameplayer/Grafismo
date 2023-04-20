import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SystemConfigurationService } from '../service/system-configuration.service';
import { ISystemConfiguration, SystemConfiguration } from '../system-configuration.model';
import { ISeason } from 'app/entities/season/season.model';
import { SeasonService } from 'app/entities/season/service/season.service';

import { SystemConfigurationUpdateComponent } from './system-configuration-update.component';

describe('SystemConfiguration Management Update Component', () => {
  let comp: SystemConfigurationUpdateComponent;
  let fixture: ComponentFixture<SystemConfigurationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let systemConfigurationService: SystemConfigurationService;
  let seasonService: SeasonService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SystemConfigurationUpdateComponent],
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
      .overrideTemplate(SystemConfigurationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SystemConfigurationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    systemConfigurationService = TestBed.inject(SystemConfigurationService);
    seasonService = TestBed.inject(SeasonService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call currentSeason query and add missing value', () => {
      const systemConfiguration: ISystemConfiguration = { id: 456 };
      const currentSeason: ISeason = { id: 34224 };
      systemConfiguration.currentSeason = currentSeason;

      const currentSeasonCollection: ISeason[] = [{ id: 76994 }];
      jest.spyOn(seasonService, 'query').mockReturnValue(of(new HttpResponse({ body: currentSeasonCollection })));
      const expectedCollection: ISeason[] = [currentSeason, ...currentSeasonCollection];
      jest.spyOn(seasonService, 'addSeasonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ systemConfiguration });
      comp.ngOnInit();

      expect(seasonService.query).toHaveBeenCalled();
      expect(seasonService.addSeasonToCollectionIfMissing).toHaveBeenCalledWith(currentSeasonCollection, currentSeason);
      expect(comp.currentSeasonsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const systemConfiguration: ISystemConfiguration = { id: 456 };
      const currentSeason: ISeason = { id: 52398 };
      systemConfiguration.currentSeason = currentSeason;

      activatedRoute.data = of({ systemConfiguration });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(systemConfiguration));
      expect(comp.currentSeasonsCollection).toContain(currentSeason);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SystemConfiguration>>();
      const systemConfiguration = { id: 123 };
      jest.spyOn(systemConfigurationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ systemConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: systemConfiguration }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(systemConfigurationService.update).toHaveBeenCalledWith(systemConfiguration);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SystemConfiguration>>();
      const systemConfiguration = new SystemConfiguration();
      jest.spyOn(systemConfigurationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ systemConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: systemConfiguration }));
      saveSubject.complete();

      // THEN
      expect(systemConfigurationService.create).toHaveBeenCalledWith(systemConfiguration);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SystemConfiguration>>();
      const systemConfiguration = { id: 123 };
      jest.spyOn(systemConfigurationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ systemConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(systemConfigurationService.update).toHaveBeenCalledWith(systemConfiguration);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSeasonById', () => {
      it('Should return tracked Season primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSeasonById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
