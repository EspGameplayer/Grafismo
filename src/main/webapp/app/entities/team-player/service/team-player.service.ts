import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITeamPlayer, getTeamPlayerIdentifier } from '../team-player.model';

export type EntityResponseType = HttpResponse<ITeamPlayer>;
export type EntityArrayResponseType = HttpResponse<ITeamPlayer[]>;

@Injectable({ providedIn: 'root' })
export class TeamPlayerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/team-players');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(teamPlayer: ITeamPlayer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamPlayer);
    return this.http
      .post<ITeamPlayer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(teamPlayer: ITeamPlayer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamPlayer);
    return this.http
      .put<ITeamPlayer>(`${this.resourceUrl}/${getTeamPlayerIdentifier(teamPlayer) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(teamPlayer: ITeamPlayer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamPlayer);
    return this.http
      .patch<ITeamPlayer>(`${this.resourceUrl}/${getTeamPlayerIdentifier(teamPlayer) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITeamPlayer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITeamPlayer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTeamPlayerToCollectionIfMissing(
    teamPlayerCollection: ITeamPlayer[],
    ...teamPlayersToCheck: (ITeamPlayer | null | undefined)[]
  ): ITeamPlayer[] {
    const teamPlayers: ITeamPlayer[] = teamPlayersToCheck.filter(isPresent);
    if (teamPlayers.length > 0) {
      const teamPlayerCollectionIdentifiers = teamPlayerCollection.map(teamPlayerItem => getTeamPlayerIdentifier(teamPlayerItem)!);
      const teamPlayersToAdd = teamPlayers.filter(teamPlayerItem => {
        const teamPlayerIdentifier = getTeamPlayerIdentifier(teamPlayerItem);
        if (teamPlayerIdentifier == null || teamPlayerCollectionIdentifiers.includes(teamPlayerIdentifier)) {
          return false;
        }
        teamPlayerCollectionIdentifiers.push(teamPlayerIdentifier);
        return true;
      });
      return [...teamPlayersToAdd, ...teamPlayerCollection];
    }
    return teamPlayerCollection;
  }

  protected convertDateFromClient(teamPlayer: ITeamPlayer): ITeamPlayer {
    return Object.assign({}, teamPlayer, {
      sinceDate: teamPlayer.sinceDate?.isValid() ? teamPlayer.sinceDate.format(DATE_FORMAT) : undefined,
      untilDate: teamPlayer.untilDate?.isValid() ? teamPlayer.untilDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.sinceDate = res.body.sinceDate ? dayjs(res.body.sinceDate) : undefined;
      res.body.untilDate = res.body.untilDate ? dayjs(res.body.untilDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((teamPlayer: ITeamPlayer) => {
        teamPlayer.sinceDate = teamPlayer.sinceDate ? dayjs(teamPlayer.sinceDate) : undefined;
        teamPlayer.untilDate = teamPlayer.untilDate ? dayjs(teamPlayer.untilDate) : undefined;
      });
    }
    return res;
  }
}
