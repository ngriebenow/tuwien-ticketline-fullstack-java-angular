export class Ticket {
  constructor(
    public id: number,
    public invoiceId: number,
    public performanceId: number,
    public priceInCents: number,
    public title: string,
  ) {
  }
}
