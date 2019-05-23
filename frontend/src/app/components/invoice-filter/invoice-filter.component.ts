import {Component, OnInit} from '@angular/core';
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

  constructor(private invoiceService: InvoiceService) {
  }

  ngOnInit() {
    this.loadInvoices();
  }

  private loadInvoices(): void {
    this.invoiceService.getInvoices(this.page, this.count).subscribe(
      (invoices: Invoice[]) => {
        this.invoices = invoices;
      },
      error => {
        // TODO: handle this error
      }
    );
  }

  private getClientFullName(invoice: Invoice): string {
    return `${invoice.client.name} ${invoice.client.surname}`;
  }

}
