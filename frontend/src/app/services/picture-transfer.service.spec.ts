import { TestBed } from '@angular/core/testing';

import { PictureTransferService } from './picture-transfer.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {Globals} from '../global/globals';

describe('PictureTransferService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule,
    ],
    providers: [
      Globals,
    ],
  }));

  it('should be created', () => {
    const service: PictureTransferService = TestBed.get(PictureTransferService);
    expect(service).toBeTruthy();
  });
});
