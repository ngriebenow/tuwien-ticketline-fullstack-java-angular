import {TestBed} from '@angular/core/testing';

import {HallService} from './hall.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {Globals} from '../global/globals';

describe('HallService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule,
    ],
    providers: [
      Globals,
    ],
  }));

  it('should be created', () => {
    const service: HallService = TestBed.get(HallService);
    expect(service).toBeTruthy();
  });
});
