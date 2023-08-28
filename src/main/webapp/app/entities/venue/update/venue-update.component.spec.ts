import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VenueService } from '../service/venue.service';
import { IVenue, Venue } from '../venue.model';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';

import { VenueUpdateComponent } from './venue-update.component';

describe('Venue Management Update Component', () => {
  let comp: VenueUpdateComponent;
  let fixture: ComponentFixture<VenueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let venueService: VenueService;
  let locationService: LocationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [VenueUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(VenueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VenueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    venueService = TestBed.inject(VenueService);
    locationService = TestBed.inject(LocationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Location query and add missing value', () => {
      const venue: IVenue = { id: 456 };
      const location: ILocation = { id: 93312 };
      venue.location = location;

      const locationCollection: ILocation[] = [{ id: 94129 }];
      jest.spyOn(locationService, 'query').mockReturnValue(of(new HttpResponse({ body: locationCollection })));
      const additionalLocations = [location];
      const expectedCollection: ILocation[] = [...additionalLocations, ...locationCollection];
      jest.spyOn(locationService, 'addLocationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ venue });
      comp.ngOnInit();

      expect(locationService.query).toHaveBeenCalled();
      expect(locationService.addLocationToCollectionIfMissing).toHaveBeenCalledWith(locationCollection, ...additionalLocations);
      expect(comp.locationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const venue: IVenue = { id: 456 };
      const location: ILocation = { id: 23525 };
      venue.location = location;

      activatedRoute.data = of({ venue });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(venue));
      expect(comp.locationsSharedCollection).toContain(location);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Venue>>();
      const venue = { id: 123 };
      jest.spyOn(venueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ venue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: venue }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(venueService.update).toHaveBeenCalledWith(venue);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Venue>>();
      const venue = new Venue();
      jest.spyOn(venueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ venue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: venue }));
      saveSubject.complete();

      // THEN
      expect(venueService.create).toHaveBeenCalledWith(venue);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Venue>>();
      const venue = { id: 123 };
      jest.spyOn(venueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ venue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(venueService.update).toHaveBeenCalledWith(venue);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackLocationById', () => {
      it('Should return tracked Location primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLocationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
