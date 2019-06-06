import {Ticket} from './ticket';
import {Client} from './client';

export class Invoice {
  constructor(
    public id: number,
    public reservationCode: string,
    public number: number,
    public cancelled: boolean,
    public paid: boolean,
    public paidAt: string,
    public parentNumber: number,
    public parentPaidAt: string,
    public client: Client,
    public tickets: Ticket[]
  ) {
  }
}
