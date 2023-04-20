import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TemplateFormationService } from '../service/template-formation.service';
import { ITemplateFormation, TemplateFormation } from '../template-formation.model';
import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';

import { TemplateFormationUpdateComponent } from './template-formation-update.component';

describe('TemplateFormation Management Update Component', () => {
  let comp: TemplateFormationUpdateComponent;
  let fixture: ComponentFixture<TemplateFormationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let templateFormationService: TemplateFormationService;
  let formationService: FormationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TemplateFormationUpdateComponent],
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
      .overrideTemplate(TemplateFormationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TemplateFormationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    templateFormationService = TestBed.inject(TemplateFormationService);
    formationService = TestBed.inject(FormationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call formation query and add missing value', () => {
      const templateFormation: ITemplateFormation = { id: 456 };
      const formation: IFormation = { id: 60248 };
      templateFormation.formation = formation;

      const formationCollection: IFormation[] = [{ id: 20939 }];
      jest.spyOn(formationService, 'query').mockReturnValue(of(new HttpResponse({ body: formationCollection })));
      const expectedCollection: IFormation[] = [formation, ...formationCollection];
      jest.spyOn(formationService, 'addFormationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ templateFormation });
      comp.ngOnInit();

      expect(formationService.query).toHaveBeenCalled();
      expect(formationService.addFormationToCollectionIfMissing).toHaveBeenCalledWith(formationCollection, formation);
      expect(comp.formationsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const templateFormation: ITemplateFormation = { id: 456 };
      const formation: IFormation = { id: 24283 };
      templateFormation.formation = formation;

      activatedRoute.data = of({ templateFormation });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(templateFormation));
      expect(comp.formationsCollection).toContain(formation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TemplateFormation>>();
      const templateFormation = { id: 123 };
      jest.spyOn(templateFormationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ templateFormation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: templateFormation }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(templateFormationService.update).toHaveBeenCalledWith(templateFormation);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TemplateFormation>>();
      const templateFormation = new TemplateFormation();
      jest.spyOn(templateFormationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ templateFormation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: templateFormation }));
      saveSubject.complete();

      // THEN
      expect(templateFormationService.create).toHaveBeenCalledWith(templateFormation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TemplateFormation>>();
      const templateFormation = { id: 123 };
      jest.spyOn(templateFormationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ templateFormation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(templateFormationService.update).toHaveBeenCalledWith(templateFormation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackFormationById', () => {
      it('Should return tracked Formation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFormationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
