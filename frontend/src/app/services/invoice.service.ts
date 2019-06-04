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

  getInvoices(queryParams: {} = {}): Observable<Invoice[]> {
    let params = new HttpParams();
    Object.keys(queryParams).forEach(key => params = params.set(key, queryParams[key]));
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

  cancelReservation(id: number): Observable<any> {
    return this.httClient.delete<any>(`${this.invoiceBaseUri}/${id}`);
  }
}
