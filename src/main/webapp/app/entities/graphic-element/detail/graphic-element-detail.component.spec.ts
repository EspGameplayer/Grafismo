import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GraphicElementDetailComponent } from './graphic-element-detail.component';

describe('GraphicElement Management Detail Component', () => {
  let comp: GraphicElementDetailComponent;
  let fixture: ComponentFixture<GraphicElementDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GraphicElementDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ graphicElement: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(GraphicElementDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GraphicElementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load graphicElement on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.graphicElement).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
