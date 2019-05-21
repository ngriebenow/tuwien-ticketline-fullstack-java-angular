import {PerformanceSearchRequest} from './performance';

export class EventSearchResult {
  constructor(
    public id: number,
    public name: string,
    public category: string,
    public hallName: string,
    public locationName: string,
    public locationPlace: string,
    public priceCategories: string,
    public performances: PerformanceSearchRequest[]
    ) {

  }

}
