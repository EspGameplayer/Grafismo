import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StaffMemberDetailComponent } from './staff-member-detail.component';

describe('StaffMember Management Detail Component', () => {
  let comp: StaffMemberDetailComponent;
  let fixture: ComponentFixture<StaffMemberDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StaffMemberDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ staffMember: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(StaffMemberDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StaffMemberDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load staffMember on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.staffMember).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
