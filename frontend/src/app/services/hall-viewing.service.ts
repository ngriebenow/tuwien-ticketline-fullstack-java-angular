import {Injectable, Optional} from '@angular/core';
import {DefinedUnit} from '../dtos/defined-unit';
import {PriceCategory} from '../dtos/price-category';
import {Point} from '../dtos/Point';
import {Globals} from "../global/globals";
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from "rxjs";
import {Performance} from "../dtos/performance";
import {Event} from "../dtos/event";

@Injectable({
  providedIn: 'root'
})
export class HallViewingService {

  defUnits: DefinedUnit[] = [];

  private hallViewingUri: string = this.globals.backendUri + '/performances/hall-viewing';

  constructor(private globals: Globals,
              private httpClient: HttpClient) {
  }

  getPerformance() {
    const performance = JSON.parse(localStorage.getItem('performance'));
    localStorage.setItem('performance', JSON.stringify(performance));
    return performance;
  }

  getEvent() {
    const event = JSON.parse(localStorage.getItem('event'));
    localStorage.setItem('event', JSON.stringify(event));
    return event;
  }

  getDefinedUnits(performance: Performance): Observable<DefinedUnit[]>{
    const url: string = this.hallViewingUri  + "/defined-units/" + performance.id;
    console.log("getDefinedUnits " + performance.id + " with url " + url);
    return this.httpClient.get<DefinedUnit[]>(url);
  }
}
