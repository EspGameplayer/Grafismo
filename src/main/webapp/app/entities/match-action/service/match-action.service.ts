import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMatchAction, getMatchActionIdentifier } from '../match-action.model';

export type EntityResponseType = HttpResponse<IMatchAction>;
export type EntityArrayResponseType = HttpResponse<IMatchAction[]>;

@Injectable({ providedIn: 'root' })
export class MatchActionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/match-actions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(matchAction: IMatchAction): Observable<EntityResponseType> {
    return this.http.post<IMatchAction>(this.resourceUrl, matchAction, { observe: 'response' });
  }

  update(matchAction: IMatchAction): Observable<EntityResponseType> {
    return this.http.put<IMatchAction>(`${this.resourceUrl}/${getMatchActionIdentifier(matchAction) as number}`, matchAction, {
      observe: 'response',
    });
  }

  partialUpdate(matchAction: IMatchAction): Observable<EntityResponseType> {
    return this.http.patch<IMatchAction>(`${this.resourceUrl}/${getMatchActionIdentifier(matchAction) as number}`, matchAction, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMatchAction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMatchAction[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMatchActionToCollectionIfMissing(
    matchActionCollection: IMatchAction[],
    ...matchActionsToCheck: (IMatchAction | null | undefined)[]
  ): IMatchAction[] {
    const matchActions: IMatchAction[] = matchActionsToCheck.filter(isPresent);
    if (matchActions.length > 0) {
      const matchActionCollectionIdentifiers = matchActionCollection.map(matchActionItem => getMatchActionIdentifier(matchActionItem)!);
      const matchActionsToAdd = matchActions.filter(matchActionItem => {
        const matchActionIdentifier = getMatchActionIdentifier(matchActionItem);
        if (matchActionIdentifier == null || matchActionCollectionIdentifiers.includes(matchActionIdentifier)) {
          return false;
        }
        matchActionCollectionIdentifiers.push(matchActionIdentifier);
        return true;
      });
      return [...matchActionsToAdd, ...matchActionCollection];
    }
    return matchActionCollection;
  }
}
