export class TicketRequest {
  constructor(
    public clientId: number,
    public reserveAndSell: boolean,
    public definedUnitIds: number[]
  ) {
  }
}
