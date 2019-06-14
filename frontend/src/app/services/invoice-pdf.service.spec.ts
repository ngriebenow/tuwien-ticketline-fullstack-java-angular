import { TestBed } from '@angular/core/testing';

import { InvoicePdfService } from './invoice-pdf.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {Globals} from '../global/globals';

describe('InvoicePdfService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule,
    ],
    providers: [
      Globals,
    ],
  }));

  it('should be created', () => {
    const service: InvoicePdfService = TestBed.get(InvoicePdfService);
    expect(service).toBeTruthy();
  });
});
