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


@Injectable({
  providedIn: 'root'
})
export class TicketingService {

  event: Event;
  performance: Performance;
  reservationRequest: ReservationRequest;
  ticketRequests: TicketRequest[] = [];
  categories: PriceCategory[] = [];
  dunits: DefinedUnit[] = [];
  client: Client;

  constructor() {}

/*  setEvent(event: Event) {


    this.event.name = event.name;
    this.event.id = event.id;
    this.event.hall = event.hall;

    console.log("setEvent with pc " + event.priceCategories.length);

    event.priceCategories.forEach(
      x => this.event.priceCategories.push(x)
    );

    this.event = event;

  }

  setPerformance(performance: Performance) {
    this.performance = performance;
  }*/

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

  getEvent() {
    return JSON.parse(localStorage.getItem('event'));
  }

  getPerformance() {
    return JSON.parse(localStorage.getItem('performance'));
  }

  getPerformanceName() {
    return this.performance.name;
  }

  getPerformanceStart() {
    return this.performance.startAt;
  }

  getEventName() {
    return this.event.name;
  }

  getHallName() {
    return this.event.hall.name;
  }

  getHallSize() {
    return this.event.hall.boundaryPoint;
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
       if (this.dunits[i].priceCategoryId === this.categories[j].id) {
         sum += this.categories[j].priceInCents * this.ticketRequests[i].amount;
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
}

