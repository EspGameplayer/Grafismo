import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StaffMemberService } from '../service/staff-member.service';
import { IStaffMember, StaffMember } from '../staff-member.model';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';

import { StaffMemberUpdateComponent } from './staff-member-update.component';

describe('StaffMember Management Update Component', () => {
  let comp: StaffMemberUpdateComponent;
  let fixture: ComponentFixture<StaffMemberUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let staffMemberService: StaffMemberService;
  let personService: PersonService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StaffMemberUpdateComponent],
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
      .overrideTemplate(StaffMemberUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StaffMemberUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    staffMemberService = TestBed.inject(StaffMemberService);
    personService = TestBed.inject(PersonService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call person query and add missing value', () => {
      const staffMember: IStaffMember = { id: 456 };
      const person: IPerson = { id: 99358 };
      staffMember.person = person;

      const personCollection: IPerson[] = [{ id: 51273 }];
      jest.spyOn(personService, 'query').mockReturnValue(of(new HttpResponse({ body: personCollection })));
      const expectedCollection: IPerson[] = [person, ...personCollection];
      jest.spyOn(personService, 'addPersonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ staffMember });
      comp.ngOnInit();

      expect(personService.query).toHaveBeenCalled();
      expect(personService.addPersonToCollectionIfMissing).toHaveBeenCalledWith(personCollection, person);
      expect(comp.peopleCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const staffMember: IStaffMember = { id: 456 };
      const person: IPerson = { id: 85871 };
      staffMember.person = person;

      activatedRoute.data = of({ staffMember });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(staffMember));
      expect(comp.peopleCollection).toContain(person);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StaffMember>>();
      const staffMember = { id: 123 };
      jest.spyOn(staffMemberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ staffMember });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: staffMember }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(staffMemberService.update).toHaveBeenCalledWith(staffMember);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StaffMember>>();
      const staffMember = new StaffMember();
      jest.spyOn(staffMemberService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ staffMember });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: staffMember }));
      saveSubject.complete();

      // THEN
      expect(staffMemberService.create).toHaveBeenCalledWith(staffMember);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StaffMember>>();
      const staffMember = { id: 123 };
      jest.spyOn(staffMemberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ staffMember });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(staffMemberService.update).toHaveBeenCalledWith(staffMember);
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
  });
});
