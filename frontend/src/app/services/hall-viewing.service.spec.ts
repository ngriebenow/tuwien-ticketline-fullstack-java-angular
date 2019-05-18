import {TestBed} from '@angular/core/testing';
import {HallViewingService} from './hall-viewing.service';

describe('HallViewingService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HallViewingService = TestBed.get(HallViewingService);
    expect(service).toBeTruthy();
  });
});
