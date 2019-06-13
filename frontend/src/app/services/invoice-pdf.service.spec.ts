import { TestBed } from '@angular/core/testing';

import { InvoicePdfService } from './invoice-pdf.service';

describe('InvoicePdfService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: InvoicePdfService = TestBed.get(InvoicePdfService);
    expect(service).toBeTruthy();
  });
});
