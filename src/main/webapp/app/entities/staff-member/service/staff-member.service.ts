import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStaffMember, getStaffMemberIdentifier } from '../staff-member.model';

export type EntityResponseType = HttpResponse<IStaffMember>;
export type EntityArrayResponseType = HttpResponse<IStaffMember[]>;

@Injectable({ providedIn: 'root' })
export class StaffMemberService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/staff-members');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(staffMember: IStaffMember): Observable<EntityResponseType> {
    return this.http.post<IStaffMember>(this.resourceUrl, staffMember, { observe: 'response' });
  }

  update(staffMember: IStaffMember): Observable<EntityResponseType> {
    return this.http.put<IStaffMember>(`${this.resourceUrl}/${getStaffMemberIdentifier(staffMember) as number}`, staffMember, {
      observe: 'response',
    });
  }

  partialUpdate(staffMember: IStaffMember): Observable<EntityResponseType> {
    return this.http.patch<IStaffMember>(`${this.resourceUrl}/${getStaffMemberIdentifier(staffMember) as number}`, staffMember, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStaffMember>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStaffMember[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStaffMemberToCollectionIfMissing(
    staffMemberCollection: IStaffMember[],
    ...staffMembersToCheck: (IStaffMember | null | undefined)[]
  ): IStaffMember[] {
    const staffMembers: IStaffMember[] = staffMembersToCheck.filter(isPresent);
    if (staffMembers.length > 0) {
      const staffMemberCollectionIdentifiers = staffMemberCollection.map(staffMemberItem => getStaffMemberIdentifier(staffMemberItem)!);
      const staffMembersToAdd = staffMembers.filter(staffMemberItem => {
        const staffMemberIdentifier = getStaffMemberIdentifier(staffMemberItem);
        if (staffMemberIdentifier == null || staffMemberCollectionIdentifiers.includes(staffMemberIdentifier)) {
          return false;
        }
        staffMemberCollectionIdentifiers.push(staffMemberIdentifier);
        return true;
      });
      return [...staffMembersToAdd, ...staffMemberCollection];
    }
    return staffMemberCollection;
  }
}
