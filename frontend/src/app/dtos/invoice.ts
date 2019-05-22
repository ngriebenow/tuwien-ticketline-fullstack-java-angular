import {Ticket} from './ticket';

export class Invoice {
  constructor(
    public id: number,
    public reservationCode: string,
    public number: number,
    public cancelled: boolean,
    public paid: boolean,
    public paidAt: string, // TODO: can we convert this to a date right here?
    public clientId: number,
    public tickets: Ticket[]
  ) {
  }
}
