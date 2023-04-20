import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVenue, Venue } from '../venue.model';

import { VenueService } from './venue.service';

describe('Venue Service', () => {
  let service: VenueService;
  let httpMock: HttpTestingController;
  let elemDefault: IVenue;
  let expectedResult: IVenue | IVenue[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(VenueService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      graphicsName: 'AAAAAAA',
      longGraphicsName: 'AAAAAAA',
      capacity: 0,
      openingDate: 'AAAAAAA',
      fieldSize: 'AAAAAAA',
      isArtificialGrass: 0,
      details: 'AAAAAAA',
      miscData: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Venue', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Venue()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Venue', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          graphicsName: 'BBBBBB',
          longGraphicsName: 'BBBBBB',
          capacity: 1,
          openingDate: 'BBBBBB',
          fieldSize: 'BBBBBB',
          isArtificialGrass: 1,
          details: 'BBBBBB',
          miscData: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Venue', () => {
      const patchObject = Object.assign(
        {
          graphicsName: 'BBBBBB',
          capacity: 1,
          fieldSize: 'BBBBBB',
        },
        new Venue()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Venue', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          graphicsName: 'BBBBBB',
          longGraphicsName: 'BBBBBB',
          capacity: 1,
          openingDate: 'BBBBBB',
          fieldSize: 'BBBBBB',
          isArtificialGrass: 1,
          details: 'BBBBBB',
          miscData: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Venue', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addVenueToCollectionIfMissing', () => {
      it('should add a Venue to an empty array', () => {
        const venue: IVenue = { id: 123 };
        expectedResult = service.addVenueToCollectionIfMissing([], venue);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(venue);
      });

      it('should not add a Venue to an array that contains it', () => {
        const venue: IVenue = { id: 123 };
        const venueCollection: IVenue[] = [
          {
            ...venue,
          },
          { id: 456 },
        ];
        expectedResult = service.addVenueToCollectionIfMissing(venueCollection, venue);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Venue to an array that doesn't contain it", () => {
        const venue: IVenue = { id: 123 };
        const venueCollection: IVenue[] = [{ id: 456 }];
        expectedResult = service.addVenueToCollectionIfMissing(venueCollection, venue);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(venue);
      });

      it('should add only unique Venue to an array', () => {
        const venueArray: IVenue[] = [{ id: 123 }, { id: 456 }, { id: 17639 }];
        const venueCollection: IVenue[] = [{ id: 123 }];
        expectedResult = service.addVenueToCollectionIfMissing(venueCollection, ...venueArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const venue: IVenue = { id: 123 };
        const venue2: IVenue = { id: 456 };
        expectedResult = service.addVenueToCollectionIfMissing([], venue, venue2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(venue);
        expect(expectedResult).toContain(venue2);
      });

      it('should accept null and undefined values', () => {
        const venue: IVenue = { id: 123 };
        expectedResult = service.addVenueToCollectionIfMissing([], null, venue, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(venue);
      });

      it('should return initial array if no Venue is added', () => {
        const venueCollection: IVenue[] = [{ id: 123 }];
        expectedResult = service.addVenueToCollectionIfMissing(venueCollection, undefined, null);
        expect(expectedResult).toEqual(venueCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
