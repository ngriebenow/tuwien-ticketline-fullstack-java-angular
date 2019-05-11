export class Ticket {
  constructor(
    public unitName: string,
    public salt: string,
    public invoiceId: number,
    public clientName: string,
    public clientSurname: string,
    public id: number,
    public priceInCents: number,
    public isCanclled: boolean
  ) {
  }
}
