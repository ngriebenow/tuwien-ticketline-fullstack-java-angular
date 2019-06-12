import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TicketQrCodeService {
  private verificationBaseUri: string = this.globals.backendUri + '/verification';
  constructor(private httpClient: HttpClient, private globals: Globals) {
  }
  /**
   * Get QR-Code by ticket id.
   * @param id of the ticket
   * @return observable of type blob.
   */
  getQR(id: number): Observable<Blob> {
    return this.httpClient.get(this.verificationBaseUri + '/' + id, { responseType: 'blob' });
  }
}
