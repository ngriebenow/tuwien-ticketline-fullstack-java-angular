import { TestBed } from '@angular/core/testing';

import { PictureService } from './picture.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {Globals} from '../global/globals';

describe('PictureService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule,
    ],
    providers: [
      Globals,
    ],
  }));

  it('should be created', () => {
    const service: PictureService = TestBed.get(PictureService);
    expect(service).toBeTruthy();
  });
});
