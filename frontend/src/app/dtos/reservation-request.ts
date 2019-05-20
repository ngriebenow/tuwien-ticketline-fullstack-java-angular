import {TicketRequest} from "./ticket-request";

export class ReservationRequest {
  constructor(
    public performanceId: number,
    public clientId: number,
    public ticketRequests: TicketRequest[]
  ) {
  }
}
