import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IBroadcastPersonnelMember, BroadcastPersonnelMember } from '../broadcast-personnel-member.model';
import { BroadcastPersonnelMemberService } from '../service/broadcast-personnel-member.service';

import { BroadcastPersonnelMemberRoutingResolveService } from './broadcast-personnel-member-routing-resolve.service';

describe('BroadcastPersonnelMember routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: BroadcastPersonnelMemberRoutingResolveService;
  let service: BroadcastPersonnelMemberService;
  let resultBroadcastPersonnelMember: IBroadcastPersonnelMember | undefined;

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
    routingResolveService = TestBed.inject(BroadcastPersonnelMemberRoutingResolveService);
    service = TestBed.inject(BroadcastPersonnelMemberService);
    resultBroadcastPersonnelMember = undefined;
  });

  describe('resolve', () => {
    it('should return IBroadcastPersonnelMember returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBroadcastPersonnelMember = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBroadcastPersonnelMember).toEqual({ id: 123 });
    });

    it('should return new IBroadcastPersonnelMember if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBroadcastPersonnelMember = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBroadcastPersonnelMember).toEqual(new BroadcastPersonnelMember());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as BroadcastPersonnelMember })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBroadcastPersonnelMember = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBroadcastPersonnelMember).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
