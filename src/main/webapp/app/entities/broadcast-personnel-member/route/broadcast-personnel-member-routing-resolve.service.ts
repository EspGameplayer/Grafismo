import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBroadcastPersonnelMember, BroadcastPersonnelMember } from '../broadcast-personnel-member.model';
import { BroadcastPersonnelMemberService } from '../service/broadcast-personnel-member.service';

@Injectable({ providedIn: 'root' })
export class BroadcastPersonnelMemberRoutingResolveService implements Resolve<IBroadcastPersonnelMember> {
  constructor(protected service: BroadcastPersonnelMemberService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBroadcastPersonnelMember> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((broadcastPersonnelMember: HttpResponse<BroadcastPersonnelMember>) => {
          if (broadcastPersonnelMember.body) {
            return of(broadcastPersonnelMember.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BroadcastPersonnelMember());
  }
}
