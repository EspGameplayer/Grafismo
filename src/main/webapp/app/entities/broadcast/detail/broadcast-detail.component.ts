import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBroadcast } from '../broadcast.model';

@Component({
  selector: 'jhi-broadcast-detail',
  templateUrl: './broadcast-detail.component.html',
})
export class BroadcastDetailComponent implements OnInit {
  broadcast: IBroadcast | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ broadcast }) => {
      this.broadcast = broadcast;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
