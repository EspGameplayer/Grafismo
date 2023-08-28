import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { StaffMemberRole } from 'app/entities/enumerations/staff-member-role.model';
import { IStaffMember, StaffMember } from '../staff-member.model';

import { StaffMemberService } from './staff-member.service';

describe('StaffMember Service', () => {
  let service: StaffMemberService;
  let httpMock: HttpTestingController;
  let elemDefault: IStaffMember;
  let expectedResult: IStaffMember | IStaffMember[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StaffMemberService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      graphicsName: 'AAAAAAA',
      longGraphicsName: 'AAAAAAA',
      defaultRole: StaffMemberRole.DT,
      contractUntil: 'AAAAAAA',
      retirementDate: 'AAAAAAA',
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

    it('should create a StaffMember', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new StaffMember()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StaffMember', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          graphicsName: 'BBBBBB',
          longGraphicsName: 'BBBBBB',
          defaultRole: 'BBBBBB',
          contractUntil: 'BBBBBB',
          retirementDate: 'BBBBBB',
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

    it('should partial update a StaffMember', () => {
      const patchObject = Object.assign(
        {
          defaultRole: 'BBBBBB',
          contractUntil: 'BBBBBB',
          retirementDate: 'BBBBBB',
          miscData: 'BBBBBB',
        },
        new StaffMember()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of StaffMember', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          graphicsName: 'BBBBBB',
          longGraphicsName: 'BBBBBB',
          defaultRole: 'BBBBBB',
          contractUntil: 'BBBBBB',
          retirementDate: 'BBBBBB',
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

    it('should delete a StaffMember', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addStaffMemberToCollectionIfMissing', () => {
      it('should add a StaffMember to an empty array', () => {
        const staffMember: IStaffMember = { id: 123 };
        expectedResult = service.addStaffMemberToCollectionIfMissing([], staffMember);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(staffMember);
      });

      it('should not add a StaffMember to an array that contains it', () => {
        const staffMember: IStaffMember = { id: 123 };
        const staffMemberCollection: IStaffMember[] = [
          {
            ...staffMember,
          },
          { id: 456 },
        ];
        expectedResult = service.addStaffMemberToCollectionIfMissing(staffMemberCollection, staffMember);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StaffMember to an array that doesn't contain it", () => {
        const staffMember: IStaffMember = { id: 123 };
        const staffMemberCollection: IStaffMember[] = [{ id: 456 }];
        expectedResult = service.addStaffMemberToCollectionIfMissing(staffMemberCollection, staffMember);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(staffMember);
      });

      it('should add only unique StaffMember to an array', () => {
        const staffMemberArray: IStaffMember[] = [{ id: 123 }, { id: 456 }, { id: 88093 }];
        const staffMemberCollection: IStaffMember[] = [{ id: 123 }];
        expectedResult = service.addStaffMemberToCollectionIfMissing(staffMemberCollection, ...staffMemberArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const staffMember: IStaffMember = { id: 123 };
        const staffMember2: IStaffMember = { id: 456 };
        expectedResult = service.addStaffMemberToCollectionIfMissing([], staffMember, staffMember2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(staffMember);
        expect(expectedResult).toContain(staffMember2);
      });

      it('should accept null and undefined values', () => {
        const staffMember: IStaffMember = { id: 123 };
        expectedResult = service.addStaffMemberToCollectionIfMissing([], null, staffMember, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(staffMember);
      });

      it('should return initial array if no StaffMember is added', () => {
        const staffMemberCollection: IStaffMember[] = [{ id: 123 }];
        expectedResult = service.addStaffMemberToCollectionIfMissing(staffMemberCollection, undefined, null);
        expect(expectedResult).toEqual(staffMemberCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
