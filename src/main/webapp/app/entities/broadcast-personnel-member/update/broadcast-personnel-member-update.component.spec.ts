import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BroadcastPersonnelMemberService } from '../service/broadcast-personnel-member.service';
import { IBroadcastPersonnelMember, BroadcastPersonnelMember } from '../broadcast-personnel-member.model';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';

import { BroadcastPersonnelMemberUpdateComponent } from './broadcast-personnel-member-update.component';

describe('BroadcastPersonnelMember Management Update Component', () => {
  let comp: BroadcastPersonnelMemberUpdateComponent;
  let fixture: ComponentFixture<BroadcastPersonnelMemberUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let broadcastPersonnelMemberService: BroadcastPersonnelMemberService;
  let personService: PersonService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BroadcastPersonnelMemberUpdateComponent],
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
      .overrideTemplate(BroadcastPersonnelMemberUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BroadcastPersonnelMemberUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    broadcastPersonnelMemberService = TestBed.inject(BroadcastPersonnelMemberService);
    personService = TestBed.inject(PersonService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call person query and add missing value', () => {
      const broadcastPersonnelMember: IBroadcastPersonnelMember = { id: 456 };
      const person: IPerson = { id: 12229 };
      broadcastPersonnelMember.person = person;

      const personCollection: IPerson[] = [{ id: 85458 }];
      jest.spyOn(personService, 'query').mockReturnValue(of(new HttpResponse({ body: personCollection })));
      const expectedCollection: IPerson[] = [person, ...personCollection];
      jest.spyOn(personService, 'addPersonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ broadcastPersonnelMember });
      comp.ngOnInit();

      expect(personService.query).toHaveBeenCalled();
      expect(personService.addPersonToCollectionIfMissing).toHaveBeenCalledWith(personCollection, person);
      expect(comp.peopleCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const broadcastPersonnelMember: IBroadcastPersonnelMember = { id: 456 };
      const person: IPerson = { id: 85963 };
      broadcastPersonnelMember.person = person;

      activatedRoute.data = of({ broadcastPersonnelMember });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(broadcastPersonnelMember));
      expect(comp.peopleCollection).toContain(person);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BroadcastPersonnelMember>>();
      const broadcastPersonnelMember = { id: 123 };
      jest.spyOn(broadcastPersonnelMemberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ broadcastPersonnelMember });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: broadcastPersonnelMember }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(broadcastPersonnelMemberService.update).toHaveBeenCalledWith(broadcastPersonnelMember);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BroadcastPersonnelMember>>();
      const broadcastPersonnelMember = new BroadcastPersonnelMember();
      jest.spyOn(broadcastPersonnelMemberService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ broadcastPersonnelMember });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: broadcastPersonnelMember }));
      saveSubject.complete();

      // THEN
      expect(broadcastPersonnelMemberService.create).toHaveBeenCalledWith(broadcastPersonnelMember);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BroadcastPersonnelMember>>();
      const broadcastPersonnelMember = { id: 123 };
      jest.spyOn(broadcastPersonnelMemberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ broadcastPersonnelMember });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(broadcastPersonnelMemberService.update).toHaveBeenCalledWith(broadcastPersonnelMember);
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
