import {Injectable} from '@angular/core';
import {HallViewingService} from './hall-viewing.service';
import {Event} from '../dtos/event';
import {Hall} from '../dtos/hall';
import {Performance} from '../dtos/performance';
import {DefinedUnit} from '../dtos/defined-unit';
import {ReservationRequest} from '../dtos/reservation-request';
import {TicketRequest} from '../dtos/ticket-request';
import {PriceCategory} from '../dtos/price-category';
import {Client} from '../dtos/client';
import {Invoice} from '../dtos/invoice';
import {Ticket} from '../dtos/ticket';

@Injectable({
  providedIn: 'root'
})
export class TicketingService {

  event: Event;
  hall: Hall;
  performance: Performance;
  reservationRequest: ReservationRequest;
  ticketRequests: TicketRequest[];
  categories: PriceCategory[];
  dunits: DefinedUnit[];
  client: Client;

  constructor() {
    this.ticketRequests = [];
    this.categories = [];
    this.dunits = [];
  }

  setEvent(event: Event) {
    this.event = event;
  }

  setPerformance(performance: Performance) {
    this.performance = performance;
  }

  setHall(hall: Hall) {
    this.hall = hall;
  }

  setTicketRequests(dunitIds: number[], amount: number[], dunits: DefinedUnit[], cats: PriceCategory[]) {
    this.dunits = dunits;
    this.categories = cats;
    for (let i = 0; i < dunitIds.length; i++) {
      this.ticketRequests[i] = new TicketRequest(dunitIds[i], amount[i]);
    }
  }

  setClient(client: Client) {
    this.client = client;
  }

  getPerformanceName() {
    return this.performance.name;
  }

  getPerformanceStart() {
    return this.performance.startAt;
  }

  getHallName() {
    return this.hall.name;
  }

  getClientName() {
    return this.client.surname + ' ' + this.client.name;
  }

  getClientId() {
    return this.client.id;
  }

  getTicketPriceSum() {
    let sum = 0;
    for (let i = 0; i < this.dunits.length; i++) {
      for (let j = 0; j < this.categories.length; j++) {
       if (this.dunits[i].priceCategory === this.categories[j].id) {
         sum += this.categories[j].priceInCent * this.ticketRequests[i].amount;
       }
      }
    }
    return sum;
  }

  getReservationRequest() {
    this.reservationRequest.clientId = this.client.id;
    this.reservationRequest.performanceId = this.performance.id;
    return this.reservationRequest;
  }

  /**
   * Generate an unsaved Invoice to be payed later or make a reservation from.
   * It is constructed from the set data in this service.
   */
  getTransientInvoice(): Invoice {
    const tickets: Ticket[] = this.ticketRequests
      .map(ticReq => this.getTransientTickets(ticReq))
      .reduce((prev, current) => prev.concat(current));

    return new Invoice(
      null,
      null,
      null,
      false,
      false,
      null,
      this.client,
      tickets
    );
  }

  /**
   * Generate a list of Tickets from a single TicketRequest.
   */
  private getTransientTickets(ticketRequest: TicketRequest): Ticket[] {
    const definedUnit = this.dunits.find(dUnit => dUnit.id === ticketRequest.definedUnitId);
    const priceCategory = this.categories.find(pCat => pCat.id === definedUnit.priceCategory);

    return Array(ticketRequest.amount).map(_ => new Ticket(
        null,
        definedUnit.name,
        this.event.name,
        this.performance.name,
        this.performance.startAt,
        priceCategory.name,
        priceCategory.priceInCent,
        this.hall.location.name,
        this.hall.name,
        definedUnit.id,
        this.performance.id
      )
    );
  }
}

