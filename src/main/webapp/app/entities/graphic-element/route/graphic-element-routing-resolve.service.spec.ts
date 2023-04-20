import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IGraphicElement, GraphicElement } from '../graphic-element.model';
import { GraphicElementService } from '../service/graphic-element.service';

import { GraphicElementRoutingResolveService } from './graphic-element-routing-resolve.service';

describe('GraphicElement routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: GraphicElementRoutingResolveService;
  let service: GraphicElementService;
  let resultGraphicElement: IGraphicElement | undefined;

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
    routingResolveService = TestBed.inject(GraphicElementRoutingResolveService);
    service = TestBed.inject(GraphicElementService);
    resultGraphicElement = undefined;
  });

  describe('resolve', () => {
    it('should return IGraphicElement returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultGraphicElement = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultGraphicElement).toEqual({ id: 123 });
    });

    it('should return new IGraphicElement if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultGraphicElement = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultGraphicElement).toEqual(new GraphicElement());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as GraphicElement })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultGraphicElement = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultGraphicElement).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
