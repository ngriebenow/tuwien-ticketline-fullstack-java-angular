import {Ticket} from './ticket';

export class Invoice {
  constructor(
    public id: number,
    public tickets: Ticket[],
    public sellerUsername: string,
    public isCancelled: boolean,
    public resercationCode: string
  ) {
  }
}
