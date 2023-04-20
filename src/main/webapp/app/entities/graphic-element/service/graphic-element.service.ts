import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGraphicElement, getGraphicElementIdentifier } from '../graphic-element.model';

export type EntityResponseType = HttpResponse<IGraphicElement>;
export type EntityArrayResponseType = HttpResponse<IGraphicElement[]>;

@Injectable({ providedIn: 'root' })
export class GraphicElementService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/graphic-elements');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(graphicElement: IGraphicElement): Observable<EntityResponseType> {
    return this.http.post<IGraphicElement>(this.resourceUrl, graphicElement, { observe: 'response' });
  }

  update(graphicElement: IGraphicElement): Observable<EntityResponseType> {
    return this.http.put<IGraphicElement>(`${this.resourceUrl}/${getGraphicElementIdentifier(graphicElement) as number}`, graphicElement, {
      observe: 'response',
    });
  }

  partialUpdate(graphicElement: IGraphicElement): Observable<EntityResponseType> {
    return this.http.patch<IGraphicElement>(
      `${this.resourceUrl}/${getGraphicElementIdentifier(graphicElement) as number}`,
      graphicElement,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGraphicElement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGraphicElement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGraphicElementToCollectionIfMissing(
    graphicElementCollection: IGraphicElement[],
    ...graphicElementsToCheck: (IGraphicElement | null | undefined)[]
  ): IGraphicElement[] {
    const graphicElements: IGraphicElement[] = graphicElementsToCheck.filter(isPresent);
    if (graphicElements.length > 0) {
      const graphicElementCollectionIdentifiers = graphicElementCollection.map(
        graphicElementItem => getGraphicElementIdentifier(graphicElementItem)!
      );
      const graphicElementsToAdd = graphicElements.filter(graphicElementItem => {
        const graphicElementIdentifier = getGraphicElementIdentifier(graphicElementItem);
        if (graphicElementIdentifier == null || graphicElementCollectionIdentifiers.includes(graphicElementIdentifier)) {
          return false;
        }
        graphicElementCollectionIdentifiers.push(graphicElementIdentifier);
        return true;
      });
      return [...graphicElementsToAdd, ...graphicElementCollection];
    }
    return graphicElementCollection;
  }
}
