import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGraphicElement, GraphicElement } from '../graphic-element.model';

import { GraphicElementService } from './graphic-element.service';

describe('GraphicElement Service', () => {
  let service: GraphicElementService;
  let httpMock: HttpTestingController;
  let elemDefault: IGraphicElement;
  let expectedResult: IGraphicElement | IGraphicElement[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GraphicElementService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      code: 'AAAAAAA',
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

    it('should create a GraphicElement', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new GraphicElement()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GraphicElement', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          code: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GraphicElement', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
        },
        new GraphicElement()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GraphicElement', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          code: 'BBBBBB',
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

    it('should delete a GraphicElement', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addGraphicElementToCollectionIfMissing', () => {
      it('should add a GraphicElement to an empty array', () => {
        const graphicElement: IGraphicElement = { id: 123 };
        expectedResult = service.addGraphicElementToCollectionIfMissing([], graphicElement);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(graphicElement);
      });

      it('should not add a GraphicElement to an array that contains it', () => {
        const graphicElement: IGraphicElement = { id: 123 };
        const graphicElementCollection: IGraphicElement[] = [
          {
            ...graphicElement,
          },
          { id: 456 },
        ];
        expectedResult = service.addGraphicElementToCollectionIfMissing(graphicElementCollection, graphicElement);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GraphicElement to an array that doesn't contain it", () => {
        const graphicElement: IGraphicElement = { id: 123 };
        const graphicElementCollection: IGraphicElement[] = [{ id: 456 }];
        expectedResult = service.addGraphicElementToCollectionIfMissing(graphicElementCollection, graphicElement);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(graphicElement);
      });

      it('should add only unique GraphicElement to an array', () => {
        const graphicElementArray: IGraphicElement[] = [{ id: 123 }, { id: 456 }, { id: 1970 }];
        const graphicElementCollection: IGraphicElement[] = [{ id: 123 }];
        expectedResult = service.addGraphicElementToCollectionIfMissing(graphicElementCollection, ...graphicElementArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const graphicElement: IGraphicElement = { id: 123 };
        const graphicElement2: IGraphicElement = { id: 456 };
        expectedResult = service.addGraphicElementToCollectionIfMissing([], graphicElement, graphicElement2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(graphicElement);
        expect(expectedResult).toContain(graphicElement2);
      });

      it('should accept null and undefined values', () => {
        const graphicElement: IGraphicElement = { id: 123 };
        expectedResult = service.addGraphicElementToCollectionIfMissing([], null, graphicElement, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(graphicElement);
      });

      it('should return initial array if no GraphicElement is added', () => {
        const graphicElementCollection: IGraphicElement[] = [{ id: 123 }];
        expectedResult = service.addGraphicElementToCollectionIfMissing(graphicElementCollection, undefined, null);
        expect(expectedResult).toEqual(graphicElementCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
