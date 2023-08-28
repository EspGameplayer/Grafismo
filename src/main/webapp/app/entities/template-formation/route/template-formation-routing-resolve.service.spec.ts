import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITemplateFormation, TemplateFormation } from '../template-formation.model';
import { TemplateFormationService } from '../service/template-formation.service';

import { TemplateFormationRoutingResolveService } from './template-formation-routing-resolve.service';

describe('TemplateFormation routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TemplateFormationRoutingResolveService;
  let service: TemplateFormationService;
  let resultTemplateFormation: ITemplateFormation | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(TemplateFormationRoutingResolveService);
    service = TestBed.inject(TemplateFormationService);
    resultTemplateFormation = undefined;
  });

  describe('resolve', () => {
    it('should return ITemplateFormation returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTemplateFormation = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTemplateFormation).toEqual({ id: 123 });
    });

    it('should return new ITemplateFormation if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTemplateFormation = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTemplateFormation).toEqual(new TemplateFormation());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TemplateFormation })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTemplateFormation = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTemplateFormation).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
