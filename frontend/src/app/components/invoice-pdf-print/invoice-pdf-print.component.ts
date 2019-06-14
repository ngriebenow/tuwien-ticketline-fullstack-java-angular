import { Component, OnInit, Input} from '@angular/core';
import {PdfService} from '../../services/pdf.service';
import {InvoicePdfService} from '../../services/invoice-pdf.service';
import {CurrencyPipe, DatePipe} from '@angular/common';
import {Ticket} from '../../dtos/ticket';
import {Invoice} from "../../dtos/invoice";

@Component({
  selector: 'app-invoice-pdf-print',
  templateUrl: './invoice-pdf-print.component.html',
  styleUrls: ['./invoice-pdf-print.component.scss']
})
export class InvoicePdfPrintComponent implements OnInit {

  @Input() tickets: Ticket[];
  @Input() invoice: Invoice;
  companyName: String = 'INSO AustriaTicket GmbH';
  street: String = 'Wiedner Hauptstraße 76/2/2';
  city: String = '1040 Wien';
  telefon: String = '+43 1 5872197';
  uid: String = 'ATU12345678';
  bic: String = 'BKAUATWW';
  iban: String = 'AT121234501234567891';
  constructor(private pdfService: PdfService,
              private currencyPipe: CurrencyPipe,
              private datePipe: DatePipe,
              private invoicePdfService: InvoicePdfService) { }

  ngOnInit() {
  }

  createPDF() {
    if (this.tickets.length === 0) {
      return;
    }
    const doc = this.pdfService.createPdf();
    doc.setFont('Roboto-regular', 'normal');
    const logoData = 'data:image/png;base64,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';
    doc.addImage(logoData, 'PNG', 95, 10, 20, 20);
    doc.setFontSize(11);
    doc.text(this.companyName, this.calcCenterXOffset(doc, this.companyName, 11), 40);
    doc.setFontSize(8);
    doc.text(this.street, this.calcCenterXOffset(doc, this.street, 8), 45);
    doc.text(this.city, this.calcCenterXOffset(doc, this.city, 8), 50);
    doc.setFontSize(18);
    if (this.invoice.paid) {
      var t = '';
      if (this.invoice.cancelled) {
        t = 'Stornorechnung';
      } else {
        t = 'Rechnung';
      }
    }
    doc.text(t, this.calcCenterXOffset(doc, t, 18), 60);
    var yOffset = 70;
    doc.setFontSize(8);
    doc.text('Rechnungsnummer: ' + this.invoice.id, 25, yOffset);
    yOffset += 4;
    doc.text('Rechnungsdatum: ' + this.datePipe.transform(this.invoice.paidAt, 'dd.MM.yyyy - HH:mm'), 25, yOffset);
    if (this.invoice.cancelled) {
      yOffset += 8;
      doc.text('Original Rechnungsnummer: ' + this.invoice.parentNumber, 25, yOffset);
      yOffset += 4;
      doc.text('Original Rechnungsdatum: ' + this.datePipe.transform(this.invoice.parentPaidAt, 'dd.MM.yyyy - HH:mm'), 25, yOffset);
    }
    yOffset += 10;
    doc.text('Menge', 25, yOffset);
    doc.text('Beschreibung', 50, yOffset);
    doc.text('Umsatzsteuer', 145, yOffset);
    doc.text('Preis', 170, yOffset);
    yOffset += 2;
    doc.setLineWidth(0.1);
    doc.line(25, yOffset, 185, yOffset);
    yOffset += 6;
    for (const ticket of this.tickets) {
      doc.text('1', 28, yOffset);
      doc.text(ticket.eventName + ': ' + ticket.performanceName + ' (' + ticket.title + ')', 50, yOffset);
      doc.text('20.0%', 145, yOffset);
      doc.text(this.currencyPipe.transform(ticket.priceInCents / 100, 'EUR'), 170, yOffset);
      yOffset += 4;
    }
    const sum = this.calcSum(this.tickets);
    const nettoSum = this.calcNetto(sum);
    const ust = this.calcUst(sum, nettoSum);
    doc.line(25, yOffset, 185, yOffset);
    yOffset += 4;
    doc.text('Gesamt netto', 25, yOffset);
    doc.text(this.currencyPipe.transform(nettoSum / 100, 'EUR'), 46, yOffset);

    doc.text('+20,0% Ust.', 85, yOffset);
    doc.text(this.currencyPipe.transform(ust / 100, 'EUR'), 102, yOffset);

    if (this.invoice.cancelled) {
      doc.text('Gesamtpreis', 145, yOffset);
      doc.text('-' + this.currencyPipe.transform(sum / 100, 'EUR'), 170, yOffset);
      yOffset += 8;
      doc.text('Bar gegeben', 145, yOffset);
      doc.text('-' + this.currencyPipe.transform(sum / 100, 'EUR'), 170, yOffset);
    } else {
      doc.text('Gesamtpreis', 145, yOffset);
      doc.text(this.currencyPipe.transform(sum / 100, 'EUR'), 170, yOffset);
      yOffset += 8;
      doc.text('Bar gegeben', 145, yOffset);
      doc.text(this.currencyPipe.transform(sum / 100, 'EUR'), 170, yOffset);
    }
    yOffset += 4;
    doc.text('Rückgeld', 145, yOffset);
    doc.text('€0.00', 170, yOffset);

    yOffset += 30;
    doc.line(25, yOffset, 185, yOffset);
    yOffset += 4;
    doc.text(this.companyName, 30, yOffset);
    doc.text('Anschrift: ' + this.street + ', ' + this.city, 75, yOffset);
    doc.text('Tel.:' + this.telefon, 155, yOffset);
    yOffset += 4;
    doc.text('UID: ' + this.uid,50, yOffset);
    doc.text('IBAN: ' + this.iban, 85, yOffset);
    doc.text('BIC: ' + this.bic, 135, yOffset);

    doc.save('Rechnung-' + this.invoice.id);
  }

  calcCenterXOffset(doc, text: String, fontSize: number): number {
    const textWidth = doc.getStringUnitWidth(text) * doc.internal.getFontSize() / doc.internal.scaleFactor;
    return (doc.internal.pageSize.width - textWidth) / 2;
  }

  calcSum(tickets: Ticket[]): number {
    var sum = 0;
    for (const ticket of tickets) {
      sum += ticket.priceInCents;
    }
    return sum;
  }

  calcNetto(sum: number): number {
    return sum / 120 * 100;
  }

  calcUst(sum: number, netto: number): number {
    return sum - netto;
  }
}
