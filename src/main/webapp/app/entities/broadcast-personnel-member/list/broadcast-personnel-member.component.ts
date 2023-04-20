import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBroadcastPersonnelMember } from '../broadcast-personnel-member.model';

import { ASC, DESC, ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { BroadcastPersonnelMemberService } from '../service/broadcast-personnel-member.service';
import { BroadcastPersonnelMemberDeleteDialogComponent } from '../delete/broadcast-personnel-member-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-broadcast-personnel-member',
  templateUrl: './broadcast-personnel-member.component.html',
})
export class BroadcastPersonnelMemberComponent implements OnInit {
  broadcastPersonnelMembers: IBroadcastPersonnelMember[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected broadcastPersonnelMemberService: BroadcastPersonnelMemberService,
    protected modalService: NgbModal,
    protected parseLinks: ParseLinks
  ) {
    this.broadcastPersonnelMembers = [];
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

    this.broadcastPersonnelMemberService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<IBroadcastPersonnelMember[]>) => {
          this.isLoading = false;
          this.paginateBroadcastPersonnelMembers(res.body, res.headers);
        },
        error: () => {
          this.isLoading = false;
        },
      });
  }

  reset(): void {
    this.page = 0;
    this.broadcastPersonnelMembers = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IBroadcastPersonnelMember): number {
    return item.id!;
  }

  delete(broadcastPersonnelMember: IBroadcastPersonnelMember): void {
    const modalRef = this.modalService.open(BroadcastPersonnelMemberDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.broadcastPersonnelMember = broadcastPersonnelMember;
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

  protected paginateBroadcastPersonnelMembers(data: IBroadcastPersonnelMember[] | null, headers: HttpHeaders): void {
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
        this.broadcastPersonnelMembers.push(d);
      }
    }
  }
}
