import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBroadcast, getBroadcastIdentifier } from '../broadcast.model';

export type EntityResponseType = HttpResponse<IBroadcast>;
export type EntityArrayResponseType = HttpResponse<IBroadcast[]>;

@Injectable({ providedIn: 'root' })
export class BroadcastService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/broadcasts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(broadcast: IBroadcast): Observable<EntityResponseType> {
    return this.http.post<IBroadcast>(this.resourceUrl, broadcast, { observe: 'response' });
  }

  update(broadcast: IBroadcast): Observable<EntityResponseType> {
    return this.http.put<IBroadcast>(`${this.resourceUrl}/${getBroadcastIdentifier(broadcast) as number}`, broadcast, {
      observe: 'response',
    });
  }

  partialUpdate(broadcast: IBroadcast): Observable<EntityResponseType> {
    return this.http.patch<IBroadcast>(`${this.resourceUrl}/${getBroadcastIdentifier(broadcast) as number}`, broadcast, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBroadcast>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBroadcast[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBroadcastToCollectionIfMissing(
    broadcastCollection: IBroadcast[],
    ...broadcastsToCheck: (IBroadcast | null | undefined)[]
  ): IBroadcast[] {
    const broadcasts: IBroadcast[] = broadcastsToCheck.filter(isPresent);
    if (broadcasts.length > 0) {
      const broadcastCollectionIdentifiers = broadcastCollection.map(broadcastItem => getBroadcastIdentifier(broadcastItem)!);
      const broadcastsToAdd = broadcasts.filter(broadcastItem => {
        const broadcastIdentifier = getBroadcastIdentifier(broadcastItem);
        if (broadcastIdentifier == null || broadcastCollectionIdentifiers.includes(broadcastIdentifier)) {
          return false;
        }
        broadcastCollectionIdentifiers.push(broadcastIdentifier);
        return true;
      });
      return [...broadcastsToAdd, ...broadcastCollection];
    }
    return broadcastCollection;
  }
}
