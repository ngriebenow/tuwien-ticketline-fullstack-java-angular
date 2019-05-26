import {TestBed} from '@angular/core/testing';
import {HallViewingService} from './hall-viewing.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {Globals} from '../global/globals';

describe('HallViewingService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule,
    ],
    providers: [
      Globals,
    ],
  }));

  it('should be created', () => {
    const service: HallViewingService = TestBed.get(HallViewingService);
    expect(service).toBeTruthy();
  });
});
