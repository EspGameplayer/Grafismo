import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMatchday, getMatchdayIdentifier } from '../matchday.model';

export type EntityResponseType = HttpResponse<IMatchday>;
export type EntityArrayResponseType = HttpResponse<IMatchday[]>;

@Injectable({ providedIn: 'root' })
export class MatchdayService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/matchdays');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(matchday: IMatchday): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(matchday);
    return this.http
      .post<IMatchday>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(matchday: IMatchday): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(matchday);
    return this.http
      .put<IMatchday>(`${this.resourceUrl}/${getMatchdayIdentifier(matchday) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(matchday: IMatchday): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(matchday);
    return this.http
      .patch<IMatchday>(`${this.resourceUrl}/${getMatchdayIdentifier(matchday) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMatchday>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMatchday[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMatchdayToCollectionIfMissing(matchdayCollection: IMatchday[], ...matchdaysToCheck: (IMatchday | null | undefined)[]): IMatchday[] {
    const matchdays: IMatchday[] = matchdaysToCheck.filter(isPresent);
    if (matchdays.length > 0) {
      const matchdayCollectionIdentifiers = matchdayCollection.map(matchdayItem => getMatchdayIdentifier(matchdayItem)!);
      const matchdaysToAdd = matchdays.filter(matchdayItem => {
        const matchdayIdentifier = getMatchdayIdentifier(matchdayItem);
        if (matchdayIdentifier == null || matchdayCollectionIdentifiers.includes(matchdayIdentifier)) {
          return false;
        }
        matchdayCollectionIdentifiers.push(matchdayIdentifier);
        return true;
      });
      return [...matchdaysToAdd, ...matchdayCollection];
    }
    return matchdayCollection;
  }

  protected convertDateFromClient(matchday: IMatchday): IMatchday {
    return Object.assign({}, matchday, {
      moment: matchday.moment?.isValid() ? matchday.moment.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.moment = res.body.moment ? dayjs(res.body.moment) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((matchday: IMatchday) => {
        matchday.moment = matchday.moment ? dayjs(matchday.moment) : undefined;
      });
    }
    return res;
  }
}
