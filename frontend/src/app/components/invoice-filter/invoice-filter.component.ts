import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {debounceTime, distinctUntilChanged} from 'rxjs/operators';
import * as _ from 'lodash';
import {InvoiceService} from '../../services/invoice.service';
import {Invoice} from '../../dtos/invoice';

@Component({
  selector: 'app-invoice-filter',
  templateUrl: './invoice-filter.component.html',
  styleUrls: ['./invoice-filter.component.scss']
})
export class InvoiceFilterComponent implements OnInit {

  private invoices: Invoice[];
  private page = 0;
  private count = 20;
  private queryParams = {};

  private searchForm = this.formBuilder.group({
    performanceName: [''],
    reservationCode: [''],
    invoiceNumber: [''],
    clientName: [''],
    clientEmail: [''],
    isCancelled: [''],
    isPaid: ['']
  });

  private activeIsCancelled = '';
  private activeIsPaid = '';

  constructor(private invoiceService: InvoiceService, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.loadInvoices();
    this.activateOnFormChange();
  }

  private loadInvoices(): void {
    this.queryParams['page'] = this.page;
    this.queryParams['count'] = this.count;
    this.invoiceService.getInvoices(this.queryParams).subscribe(
      (invoices: Invoice[]) => {
        this.invoices = invoices;
      },
      error => {
        // TODO: handle this error
      }
    );
  }

  private activateOnFormChange(): void {
    this.searchForm.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged(),
    ).subscribe(values => {
      console.log(values);
      this.queryParams = {};
        Object.entries<any>(values)
        .filter(entry => entry[1] !== null)
        .map(entry => [entry[0], entry[1].toString().trim()])
        .filter(entry => !_.isEmpty(entry[1]))
        .forEach(entry => {
          const [key, val] = entry;
          this.queryParams[key] = val;
        });
      this.loadInvoices();
    });
  }

  private nextPage(): void {
    this.page++;
    this.loadInvoices();
  }

  private previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadInvoices();
    }
  }

  private resetSearchForm(): void {
    this.page = 0;
    this.activeIsPaid = '';
    this.activeIsCancelled = '';
    this.searchForm.reset({}, { emitEvent: true });
  }

  private setActiveIsCancelled(event: any): void {
    this.activeIsCancelled = event.target.value;
  }

  private setActiveIsPaid(event: any): void {
    this.activeIsPaid = event.target.value;
  }

  private getClientFullName(invoice: Invoice): string {
    return `${invoice.client.name} ${invoice.client.surname}`;
  }

  private getPerformanceTitle(invoice: Invoice): string {
    if (invoice.tickets.length < 1) {
      return '';
    }
    const ticket = invoice.tickets[0];
    return `${ticket.eventName}: ${ticket.performanceName}`;
  }
}