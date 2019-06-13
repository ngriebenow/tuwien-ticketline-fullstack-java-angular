import { Injectable } from '@angular/core';
import {CurrencyPipe, DatePipe} from '@angular/common'

@Injectable({
  providedIn: 'root'
})
export class InvoicePdfService {

  constructor(private datePipe: DatePipe) { }

}
