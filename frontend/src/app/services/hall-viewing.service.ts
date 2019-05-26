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
  cats: PriceCategory[] = [];
  sectorSel: DefinedUnit;

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

  sectorSelected(dunit: DefinedUnit) {
    this.sectorSel = dunit;
  }

  anySectorSelected() {
    return this.sectorSel != null;
  }

  getSelectedSectorName() {
    return this.sectorSel.name;
  }

  getSelectedSectorCap() {
    return this.sectorSel.capacity;
  }

  getSelectedSectorFree() {
    let num = this.sectorSel.free;
    const index = this.defUnits.indexOf(this.sectorSel);
    if (this.defUnits[index].selected && this.sectorSel.free === 0) {
      num = this.defUnits[index].num;
    }
    return num;
  }

  updateSelectedNum(sectorNum: number) {
    this.defUnits[this.defUnits.indexOf(this.sectorSel)].num = sectorNum;
  }

  getNumOfSelectedSec() {
    const index = this.defUnits.indexOf(this.sectorSel);
    return this.defUnits[index].num > 0 ? this.defUnits[index].num : 0;
  }

  checkValue(value: number) {
    if (value !== null) {
      const index = this.defUnits.indexOf(this.sectorSel);
      if (value > this.defUnits[index].free) {
        value = this.defUnits[index].free;
      }
      if (value < 0) {
        value = 0;
      }
      return value;
    }
    return 0;
  }

  sectorDone(sectorNum: number) {
    if (sectorNum !== null) {
      const index = this.defUnits.indexOf(this.sectorSel);
      this.defUnits[index].num = sectorNum;
      if (sectorNum !== 0) {
        this.defUnits[index].selected = true;
      } else {
        this.defUnits[index].selected = false;
      }
    }
    this.sectorSel = null;
  }

  endTransaction() {}
}
