import { TestBed } from '@angular/core/testing';

import { LocationService } from './location.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {Globals} from '../global/globals';

describe('LocationService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule,
    ],
    providers: [
      Globals,
    ],
  }));

  it('should be created', () => {
    const service: LocationService = TestBed.get(LocationService);
    expect(service).toBeTruthy();
  });
});
