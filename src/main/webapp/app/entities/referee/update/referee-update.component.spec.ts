import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RefereeService } from '../service/referee.service';
import { IReferee, Referee } from '../referee.model';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';
import { IAssociation } from 'app/entities/association/association.model';
import { AssociationService } from 'app/entities/association/service/association.service';

import { RefereeUpdateComponent } from './referee-update.component';

describe('Referee Management Update Component', () => {
  let comp: RefereeUpdateComponent;
  let fixture: ComponentFixture<RefereeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let refereeService: RefereeService;
  let personService: PersonService;
  let associationService: AssociationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RefereeUpdateComponent],
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
      .overrideTemplate(RefereeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RefereeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    refereeService = TestBed.inject(RefereeService);
    personService = TestBed.inject(PersonService);
    associationService = TestBed.inject(AssociationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call person query and add missing value', () => {
      const referee: IReferee = { id: 456 };
      const person: IPerson = { id: 95546 };
      referee.person = person;

      const personCollection: IPerson[] = [{ id: 16223 }];
      jest.spyOn(personService, 'query').mockReturnValue(of(new HttpResponse({ body: personCollection })));
      const expectedCollection: IPerson[] = [person, ...personCollection];
      jest.spyOn(personService, 'addPersonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ referee });
      comp.ngOnInit();

      expect(personService.query).toHaveBeenCalled();
      expect(personService.addPersonToCollectionIfMissing).toHaveBeenCalledWith(personCollection, person);
      expect(comp.peopleCollection).toEqual(expectedCollection);
    });

    it('Should call Association query and add missing value', () => {
      const referee: IReferee = { id: 456 };
      const association: IAssociation = { id: 39101 };
      referee.association = association;

      const associationCollection: IAssociation[] = [{ id: 90493 }];
      jest.spyOn(associationService, 'query').mockReturnValue(of(new HttpResponse({ body: associationCollection })));
      const additionalAssociations = [association];
      const expectedCollection: IAssociation[] = [...additionalAssociations, ...associationCollection];
      jest.spyOn(associationService, 'addAssociationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ referee });
      comp.ngOnInit();

      expect(associationService.query).toHaveBeenCalled();
      expect(associationService.addAssociationToCollectionIfMissing).toHaveBeenCalledWith(associationCollection, ...additionalAssociations);
      expect(comp.associationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const referee: IReferee = { id: 456 };
      const person: IPerson = { id: 34465 };
      referee.person = person;
      const association: IAssociation = { id: 17925 };
      referee.association = association;

      activatedRoute.data = of({ referee });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(referee));
      expect(comp.peopleCollection).toContain(person);
      expect(comp.associationsSharedCollection).toContain(association);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Referee>>();
      const referee = { id: 123 };
      jest.spyOn(refereeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ referee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: referee }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(refereeService.update).toHaveBeenCalledWith(referee);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Referee>>();
      const referee = new Referee();
      jest.spyOn(refereeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ referee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: referee }));
      saveSubject.complete();

      // THEN
      expect(refereeService.create).toHaveBeenCalledWith(referee);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Referee>>();
      const referee = { id: 123 };
      jest.spyOn(refereeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ referee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(refereeService.update).toHaveBeenCalledWith(referee);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPersonById', () => {
      it('Should return tracked Person primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPersonById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAssociationById', () => {
      it('Should return tracked Association primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAssociationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
