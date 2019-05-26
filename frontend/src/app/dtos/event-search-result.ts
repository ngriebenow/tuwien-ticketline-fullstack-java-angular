import {PerformanceSearchResult} from './performance-search-result';
import {PriceCategory} from "./price-category";

export class EventSearchResult {
  constructor(
    public id: number,
    public name: string,
    public category: string,
    public hallName: string,
    public locationName: string,
    public locationPlace: string,
    public duration: string,
    public priceCategories: PriceCategory[],
    public performances: PerformanceSearchResult[]
    ) {

  }

}
