import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITemplateFormation } from '../template-formation.model';

import { ASC, DESC, ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { TemplateFormationService } from '../service/template-formation.service';
import { TemplateFormationDeleteDialogComponent } from '../delete/template-formation-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-template-formation',
  templateUrl: './template-formation.component.html',
})
export class TemplateFormationComponent implements OnInit {
  templateFormations: ITemplateFormation[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected templateFormationService: TemplateFormationService,
    protected modalService: NgbModal,
    protected parseLinks: ParseLinks
  ) {
    this.templateFormations = [];
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

    this.templateFormationService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<ITemplateFormation[]>) => {
          this.isLoading = false;
          this.paginateTemplateFormations(res.body, res.headers);
        },
        error: () => {
          this.isLoading = false;
        },
      });
  }

  reset(): void {
    this.page = 0;
    this.templateFormations = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ITemplateFormation): number {
    return item.id!;
  }

  delete(templateFormation: ITemplateFormation): void {
    const modalRef = this.modalService.open(TemplateFormationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.templateFormation = templateFormation;
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

  protected paginateTemplateFormations(data: ITemplateFormation[] | null, headers: HttpHeaders): void {
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
        this.templateFormations.push(d);
      }
    }
  }
}
