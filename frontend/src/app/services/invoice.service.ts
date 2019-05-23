import {Injectable} from '@angular/core';
import {Globals} from '../global/globals';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Invoice} from '../dtos/invoice';
import {ReservationRequest} from '../dtos/reservation-request';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  private invoiceBaseUri: string = this.globals.backendUri + '/invoices';

  constructor(private httClient: HttpClient, private globals: Globals) {
  }

  getInvoiceById(id: number): Observable<Invoice> {
    return this.httClient.get<Invoice>(`${this.invoiceBaseUri}/${id}`);
  }

  // TODO: add search parameters
  getInvoices(page: number, count: number): Observable<Invoice[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('count', count.toString());
    return this.httClient.get<Invoice[]>(this.invoiceBaseUri, {params});
  }

  buyTickets(reservationRequest: ReservationRequest): Observable<Invoice> {
    return this.httClient.post<Invoice>(this.invoiceBaseUri, reservationRequest);
  }

  payInvoice(id: number, ticketIds: number[]): Observable<Invoice> {
    return this.httClient.post<Invoice>(`${this.invoiceBaseUri}/${id}/pay`, ticketIds);
  }

  reserveTickets(reservationRequest: ReservationRequest): Observable<Invoice> {
    return this.httClient.post<Invoice>(`${this.invoiceBaseUri}/reserve`, reservationRequest);
  }

}
