import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITemplateFormation, TemplateFormation } from '../template-formation.model';

import { TemplateFormationService } from './template-formation.service';

describe('TemplateFormation Service', () => {
  let service: TemplateFormationService;
  let httpMock: HttpTestingController;
  let elemDefault: ITemplateFormation;
  let expectedResult: ITemplateFormation | ITemplateFormation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TemplateFormationService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
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

    it('should create a TemplateFormation', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new TemplateFormation()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TemplateFormation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TemplateFormation', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
        },
        new TemplateFormation()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TemplateFormation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
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

    it('should delete a TemplateFormation', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTemplateFormationToCollectionIfMissing', () => {
      it('should add a TemplateFormation to an empty array', () => {
        const templateFormation: ITemplateFormation = { id: 123 };
        expectedResult = service.addTemplateFormationToCollectionIfMissing([], templateFormation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(templateFormation);
      });

      it('should not add a TemplateFormation to an array that contains it', () => {
        const templateFormation: ITemplateFormation = { id: 123 };
        const templateFormationCollection: ITemplateFormation[] = [
          {
            ...templateFormation,
          },
          { id: 456 },
        ];
        expectedResult = service.addTemplateFormationToCollectionIfMissing(templateFormationCollection, templateFormation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TemplateFormation to an array that doesn't contain it", () => {
        const templateFormation: ITemplateFormation = { id: 123 };
        const templateFormationCollection: ITemplateFormation[] = [{ id: 456 }];
        expectedResult = service.addTemplateFormationToCollectionIfMissing(templateFormationCollection, templateFormation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(templateFormation);
      });

      it('should add only unique TemplateFormation to an array', () => {
        const templateFormationArray: ITemplateFormation[] = [{ id: 123 }, { id: 456 }, { id: 96656 }];
        const templateFormationCollection: ITemplateFormation[] = [{ id: 123 }];
        expectedResult = service.addTemplateFormationToCollectionIfMissing(templateFormationCollection, ...templateFormationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const templateFormation: ITemplateFormation = { id: 123 };
        const templateFormation2: ITemplateFormation = { id: 456 };
        expectedResult = service.addTemplateFormationToCollectionIfMissing([], templateFormation, templateFormation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(templateFormation);
        expect(expectedResult).toContain(templateFormation2);
      });

      it('should accept null and undefined values', () => {
        const templateFormation: ITemplateFormation = { id: 123 };
        expectedResult = service.addTemplateFormationToCollectionIfMissing([], null, templateFormation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(templateFormation);
      });

      it('should return initial array if no TemplateFormation is added', () => {
        const templateFormationCollection: ITemplateFormation[] = [{ id: 123 }];
        expectedResult = service.addTemplateFormationToCollectionIfMissing(templateFormationCollection, undefined, null);
        expect(expectedResult).toEqual(templateFormationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
