import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGraphicElement, GraphicElement } from '../graphic-element.model';
import { GraphicElementService } from '../service/graphic-element.service';

@Injectable({ providedIn: 'root' })
export class GraphicElementRoutingResolveService implements Resolve<IGraphicElement> {
  constructor(protected service: GraphicElementService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGraphicElement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((graphicElement: HttpResponse<GraphicElement>) => {
          if (graphicElement.body) {
            return of(graphicElement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GraphicElement());
  }
}
