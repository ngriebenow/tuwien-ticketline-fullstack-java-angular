import { TestBed } from '@angular/core/testing';

import { PictureTransferService } from './picture-transfer.service';

describe('PictureTransferService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PictureTransferService = TestBed.get(PictureTransferService);
    expect(service).toBeTruthy();
  });
});
