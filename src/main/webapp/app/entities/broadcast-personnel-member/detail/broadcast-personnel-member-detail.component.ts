import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBroadcastPersonnelMember } from '../broadcast-personnel-member.model';

@Component({
  selector: 'jhi-broadcast-personnel-member-detail',
  templateUrl: './broadcast-personnel-member-detail.component.html',
})
export class BroadcastPersonnelMemberDetailComponent implements OnInit {
  broadcastPersonnelMember: IBroadcastPersonnelMember | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ broadcastPersonnelMember }) => {
      this.broadcastPersonnelMember = broadcastPersonnelMember;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
