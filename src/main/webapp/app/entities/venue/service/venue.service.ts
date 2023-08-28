import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVenue, getVenueIdentifier } from '../venue.model';

export type EntityResponseType = HttpResponse<IVenue>;
export type EntityArrayResponseType = HttpResponse<IVenue[]>;

@Injectable({ providedIn: 'root' })
export class VenueService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/venues');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(venue: IVenue): Observable<EntityResponseType> {
    return this.http.post<IVenue>(this.resourceUrl, venue, { observe: 'response' });
  }

  update(venue: IVenue): Observable<EntityResponseType> {
    return this.http.put<IVenue>(`${this.resourceUrl}/${getVenueIdentifier(venue) as number}`, venue, { observe: 'response' });
  }

  partialUpdate(venue: IVenue): Observable<EntityResponseType> {
    return this.http.patch<IVenue>(`${this.resourceUrl}/${getVenueIdentifier(venue) as number}`, venue, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVenue>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVenue[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVenueToCollectionIfMissing(venueCollection: IVenue[], ...venuesToCheck: (IVenue | null | undefined)[]): IVenue[] {
    const venues: IVenue[] = venuesToCheck.filter(isPresent);
    if (venues.length > 0) {
      const venueCollectionIdentifiers = venueCollection.map(venueItem => getVenueIdentifier(venueItem)!);
      const venuesToAdd = venues.filter(venueItem => {
        const venueIdentifier = getVenueIdentifier(venueItem);
        if (venueIdentifier == null || venueCollectionIdentifiers.includes(venueIdentifier)) {
          return false;
        }
        venueCollectionIdentifiers.push(venueIdentifier);
        return true;
      });
      return [...venuesToAdd, ...venueCollection];
    }
    return venueCollection;
  }
}
