export class Ticket {
  constructor(
    public id: number,
    public title: string,
    public eventName: string,
    public performanceName: string,
    public startAt: string,
    public priceCategoryName: string,
    public priceInCents: number,
    public locationName: string,
    public hallName: string,
    public definedUnitId: number,
    public performanceId: number
  ) {
  }
}
