import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGraphicElement } from '../graphic-element.model';

@Component({
  selector: 'jhi-graphic-element-detail',
  templateUrl: './graphic-element-detail.component.html',
})
export class GraphicElementDetailComponent implements OnInit {
  graphicElement: IGraphicElement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ graphicElement }) => {
      this.graphicElement = graphicElement;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
