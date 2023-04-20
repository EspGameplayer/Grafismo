import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { BroadcastPersonnelMemberRole } from 'app/entities/enumerations/broadcast-personnel-member-role.model';
import { IBroadcastPersonnelMember, BroadcastPersonnelMember } from '../broadcast-personnel-member.model';

import { BroadcastPersonnelMemberService } from './broadcast-personnel-member.service';

describe('BroadcastPersonnelMember Service', () => {
  let service: BroadcastPersonnelMemberService;
  let httpMock: HttpTestingController;
  let elemDefault: IBroadcastPersonnelMember;
  let expectedResult: IBroadcastPersonnelMember | IBroadcastPersonnelMember[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BroadcastPersonnelMemberService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      role: BroadcastPersonnelMemberRole.NARRATOR,
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

    it('should create a BroadcastPersonnelMember', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new BroadcastPersonnelMember()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BroadcastPersonnelMember', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          role: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BroadcastPersonnelMember', () => {
      const patchObject = Object.assign(
        {
          role: 'BBBBBB',
        },
        new BroadcastPersonnelMember()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BroadcastPersonnelMember', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          role: 'BBBBBB',
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

    it('should delete a BroadcastPersonnelMember', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBroadcastPersonnelMemberToCollectionIfMissing', () => {
      it('should add a BroadcastPersonnelMember to an empty array', () => {
        const broadcastPersonnelMember: IBroadcastPersonnelMember = { id: 123 };
        expectedResult = service.addBroadcastPersonnelMemberToCollectionIfMissing([], broadcastPersonnelMember);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(broadcastPersonnelMember);
      });

      it('should not add a BroadcastPersonnelMember to an array that contains it', () => {
        const broadcastPersonnelMember: IBroadcastPersonnelMember = { id: 123 };
        const broadcastPersonnelMemberCollection: IBroadcastPersonnelMember[] = [
          {
            ...broadcastPersonnelMember,
          },
          { id: 456 },
        ];
        expectedResult = service.addBroadcastPersonnelMemberToCollectionIfMissing(
          broadcastPersonnelMemberCollection,
          broadcastPersonnelMember
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BroadcastPersonnelMember to an array that doesn't contain it", () => {
        const broadcastPersonnelMember: IBroadcastPersonnelMember = { id: 123 };
        const broadcastPersonnelMemberCollection: IBroadcastPersonnelMember[] = [{ id: 456 }];
        expectedResult = service.addBroadcastPersonnelMemberToCollectionIfMissing(
          broadcastPersonnelMemberCollection,
          broadcastPersonnelMember
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(broadcastPersonnelMember);
      });

      it('should add only unique BroadcastPersonnelMember to an array', () => {
        const broadcastPersonnelMemberArray: IBroadcastPersonnelMember[] = [{ id: 123 }, { id: 456 }, { id: 52015 }];
        const broadcastPersonnelMemberCollection: IBroadcastPersonnelMember[] = [{ id: 123 }];
        expectedResult = service.addBroadcastPersonnelMemberToCollectionIfMissing(
          broadcastPersonnelMemberCollection,
          ...broadcastPersonnelMemberArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const broadcastPersonnelMember: IBroadcastPersonnelMember = { id: 123 };
        const broadcastPersonnelMember2: IBroadcastPersonnelMember = { id: 456 };
        expectedResult = service.addBroadcastPersonnelMemberToCollectionIfMissing([], broadcastPersonnelMember, broadcastPersonnelMember2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(broadcastPersonnelMember);
        expect(expectedResult).toContain(broadcastPersonnelMember2);
      });

      it('should accept null and undefined values', () => {
        const broadcastPersonnelMember: IBroadcastPersonnelMember = { id: 123 };
        expectedResult = service.addBroadcastPersonnelMemberToCollectionIfMissing([], null, broadcastPersonnelMember, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(broadcastPersonnelMember);
      });

      it('should return initial array if no BroadcastPersonnelMember is added', () => {
        const broadcastPersonnelMemberCollection: IBroadcastPersonnelMember[] = [{ id: 123 }];
        expectedResult = service.addBroadcastPersonnelMemberToCollectionIfMissing(broadcastPersonnelMemberCollection, undefined, null);
        expect(expectedResult).toEqual(broadcastPersonnelMemberCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
