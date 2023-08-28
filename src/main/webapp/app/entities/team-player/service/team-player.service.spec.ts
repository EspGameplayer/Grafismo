import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITeamPlayer, TeamPlayer } from '../team-player.model';

import { TeamPlayerService } from './team-player.service';

describe('TeamPlayer Service', () => {
  let service: TeamPlayerService;
  let httpMock: HttpTestingController;
  let elemDefault: ITeamPlayer;
  let expectedResult: ITeamPlayer | ITeamPlayer[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TeamPlayerService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      preferredShirtNumber: 0,
      isYouth: 0,
      sinceDate: currentDate,
      untilDate: currentDate,
      miscData: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          sinceDate: currentDate.format(DATE_FORMAT),
          untilDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TeamPlayer', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          sinceDate: currentDate.format(DATE_FORMAT),
          untilDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          sinceDate: currentDate,
          untilDate: currentDate,
        },
        returnedFromService
      );

      service.create(new TeamPlayer()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TeamPlayer', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          preferredShirtNumber: 1,
          isYouth: 1,
          sinceDate: currentDate.format(DATE_FORMAT),
          untilDate: currentDate.format(DATE_FORMAT),
          miscData: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          sinceDate: currentDate,
          untilDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TeamPlayer', () => {
      const patchObject = Object.assign(
        {
          isYouth: 1,
          sinceDate: currentDate.format(DATE_FORMAT),
        },
        new TeamPlayer()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          sinceDate: currentDate,
          untilDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TeamPlayer', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          preferredShirtNumber: 1,
          isYouth: 1,
          sinceDate: currentDate.format(DATE_FORMAT),
          untilDate: currentDate.format(DATE_FORMAT),
          miscData: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          sinceDate: currentDate,
          untilDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TeamPlayer', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTeamPlayerToCollectionIfMissing', () => {
      it('should add a TeamPlayer to an empty array', () => {
        const teamPlayer: ITeamPlayer = { id: 123 };
        expectedResult = service.addTeamPlayerToCollectionIfMissing([], teamPlayer);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(teamPlayer);
      });

      it('should not add a TeamPlayer to an array that contains it', () => {
        const teamPlayer: ITeamPlayer = { id: 123 };
        const teamPlayerCollection: ITeamPlayer[] = [
          {
            ...teamPlayer,
          },
          { id: 456 },
        ];
        expectedResult = service.addTeamPlayerToCollectionIfMissing(teamPlayerCollection, teamPlayer);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TeamPlayer to an array that doesn't contain it", () => {
        const teamPlayer: ITeamPlayer = { id: 123 };
        const teamPlayerCollection: ITeamPlayer[] = [{ id: 456 }];
        expectedResult = service.addTeamPlayerToCollectionIfMissing(teamPlayerCollection, teamPlayer);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(teamPlayer);
      });

      it('should add only unique TeamPlayer to an array', () => {
        const teamPlayerArray: ITeamPlayer[] = [{ id: 123 }, { id: 456 }, { id: 59891 }];
        const teamPlayerCollection: ITeamPlayer[] = [{ id: 123 }];
        expectedResult = service.addTeamPlayerToCollectionIfMissing(teamPlayerCollection, ...teamPlayerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const teamPlayer: ITeamPlayer = { id: 123 };
        const teamPlayer2: ITeamPlayer = { id: 456 };
        expectedResult = service.addTeamPlayerToCollectionIfMissing([], teamPlayer, teamPlayer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(teamPlayer);
        expect(expectedResult).toContain(teamPlayer2);
      });

      it('should accept null and undefined values', () => {
        const teamPlayer: ITeamPlayer = { id: 123 };
        expectedResult = service.addTeamPlayerToCollectionIfMissing([], null, teamPlayer, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(teamPlayer);
      });

      it('should return initial array if no TeamPlayer is added', () => {
        const teamPlayerCollection: ITeamPlayer[] = [{ id: 123 }];
        expectedResult = service.addTeamPlayerToCollectionIfMissing(teamPlayerCollection, undefined, null);
        expect(expectedResult).toEqual(teamPlayerCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
