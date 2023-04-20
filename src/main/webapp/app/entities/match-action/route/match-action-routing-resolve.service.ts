import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMatchAction, MatchAction } from '../match-action.model';
import { MatchActionService } from '../service/match-action.service';

@Injectable({ providedIn: 'root' })
export class MatchActionRoutingResolveService implements Resolve<IMatchAction> {
  constructor(protected service: MatchActionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMatchAction> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((matchAction: HttpResponse<MatchAction>) => {
          if (matchAction.body) {
            return of(matchAction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MatchAction());
  }
}
