import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InvoicePdfService {

  private qrBaseUri: String = this.globals.backendUri + '/invoices/qr';

  constructor(private httpClient: HttpClient,
              private globals: Globals) {
  }

  getQR(id: number): Observable<Blob> {
    return this.httpClient.get(this.qrBaseUri + '/' + id, {responseType: 'blob'});
  }
}
