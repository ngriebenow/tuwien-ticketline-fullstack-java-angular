import {PerformanceSearchResult} from "./performance-search-result";
import {Optional} from "@angular/core";

export class EventSearchResult {
  constructor(
    public id: number,
    public name: string,
    public category: string,
    public hallName: string,
    public locationName: string,
    public locationPlace: string,
    public duration: string,
    public priceRange: string,
    public performances: PerformanceSearchResult[]
    ) {

  }

}
