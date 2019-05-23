import {Injectable} from '@angular/core';
import {HallViewingService} from './hall-viewing.service';
import {Event} from '../dtos/event';
import {Hall} from '../dtos/hall';
import {Performance} from '../dtos/performance';
import {DefinedUnit} from '../dtos/defined-unit';
import {ReservationRequest} from '../dtos/reservation-request';
import {TicketRequest} from '../dtos/ticket-request';
import {User} from '../dtos/user';

@Injectable({
  providedIn: 'root'
})
export class TicketingService {

  event: Event;
  hall: Hall;
  performance: Performance;
  reservationRequest: ReservationRequest;
  ticketRequests: TicketRequest[] = [];
  user: User;

  constructor() {
  }

  putTicketRequests(dunits: number[], amount: number[]) {
    for (let i = 0; i < dunits.length; i++) {
      this.ticketRequests[i] = new TicketRequest(dunits[i], amount[i]);
    }
  }

  putPerformance(performance: Performance) {
    this.performance = performance;
  }

  getPerformanceName() {
    // return this.performance.name;
  }

  getPerformanceStart() {
    // return this.performance.startAt;
  }
}

