import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITemplateFormation, getTemplateFormationIdentifier } from '../template-formation.model';

export type EntityResponseType = HttpResponse<ITemplateFormation>;
export type EntityArrayResponseType = HttpResponse<ITemplateFormation[]>;

@Injectable({ providedIn: 'root' })
export class TemplateFormationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/template-formations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(templateFormation: ITemplateFormation): Observable<EntityResponseType> {
    return this.http.post<ITemplateFormation>(this.resourceUrl, templateFormation, { observe: 'response' });
  }

  update(templateFormation: ITemplateFormation): Observable<EntityResponseType> {
    return this.http.put<ITemplateFormation>(
      `${this.resourceUrl}/${getTemplateFormationIdentifier(templateFormation) as number}`,
      templateFormation,
      { observe: 'response' }
    );
  }

  partialUpdate(templateFormation: ITemplateFormation): Observable<EntityResponseType> {
    return this.http.patch<ITemplateFormation>(
      `${this.resourceUrl}/${getTemplateFormationIdentifier(templateFormation) as number}`,
      templateFormation,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITemplateFormation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITemplateFormation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTemplateFormationToCollectionIfMissing(
    templateFormationCollection: ITemplateFormation[],
    ...templateFormationsToCheck: (ITemplateFormation | null | undefined)[]
  ): ITemplateFormation[] {
    const templateFormations: ITemplateFormation[] = templateFormationsToCheck.filter(isPresent);
    if (templateFormations.length > 0) {
      const templateFormationCollectionIdentifiers = templateFormationCollection.map(
        templateFormationItem => getTemplateFormationIdentifier(templateFormationItem)!
      );
      const templateFormationsToAdd = templateFormations.filter(templateFormationItem => {
        const templateFormationIdentifier = getTemplateFormationIdentifier(templateFormationItem);
        if (templateFormationIdentifier == null || templateFormationCollectionIdentifiers.includes(templateFormationIdentifier)) {
          return false;
        }
        templateFormationCollectionIdentifiers.push(templateFormationIdentifier);
        return true;
      });
      return [...templateFormationsToAdd, ...templateFormationCollection];
    }
    return templateFormationCollection;
  }
}
