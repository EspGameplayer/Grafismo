import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStaffMember } from '../staff-member.model';

@Component({
  selector: 'jhi-staff-member-detail',
  templateUrl: './staff-member-detail.component.html',
})
export class StaffMemberDetailComponent implements OnInit {
  staffMember: IStaffMember | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ staffMember }) => {
      this.staffMember = staffMember;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
