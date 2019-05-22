
export class EventFilter {
  constructor(
    public name: string,
    public eventCategory: string,
    public artistName: string,
    public priceInEuro: string,
    public content: string,
    public duration: string,
    public hallId: string,
    public hallName: string,
    public locationId: string,
    public locationName: string,
    public locationPlace: string,
    public locationCountry: string,
    public locationStreet: string,
    ) {
  }
}
