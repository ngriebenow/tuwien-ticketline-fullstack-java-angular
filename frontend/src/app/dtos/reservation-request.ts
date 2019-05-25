import {TicketRequest} from './ticket-request';

export class ReservationRequest {
  constructor(
    public clientId: number,
    public performanceId: number,
    public ticketRequests: TicketRequest[]
  ) {
  }
}
