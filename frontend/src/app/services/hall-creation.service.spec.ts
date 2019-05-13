import { TestBed } from '@angular/core/testing';

import { HallCreationService } from './hall-creation.service';

describe('HallCreationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HallCreationService = TestBed.get(HallCreationService);
    expect(service).toBeTruthy();
  });
});
