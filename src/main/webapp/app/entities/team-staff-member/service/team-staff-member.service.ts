import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITeamStaffMember, getTeamStaffMemberIdentifier } from '../team-staff-member.model';

export type EntityResponseType = HttpResponse<ITeamStaffMember>;
export type EntityArrayResponseType = HttpResponse<ITeamStaffMember[]>;

@Injectable({ providedIn: 'root' })
export class TeamStaffMemberService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/team-staff-members');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(teamStaffMember: ITeamStaffMember): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamStaffMember);
    return this.http
      .post<ITeamStaffMember>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(teamStaffMember: ITeamStaffMember): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamStaffMember);
    return this.http
      .put<ITeamStaffMember>(`${this.resourceUrl}/${getTeamStaffMemberIdentifier(teamStaffMember) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(teamStaffMember: ITeamStaffMember): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamStaffMember);
    return this.http
      .patch<ITeamStaffMember>(`${this.resourceUrl}/${getTeamStaffMemberIdentifier(teamStaffMember) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITeamStaffMember>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITeamStaffMember[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTeamStaffMemberToCollectionIfMissing(
    teamStaffMemberCollection: ITeamStaffMember[],
    ...teamStaffMembersToCheck: (ITeamStaffMember | null | undefined)[]
  ): ITeamStaffMember[] {
    const teamStaffMembers: ITeamStaffMember[] = teamStaffMembersToCheck.filter(isPresent);
    if (teamStaffMembers.length > 0) {
      const teamStaffMemberCollectionIdentifiers = teamStaffMemberCollection.map(
        teamStaffMemberItem => getTeamStaffMemberIdentifier(teamStaffMemberItem)!
      );
      const teamStaffMembersToAdd = teamStaffMembers.filter(teamStaffMemberItem => {
        const teamStaffMemberIdentifier = getTeamStaffMemberIdentifier(teamStaffMemberItem);
        if (teamStaffMemberIdentifier == null || teamStaffMemberCollectionIdentifiers.includes(teamStaffMemberIdentifier)) {
          return false;
        }
        teamStaffMemberCollectionIdentifiers.push(teamStaffMemberIdentifier);
        return true;
      });
      return [...teamStaffMembersToAdd, ...teamStaffMemberCollection];
    }
    return teamStaffMemberCollection;
  }

  protected convertDateFromClient(teamStaffMember: ITeamStaffMember): ITeamStaffMember {
    return Object.assign({}, teamStaffMember, {
      sinceDate: teamStaffMember.sinceDate?.isValid() ? teamStaffMember.sinceDate.format(DATE_FORMAT) : undefined,
      untilDate: teamStaffMember.untilDate?.isValid() ? teamStaffMember.untilDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((teamStaffMember: ITeamStaffMember) => {
        teamStaffMember.sinceDate = teamStaffMember.sinceDate ? dayjs(teamStaffMember.sinceDate) : undefined;
        teamStaffMember.untilDate = teamStaffMember.untilDate ? dayjs(teamStaffMember.untilDate) : undefined;
      });
    }
    return res;
  }
}
