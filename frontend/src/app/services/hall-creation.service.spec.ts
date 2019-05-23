import {TestBed} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';

import {HallCreationService} from './hall-creation.service';
import {Globals} from '../global/globals';
import {RouterTestingModule} from '@angular/router/testing';

describe('HallCreationService', () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
      ],
      providers: [
        Globals,
      ],
    }));

  it('should be created', () => {
    const service: HallCreationService = TestBed.get(HallCreationService);
    expect(service).toBeTruthy();
  });
});
