import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBroadcast, Broadcast } from '../broadcast.model';

import { BroadcastService } from './broadcast.service';

describe('Broadcast Service', () => {
  let service: BroadcastService;
  let httpMock: HttpTestingController;
  let elemDefault: IBroadcast;
  let expectedResult: IBroadcast | IBroadcast[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BroadcastService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
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

    it('should create a Broadcast', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Broadcast()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Broadcast', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should partial update a Broadcast', () => {
      const patchObject = Object.assign(
        {
          miscData: 'BBBBBB',
        },
        new Broadcast()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Broadcast', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should delete a Broadcast', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBroadcastToCollectionIfMissing', () => {
      it('should add a Broadcast to an empty array', () => {
        const broadcast: IBroadcast = { id: 123 };
        expectedResult = service.addBroadcastToCollectionIfMissing([], broadcast);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(broadcast);
      });

      it('should not add a Broadcast to an array that contains it', () => {
        const broadcast: IBroadcast = { id: 123 };
        const broadcastCollection: IBroadcast[] = [
          {
            ...broadcast,
          },
          { id: 456 },
        ];
        expectedResult = service.addBroadcastToCollectionIfMissing(broadcastCollection, broadcast);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Broadcast to an array that doesn't contain it", () => {
        const broadcast: IBroadcast = { id: 123 };
        const broadcastCollection: IBroadcast[] = [{ id: 456 }];
        expectedResult = service.addBroadcastToCollectionIfMissing(broadcastCollection, broadcast);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(broadcast);
      });

      it('should add only unique Broadcast to an array', () => {
        const broadcastArray: IBroadcast[] = [{ id: 123 }, { id: 456 }, { id: 94108 }];
        const broadcastCollection: IBroadcast[] = [{ id: 123 }];
        expectedResult = service.addBroadcastToCollectionIfMissing(broadcastCollection, ...broadcastArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const broadcast: IBroadcast = { id: 123 };
        const broadcast2: IBroadcast = { id: 456 };
        expectedResult = service.addBroadcastToCollectionIfMissing([], broadcast, broadcast2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(broadcast);
        expect(expectedResult).toContain(broadcast2);
      });

      it('should accept null and undefined values', () => {
        const broadcast: IBroadcast = { id: 123 };
        expectedResult = service.addBroadcastToCollectionIfMissing([], null, broadcast, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(broadcast);
      });

      it('should return initial array if no Broadcast is added', () => {
        const broadcastCollection: IBroadcast[] = [{ id: 123 }];
        expectedResult = service.addBroadcastToCollectionIfMissing(broadcastCollection, undefined, null);
        expect(expectedResult).toEqual(broadcastCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
