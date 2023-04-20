import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MatchActionDetailComponent } from './match-action-detail.component';

describe('MatchAction Management Detail Component', () => {
  let comp: MatchActionDetailComponent;
  let fixture: ComponentFixture<MatchActionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MatchActionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ matchAction: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MatchActionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MatchActionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load matchAction on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.matchAction).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
