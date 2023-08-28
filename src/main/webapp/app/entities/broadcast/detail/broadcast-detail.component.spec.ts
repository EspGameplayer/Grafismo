import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BroadcastDetailComponent } from './broadcast-detail.component';

describe('Broadcast Management Detail Component', () => {
  let comp: BroadcastDetailComponent;
  let fixture: ComponentFixture<BroadcastDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BroadcastDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ broadcast: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BroadcastDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BroadcastDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load broadcast on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.broadcast).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
