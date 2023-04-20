import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBroadcastPersonnelMember, getBroadcastPersonnelMemberIdentifier } from '../broadcast-personnel-member.model';

export type EntityResponseType = HttpResponse<IBroadcastPersonnelMember>;
export type EntityArrayResponseType = HttpResponse<IBroadcastPersonnelMember[]>;

@Injectable({ providedIn: 'root' })
export class BroadcastPersonnelMemberService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/broadcast-personnel-members');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(broadcastPersonnelMember: IBroadcastPersonnelMember): Observable<EntityResponseType> {
    return this.http.post<IBroadcastPersonnelMember>(this.resourceUrl, broadcastPersonnelMember, { observe: 'response' });
  }

  update(broadcastPersonnelMember: IBroadcastPersonnelMember): Observable<EntityResponseType> {
    return this.http.put<IBroadcastPersonnelMember>(
      `${this.resourceUrl}/${getBroadcastPersonnelMemberIdentifier(broadcastPersonnelMember) as number}`,
      broadcastPersonnelMember,
      { observe: 'response' }
    );
  }

  partialUpdate(broadcastPersonnelMember: IBroadcastPersonnelMember): Observable<EntityResponseType> {
    return this.http.patch<IBroadcastPersonnelMember>(
      `${this.resourceUrl}/${getBroadcastPersonnelMemberIdentifier(broadcastPersonnelMember) as number}`,
      broadcastPersonnelMember,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBroadcastPersonnelMember>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBroadcastPersonnelMember[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBroadcastPersonnelMemberToCollectionIfMissing(
    broadcastPersonnelMemberCollection: IBroadcastPersonnelMember[],
    ...broadcastPersonnelMembersToCheck: (IBroadcastPersonnelMember | null | undefined)[]
  ): IBroadcastPersonnelMember[] {
    const broadcastPersonnelMembers: IBroadcastPersonnelMember[] = broadcastPersonnelMembersToCheck.filter(isPresent);
    if (broadcastPersonnelMembers.length > 0) {
      const broadcastPersonnelMemberCollectionIdentifiers = broadcastPersonnelMemberCollection.map(
        broadcastPersonnelMemberItem => getBroadcastPersonnelMemberIdentifier(broadcastPersonnelMemberItem)!
      );
      const broadcastPersonnelMembersToAdd = broadcastPersonnelMembers.filter(broadcastPersonnelMemberItem => {
        const broadcastPersonnelMemberIdentifier = getBroadcastPersonnelMemberIdentifier(broadcastPersonnelMemberItem);
        if (
          broadcastPersonnelMemberIdentifier == null ||
          broadcastPersonnelMemberCollectionIdentifiers.includes(broadcastPersonnelMemberIdentifier)
        ) {
          return false;
        }
        broadcastPersonnelMemberCollectionIdentifiers.push(broadcastPersonnelMemberIdentifier);
        return true;
      });
      return [...broadcastPersonnelMembersToAdd, ...broadcastPersonnelMemberCollection];
    }
    return broadcastPersonnelMemberCollection;
  }
}
