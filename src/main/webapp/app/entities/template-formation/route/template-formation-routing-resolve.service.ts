import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITemplateFormation, TemplateFormation } from '../template-formation.model';
import { TemplateFormationService } from '../service/template-formation.service';

@Injectable({ providedIn: 'root' })
export class TemplateFormationRoutingResolveService implements Resolve<ITemplateFormation> {
  constructor(protected service: TemplateFormationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITemplateFormation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((templateFormation: HttpResponse<TemplateFormation>) => {
          if (templateFormation.body) {
            return of(templateFormation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TemplateFormation());
  }
}
