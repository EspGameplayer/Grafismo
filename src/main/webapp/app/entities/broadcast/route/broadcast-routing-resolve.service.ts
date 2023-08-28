import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBroadcast, Broadcast } from '../broadcast.model';
import { BroadcastService } from '../service/broadcast.service';

@Injectable({ providedIn: 'root' })
export class BroadcastRoutingResolveService implements Resolve<IBroadcast> {
  constructor(protected service: BroadcastService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBroadcast> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((broadcast: HttpResponse<Broadcast>) => {
          if (broadcast.body) {
            return of(broadcast.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Broadcast());
  }
}
