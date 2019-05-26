import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {AlertService} from '../../services/alert.service';
import {InvoiceService} from '../../services/invoice.service';
import {TicketingService} from '../../services/ticketing.service';
import {ReservationRequest} from '../../dtos/reservation-request';
import {TicketRequest} from '../../dtos/ticket-request';
import {Invoice} from '../../dtos/invoice';
import {Ticket} from '../../dtos/ticket';

enum State {
  Transient,
  Reserved,
  Bought,
  Cancelled
}

@Component({
  selector: 'app-invoice-detail',
  templateUrl: './invoice-detail.component.html',
  styleUrls: ['./invoice-detail.component.scss']
})
export class InvoiceDetailComponent implements OnInit {

  invoice: Invoice;
  private state: State;
  private selectedTickets: Ticket[] = [];

  constructor(
    private route: ActivatedRoute,
    private invoiceService: InvoiceService,
    private ticketingService: TicketingService,
    private alertService: AlertService) {
  }

  ngOnInit() {
    const route = this.route.snapshot;
    if (route.paramMap.has('id')) {
      this.loadInvoice(+route.paramMap.get('id'));
    } else {
      this.loadTransientInvoice();
    }
  }

  private loadInvoice(id: number): void {
    this.invoiceService.getInvoiceById(id).subscribe(
      invoice => this.reset(invoice),
      error => {
        // TODO: refine this error handling
        this.alertService.error('Rechnung konnte nicht geladen werden.');
        console.log(error);
      }
    );
  }

  private reset(invoice: Invoice): void {
    this.invoice = invoice;
    this.initState();
    this.selectAllTickets();
  }

  private loadTransientInvoice(): void {
    this.reset(this.ticketingService.getTransientInvoice());
  }

  private initState(): void {
    if (this.invoice.id === null) {
      this.state = State.Transient;
    } else if (this.invoice.cancelled) {
      this.state = State.Cancelled;
    } else if (this.invoice.paid) {
      this.state = State.Bought;
    } else {
      this.state = State.Reserved;
    }
  }

  private getTitle(): string {
    switch (this.state) {
      case State.Transient:
        return 'Auswahlübersicht';
      case State.Reserved:
        return 'Reservierung';
      case State.Bought:
        return 'Rechnung';
      case State.Cancelled:
        return 'Stornorechnung';
    }
  }

  private selectAllTickets(): void {
    this.selectedTickets = Object.assign([], this.invoice.tickets);
  }

  private isSelected(ticket: Ticket): boolean {
    return this.selectedTickets.includes(ticket);
  }

  private toggleSelected(ticket: Ticket): void {
    if (this.state === State.Cancelled) {
      return;
    }
    if (this.isSelected(ticket)) {
      this.selectedTickets = this.selectedTickets.filter(tic => tic !== ticket);
    } else {
      this.selectedTickets.push(ticket);
    }
  }

  private getFirstTicket(): Ticket {
    if (!this.invoice || !this.invoice.tickets || this.invoice.tickets.length === 0) {
      return null;
    }
    return this.invoice.tickets[0];
  }

  private getClientFullName(): string {
    return `${this.invoice.client.name} ${this.invoice.client.surname}`;
  }

  private getPerformanceName(): string {
    return this.getFirstTicket().performanceName;
  }

  private getEventName(): string {
    return this.getFirstTicket().eventName;
  }

  private getPeformanceStart(): string {
    return this.getFirstTicket().startAt;
  }

  private getLocationName(): string {
    return this.getFirstTicket().locationName;
  }

  private getHallName(): string {
    return this.getFirstTicket().hallName;
  }

  private centsToEuros(cents: number): number {
    return cents / 100;
  }

  private getTotalPriceInCents(): number {
    return this.selectedTickets
      .map(tic => tic.priceInCents)
      .reduce((prev, current) => prev + current, 0);
  }

  private getTotalPriceInEuros(): number {
    return this.centsToEuros(this.getTotalPriceInCents());
  }

  private buildTicketRequests(): TicketRequest[] {
    const ticketRequests: TicketRequest[] = [];

    const definedUnitIds = this.selectedTickets.map(tic => tic.definedUnitId);
    new Set(definedUnitIds).forEach(dUnitId => {
      const count = definedUnitIds.filter(id => id === dUnitId).length;
      ticketRequests.push(new TicketRequest(dUnitId, count));
    });

    return ticketRequests;
  }

  private buildReservationRequest(): ReservationRequest {
    const clientId = this.invoice.client.id;
    const performanceId = this.getFirstTicket().performanceId;
    const ticketRequests = this.buildTicketRequests();
    return new ReservationRequest(clientId, performanceId, ticketRequests);
  }

  /**
   * Reserve the seleceted tickets for the given client and performance.
   * Can only be done from a transient state.
   */
  private reserveTickets(): void {
    console.log('reserving');
    if (!this.isTransient()) {
      return;
    }
    if (this.selectedTickets.length === 0) {
      this.alertService.error('Bitte wählen Sie zumindest ein Ticket aus');
      return;
    }
    this.invoiceService.reserveTickets(this.buildReservationRequest()).subscribe(
      invoice => {
        this.alertService.success('Reservierung erfolgreich abgeschlossen');
        this.reset(invoice);
      },
      error => {
        console.log(error);
        this.alertService.error('Tickets konnten nicht reserviert werden, bitte versuchen Sie es später noch ein mal');
      }
    );
  }

  /**
   * Pays the selected tickets for an existing reservation.
   * Can only be issued from an exitsting reservation.
   */
  private payInvoice(): void {
    if (!this.isReserved()) {
      return;
    }
    if (this.selectedTickets.length === 0) {
      this.alertService.warning('Bitte wählen Sie zumindest ein Ticket aus');
      return;
    }
    const ticketIds: number[] = this.selectedTickets.map(tic => tic.id);
    this.invoiceService.payInvoice(this.invoice.id, ticketIds).subscribe(
      invoice => {
        this.alertService.success('Ticketkauf erfolgreich abgeschlossen');
        this.reset(invoice);
      },
      error => {
        console.log(error);
        this.alertService.error('Tickets konnten nicht gekauft werden, bitte versuchen Sie es später noch ein mal');
      },
    );
  }

  /**
   * Buys all the selected tickets without a reservation being present.
   */
  private buyTickets(): void {
    if (!this.isTransient()) {
      return;
      return;
    }
    if (this.selectedTickets.length === 0) {
      this.alertService.warning('Bitte wählen Sie zumindest ein Ticket aus');
    }
    this.invoiceService.buyTickets(this.buildReservationRequest()).subscribe(
      invoice => {
        this.alertService.success('Ticketkauf erfolgreich abgeschlossen');
        this.reset(invoice);
      },
      error => {
        console.log(error);
        this.alertService.error('Tickets konnten nicht gekauft werden, bitte versuchen Sie es später noch ein mal');
      }
    );
  }

  // TODO: next sprint
  private cancelTickets(): void {
    if (!this.isReserved() && !this.isBought()) {
      return;
    }
    this.alertService.info('Implementierung dieses Features folg demnächst');
  }

  // TODO: next sprint
  private printInvoice(): void {
    if (!this.isBought() && !this.isCancelled()) {
      return;
    }
    this.alertService.info('Implementierung dieses Features folg demnächst');
  }

  isTransient(): boolean {
    return this.state === State.Transient;
  }

  isReserved(): boolean {
    return this.state === State.Reserved;
  }

  isBought(): boolean {
    return this.state === State.Bought;
  }

  isCancelled(): boolean {
    return this.state === State.Cancelled;
  }

}
