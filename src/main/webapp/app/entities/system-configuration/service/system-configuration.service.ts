import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISystemConfiguration, getSystemConfigurationIdentifier } from '../system-configuration.model';

export type EntityResponseType = HttpResponse<ISystemConfiguration>;
export type EntityArrayResponseType = HttpResponse<ISystemConfiguration[]>;

@Injectable({ providedIn: 'root' })
export class SystemConfigurationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/system-configurations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(systemConfiguration: ISystemConfiguration): Observable<EntityResponseType> {
    return this.http.post<ISystemConfiguration>(this.resourceUrl, systemConfiguration, { observe: 'response' });
  }

  update(systemConfiguration: ISystemConfiguration): Observable<EntityResponseType> {
    return this.http.put<ISystemConfiguration>(
      `${this.resourceUrl}/${getSystemConfigurationIdentifier(systemConfiguration) as number}`,
      systemConfiguration,
      { observe: 'response' }
    );
  }

  partialUpdate(systemConfiguration: ISystemConfiguration): Observable<EntityResponseType> {
    return this.http.patch<ISystemConfiguration>(
      `${this.resourceUrl}/${getSystemConfigurationIdentifier(systemConfiguration) as number}`,
      systemConfiguration,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISystemConfiguration>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISystemConfiguration[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSystemConfigurationToCollectionIfMissing(
    systemConfigurationCollection: ISystemConfiguration[],
    ...systemConfigurationsToCheck: (ISystemConfiguration | null | undefined)[]
  ): ISystemConfiguration[] {
    const systemConfigurations: ISystemConfiguration[] = systemConfigurationsToCheck.filter(isPresent);
    if (systemConfigurations.length > 0) {
      const systemConfigurationCollectionIdentifiers = systemConfigurationCollection.map(
        systemConfigurationItem => getSystemConfigurationIdentifier(systemConfigurationItem)!
      );
      const systemConfigurationsToAdd = systemConfigurations.filter(systemConfigurationItem => {
        const systemConfigurationIdentifier = getSystemConfigurationIdentifier(systemConfigurationItem);
        if (systemConfigurationIdentifier == null || systemConfigurationCollectionIdentifiers.includes(systemConfigurationIdentifier)) {
          return false;
        }
        systemConfigurationCollectionIdentifiers.push(systemConfigurationIdentifier);
        return true;
      });
      return [...systemConfigurationsToAdd, ...systemConfigurationCollection];
    }
    return systemConfigurationCollection;
  }
}
