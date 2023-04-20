import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITemplateFormation } from '../template-formation.model';

@Component({
  selector: 'jhi-template-formation-detail',
  templateUrl: './template-formation-detail.component.html',
})
export class TemplateFormationDetailComponent implements OnInit {
  templateFormation: ITemplateFormation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ templateFormation }) => {
      this.templateFormation = templateFormation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
