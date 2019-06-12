import { TestBed } from '@angular/core/testing';

import { TicketQrCodeService } from './ticket-qr-code.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {Globals} from '../global/globals';

describe('TicketVerificationService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule,
    ],
    providers: [
      Globals,
    ],
  }));

  it('should be created', () => {
    const service: TicketQrCodeService = TestBed.get(TicketQrCodeService);
    expect(service).toBeTruthy();
  });
});
