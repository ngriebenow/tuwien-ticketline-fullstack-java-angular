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
    return JSON.parse(localStorage.getItem('performance'));

  }

  getEvent() {
    return JSON.parse(localStorage.getItem('event'));
  }

  getDefinedUnits(performance: Performance): Observable<DefinedUnit[]>{
    const url: string = this.hallViewingUri  + "/defined-units/" + performance.id;
    console.log("getDefinedUnits " + performance.id + " with url " + url);
    return this.httpClient.get<DefinedUnit[]>(url);
  }
}
