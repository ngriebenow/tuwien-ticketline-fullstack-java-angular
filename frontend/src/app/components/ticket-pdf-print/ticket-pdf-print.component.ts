import { Component, OnInit } from '@angular/core';
import {Ticket} from '../../dtos/ticket';
import {PdfService} from '../../services/pdf.service';

@Component({
  selector: 'app-ticket-pdf-print',
  templateUrl: './ticket-pdf-print.component.html',
  styleUrls: ['./ticket-pdf-print.component.scss']
})
export class TicketPdfPrintComponent implements OnInit {
  dummyTicket: Ticket;
  constructor(private pdfService: PdfService) {
    this.dummyTicket = new Ticket(
    0,
    'Title',
    'Event A',
    'Performance 1 Event A',
    new Date().toISOString(),
    'Price Category 1',
    10000,
    'Wien Stadthalle',
    'R 104',
    100,
    3);
  }
  ngOnInit() {
  }
  /**
   * Print a ticket as PDF.
   * @param ticket to print
   */
  printPDF(ticket: Ticket) {
    // TODO [issue #39241]: style the ticket properly
    const doc = this.pdfService.createPdf();
    doc.setFont('Roboto-bold', 'bold');
    doc.setFontSize(35);
    doc.text(10, 10, ticket.id.toString());
    doc.text(10, 20, ticket.title);
    doc.setFont('Roboto-regular', 'normal');
    doc.setFontSize(14);
    doc.text(10, 30, ticket.eventName);
    doc.text(10, 40, ticket.performanceName);
    doc.text(10, 50, ticket.startAt.toLocaleUpperCase());
    doc.text(10, 60, ticket.priceCategoryName);
    doc.text(10, 70, ticket.priceInCents.toString());
    doc.text(10, 80, ticket.locationName);
    doc.text(10, 90, ticket.hallName);
    doc.text(10, 100, ticket.definedUnitId.toString());
    doc.setFont('Roboto-italic', 'italic');
    doc.text(10, 110, ticket.performanceId.toString());
    doc.save('Ticket-' + ticket.id + '.pdf');
  }

}