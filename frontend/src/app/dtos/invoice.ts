import {Ticket} from './ticket';
import {Client} from './client';

export class Invoice {
  constructor(
    public id: number,
    public reservationCode: string,
    public number: number,
    public cancelled: boolean,
    public paid: boolean,
    public paidAt: string, // TODO: can we convert this to a date right here? do we need to?
    public client: Client,
    public tickets: Ticket[]
  ) {
  }
}
