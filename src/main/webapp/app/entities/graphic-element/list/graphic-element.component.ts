import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGraphicElement } from '../graphic-element.model';

import { ASC, DESC, ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { GraphicElementService } from '../service/graphic-element.service';
import { GraphicElementDeleteDialogComponent } from '../delete/graphic-element-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-graphic-element',
  templateUrl: './graphic-element.component.html',
})
export class GraphicElementComponent implements OnInit {
  graphicElements: IGraphicElement[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected graphicElementService: GraphicElementService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.graphicElements = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.isLoading = true;

    this.graphicElementService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<IGraphicElement[]>) => {
          this.isLoading = false;
          this.paginateGraphicElements(res.body, res.headers);
        },
        error: () => {
          this.isLoading = false;
        },
      });
  }

  reset(): void {
    this.page = 0;
    this.graphicElements = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IGraphicElement): number {
    return item.id!;
  }

  delete(graphicElement: IGraphicElement): void {
    const modalRef = this.modalService.open(GraphicElementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.graphicElement = graphicElement;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.reset();
      }
    });
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateGraphicElements(data: IGraphicElement[] | null, headers: HttpHeaders): void {
    const linkHeader = headers.get('link');
    if (linkHeader) {
      this.links = this.parseLinks.parse(linkHeader);
    } else {
      this.links = {
        last: 0,
      };
    }
    if (data) {
      for (const d of data) {
        this.graphicElements.push(d);
      }
    }
  }
}
