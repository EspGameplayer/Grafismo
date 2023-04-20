import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BroadcastPersonnelMemberDetailComponent } from './broadcast-personnel-member-detail.component';

describe('BroadcastPersonnelMember Management Detail Component', () => {
  let comp: BroadcastPersonnelMemberDetailComponent;
  let fixture: ComponentFixture<BroadcastPersonnelMemberDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BroadcastPersonnelMemberDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ broadcastPersonnelMember: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BroadcastPersonnelMemberDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BroadcastPersonnelMemberDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load broadcastPersonnelMember on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.broadcastPersonnelMember).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
