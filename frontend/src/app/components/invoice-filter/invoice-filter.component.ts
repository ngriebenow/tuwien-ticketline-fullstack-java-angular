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

  private searchForm = this.formBuilder.group({
    performanceName: [''],
    reservationCode: [''],
    invoiceNumber: [''],
    clientName: [''],
    clientEmail: [''],
    isCancelled: [''],
    isPaid: ['']
  });

  constructor(private invoiceService: InvoiceService, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.loadInvoices();
    this.onFormChange();
  }

  private loadInvoices(queryParams: {} = {}): void {
    queryParams['page'] = this.page;
    queryParams['count'] = this.count;
    this.invoiceService.getInvoices(queryParams).subscribe(
      (invoices: Invoice[]) => {
        this.invoices = invoices;
      },
      error => {
        // TODO: handle this error
      }
    );
  }

  private onFormChange(): void {
    this.searchForm.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged(),
    ).subscribe(values => {
      const queryParams = {};
        Object.entries<any>(values)
        .map(entry => [entry[0], entry[1].toString().trim()])
        .filter(entry => !_.isEmpty(entry[1]))
        .forEach(entry => {
          const [key, val] = entry;
          queryParams[key] = val;
        });
      this.loadInvoices(queryParams);
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

  private getClientFullName(invoice: Invoice): string {
    return `${invoice.client.name} ${invoice.client.surname}`;
  }

}
