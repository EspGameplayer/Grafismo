import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMatchAction } from '../match-action.model';

@Component({
  selector: 'jhi-match-action-detail',
  templateUrl: './match-action-detail.component.html',
})
export class MatchActionDetailComponent implements OnInit {
  matchAction: IMatchAction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matchAction }) => {
      this.matchAction = matchAction;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
