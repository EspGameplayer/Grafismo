import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMatchAction, MatchAction } from '../match-action.model';

import { MatchActionService } from './match-action.service';

describe('MatchAction Service', () => {
  let service: MatchActionService;
  let httpMock: HttpTestingController;
  let elemDefault: IMatchAction;
  let expectedResult: IMatchAction | IMatchAction[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MatchActionService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      timestamp: 'AAAAAAA',
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

    it('should create a MatchAction', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new MatchAction()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MatchAction', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          timestamp: 'BBBBBB',
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

    it('should partial update a MatchAction', () => {
      const patchObject = Object.assign(
        {
          timestamp: 'BBBBBB',
          details: 'BBBBBB',
        },
        new MatchAction()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MatchAction', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          timestamp: 'BBBBBB',
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

    it('should delete a MatchAction', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMatchActionToCollectionIfMissing', () => {
      it('should add a MatchAction to an empty array', () => {
        const matchAction: IMatchAction = { id: 123 };
        expectedResult = service.addMatchActionToCollectionIfMissing([], matchAction);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(matchAction);
      });

      it('should not add a MatchAction to an array that contains it', () => {
        const matchAction: IMatchAction = { id: 123 };
        const matchActionCollection: IMatchAction[] = [
          {
            ...matchAction,
          },
          { id: 456 },
        ];
        expectedResult = service.addMatchActionToCollectionIfMissing(matchActionCollection, matchAction);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MatchAction to an array that doesn't contain it", () => {
        const matchAction: IMatchAction = { id: 123 };
        const matchActionCollection: IMatchAction[] = [{ id: 456 }];
        expectedResult = service.addMatchActionToCollectionIfMissing(matchActionCollection, matchAction);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(matchAction);
      });

      it('should add only unique MatchAction to an array', () => {
        const matchActionArray: IMatchAction[] = [{ id: 123 }, { id: 456 }, { id: 77153 }];
        const matchActionCollection: IMatchAction[] = [{ id: 123 }];
        expectedResult = service.addMatchActionToCollectionIfMissing(matchActionCollection, ...matchActionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const matchAction: IMatchAction = { id: 123 };
        const matchAction2: IMatchAction = { id: 456 };
        expectedResult = service.addMatchActionToCollectionIfMissing([], matchAction, matchAction2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(matchAction);
        expect(expectedResult).toContain(matchAction2);
      });

      it('should accept null and undefined values', () => {
        const matchAction: IMatchAction = { id: 123 };
        expectedResult = service.addMatchActionToCollectionIfMissing([], null, matchAction, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(matchAction);
      });

      it('should return initial array if no MatchAction is added', () => {
        const matchActionCollection: IMatchAction[] = [{ id: 123 }];
        expectedResult = service.addMatchActionToCollectionIfMissing(matchActionCollection, undefined, null);
        expect(expectedResult).toEqual(matchActionCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
