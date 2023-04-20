import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GraphicElementService } from '../service/graphic-element.service';
import { IGraphicElement, GraphicElement } from '../graphic-element.model';

import { GraphicElementUpdateComponent } from './graphic-element-update.component';

describe('GraphicElement Management Update Component', () => {
  let comp: GraphicElementUpdateComponent;
  let fixture: ComponentFixture<GraphicElementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let graphicElementService: GraphicElementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GraphicElementUpdateComponent],
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
      .overrideTemplate(GraphicElementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GraphicElementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    graphicElementService = TestBed.inject(GraphicElementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const graphicElement: IGraphicElement = { id: 456 };

      activatedRoute.data = of({ graphicElement });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(graphicElement));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<GraphicElement>>();
      const graphicElement = { id: 123 };
      jest.spyOn(graphicElementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ graphicElement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: graphicElement }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(graphicElementService.update).toHaveBeenCalledWith(graphicElement);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<GraphicElement>>();
      const graphicElement = new GraphicElement();
      jest.spyOn(graphicElementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ graphicElement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: graphicElement }));
      saveSubject.complete();

      // THEN
      expect(graphicElementService.create).toHaveBeenCalledWith(graphicElement);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<GraphicElement>>();
      const graphicElement = { id: 123 };
      jest.spyOn(graphicElementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ graphicElement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(graphicElementService.update).toHaveBeenCalledWith(graphicElement);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
