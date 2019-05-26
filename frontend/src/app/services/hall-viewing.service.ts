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

  defUnits: DefinedUnit[] = [];
  cats: PriceCategory[] = [];
  sectorSel: DefinedUnit;
  event: Event;
  performance: Performance;

  private hallViewingUri: string = this.globals.backendUri + '/performances/hall-viewing';

  constructor( private ticketingService: TicketingService, private globals: Globals,
              private httpClient: HttpClient) {
  }

  init() {
    let ev: Event = JSON.parse(localStorage.getItem('event'));
    this.event = ev;
    this.performance = JSON.parse(localStorage.getItem('performance'));
    this.cats = this.event.priceCategories;
  }

  clickSeat(seat: DefinedUnit): void {
    seat.selected = !seat.selected;
  }

  getBackColor(dunit: DefinedUnit) {
    return dunit.selected ? '#FF9824' : '#CFCFCF';
  }

  getDefinedUnits(performanceId: number): Observable<DefinedUnit[]>{
    const url: string = this.hallViewingUri  + "/defined-units/" + performanceId;
    console.log("getDefinedUnits " + performanceId + " with url " + url);
    return this.httpClient.get<DefinedUnit[]>(url);
  }

  selectionNotEmpty() {
    let any:boolean = false
    this.defUnits.forEach(
      x => any = any || x.selected
    );
    return any;
  }

  getTicketSum() {
    let ticketSum = 0;
    let num = 1;
    let cat = 0;
    for (let i = 0; i < this.defUnits.length; i++) {
      if (this.defUnits[i].selected) {
        cat = this.defUnits[i].priceCategoryId;
        for (let j = 0; j < this.cats.length; j++) {
          if (cat === this.cats[j].id) {
            if (this.defUnits[i].capacity > 1) {
              num = this.defUnits[i].num;
            }
            ticketSum += (this.cats[j].priceInCents * num);
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
    return this.defUnits.length > 0 ? this.defUnits[index].num : 0;
  }

  getChosenNum(dunit: DefinedUnit) {
    return this.defUnits[this.defUnits.indexOf(dunit)].num;
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
    return dunit.selected;
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

  endTransaction() {
    const dunitIds: number[] = [];
    const dunits: DefinedUnit[] = [];
    const amount: number[] = [];
    let tmp = 0;
    for (let i = 0; i < this.defUnits.length; i++) {
      if (this.defUnits[i].selected) {
        dunitIds[tmp] = this.defUnits[i].id;
        dunits[tmp] = this.defUnits[i];
        if (this.defUnits[i].capacity > 1) {
          amount[tmp] = this.defUnits[i].num;
        } else {
          amount[tmp] = 1;
        }
        tmp++;
      }
    }
    this.ticketingService.setTicketRequests(dunitIds, amount, dunits, this.cats);
  }
}
