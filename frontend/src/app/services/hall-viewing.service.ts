import {Injectable, Optional} from '@angular/core';
import {DefinedUnit} from '../dtos/defined-unit';
import {PriceCategory} from '../dtos/price-category';
import {Point} from '../dtos/Point';
import {TicketingService} from './ticketing.service';
import {Globals} from "../global/globals";
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from "rxjs";
import {Performance} from "../dtos/performance";
import {Event} from "../dtos/event";

@Injectable({
  providedIn: 'root'
})
export class HallViewingService {

  selected: Boolean[] = [];
  selectedNum: number[] = [];
  defUnits: DefinedUnit[] = [];
  cats: PriceCategory[] = [];
  sectorSel: DefinedUnit;

  private hallViewingUri: string = this.globals.backendUri + '/performances/hall-viewing';

  constructor( private ticketingService: TicketingService, private globals: Globals,
              private httpClient: HttpClient) {
  }

  clickSeat(seat: DefinedUnit): void {
    const index = this.defUnits.indexOf(seat);
    this.selected[index] = !this.selected[index];
  }

  getBackColor(dunit: DefinedUnit) {
    return this.selected[this.defUnits.indexOf(dunit)] ? '#FF9824' : '#CFCFCF';
  }

  getHallSize(): Point {
    return this.ticketingService.getHallSize();
  }

  getPerformance() {
    return this.ticketingService.getPerformance();
  }

  getEventName(): String {
    return this.ticketingService.getEventName();
  }

  getPerformanceName(): String {
    return this.ticketingService.getPerformanceName();
  }

  getEvent() {
    return this.ticketingService.getEvent();
  }

  getCategoriesComp() {
    const event = this.ticketingService.getEvent();
    this.getCategories(event).subscribe(
      cats => this.cats = cats as PriceCategory[]);

    return this.cats;
  }

  getCategories(event: Event) {
    const url: string = this.hallViewingUri  + "/price-categories/" + event.id;
    console.log("getCategories " + event.id + " with " + url);
    return this.httpClient.get<PriceCategory[]>(url);
  }

  getDefinedUnits(performance: Performance): Observable<DefinedUnit[]>{
    const url: string = this.hallViewingUri  + "/defined-units/" + performance.id;
    console.log("getDefinedUnits " + performance.id + " with url " + url);
    return this.httpClient.get<DefinedUnit[]>(url);
  }

  getDefinedUnitsComp() {
    const performance = this.ticketingService.getPerformance();
    this.getDefinedUnits(performance).subscribe(
      defUnits => this.defUnits = defUnits as DefinedUnit[]);

    for (let i = 0; i < this.defUnits.length; i++) {
      this.selected[i] = false;
    }

    for (let i = 0; i < this.defUnits.length; i++) {
      this.selectedNum[i] = 0;
    }

    return this.defUnits;
  }

  getHallName() {
    return this.ticketingService.getHallName();
  }

  getStartAt() {
    return this.ticketingService.getPerformanceStart();
  }

  selectionNotEmpty() {
    return this.selected.includes(true);
  }

  getTicketSum() {
    let ticketSum = 0;
    let num = 1;
    let cat = 0;
    for (let i = 0; i < this.selected.length; i++) {
      if (this.selected[i]) {
        cat = this.defUnits[i].priceCategory;
        for (let j = 0; j < this.cats.length; j++) {
          if (cat === this.cats[j].id) {
            if (this.defUnits[i].capacity > 1) {
              num = this.selectedNum[i];
            }
            ticketSum += (this.cats[j].priceInCent * num);
            num = 1;
          }
        }
      }
    }
    return ticketSum;
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
    if (this.selected[index] && this.sectorSel.free === 0) {
      num = this.selectedNum[index];
    }
    return num;
  }

  updateSelectedNum(sectorNum: number) {
    this.selectedNum[this.defUnits.indexOf(this.sectorSel)] = sectorNum;
  }

  getNumOfSelectedSec() {
    const index = this.defUnits.indexOf(this.sectorSel);
    return this.selectedNum[index] > 0 ? this.selectedNum[index] : 0;
  }

  getChosenNum(dunit: DefinedUnit) {
    return this.selectedNum[this.defUnits.indexOf(dunit)];
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

  sectorIsSelected(dunit: DefinedUnit) {
    return this.selected[this.defUnits.indexOf(dunit)];
  }

  sectorDone(sectorNum: number) {
    if (sectorNum !== null) {
      const index = this.defUnits.indexOf(this.sectorSel);
      this.selectedNum[index] = sectorNum;
      if (sectorNum !== 0) {
        this.selected[index] = true;
      } else {
        this.selected[index] = false;
      }
    }
    this.sectorSel = null;
  }

  endTransaction() {
    const dunitIds: number[] = [];
    const dunits: DefinedUnit[] = [];
    const amount: number[] = [];
    let tmp = 0;
    for (let i = 0; i < this.defUnits.length; i++) {
      if (this.selected[i]) {
        dunitIds[tmp] = this.defUnits[i].id;
        dunits[tmp] = this.defUnits[i];
        if (this.defUnits[i].capacity > 1) {
          amount[tmp] = this.selectedNum[i];
        } else {
          amount[tmp] = 1;
        }
        tmp++;
      }
    }
    this.ticketingService.setTicketRequests(dunitIds, amount, dunits, this.cats);
  }
}
