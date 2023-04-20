import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TemplateFormationDetailComponent } from './template-formation-detail.component';

describe('TemplateFormation Management Detail Component', () => {
  let comp: TemplateFormationDetailComponent;
  let fixture: ComponentFixture<TemplateFormationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TemplateFormationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ templateFormation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TemplateFormationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TemplateFormationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load templateFormation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.templateFormation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
