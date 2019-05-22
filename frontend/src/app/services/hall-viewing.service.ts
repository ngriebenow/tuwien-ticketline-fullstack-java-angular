import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {DefinedUnit} from '../dtos/defined-unit';
import {Event} from '../dtos/event';
import {Performance} from '../dtos/performance';
import {PriceCategory} from '../dtos/price-category';
import {Hall} from '../dtos/hall';
import {Unit} from '../dtos/unit';
import {Point} from '../dtos/Point';
import {TicketingService} from "./ticketing.service";
import {compileBaseDefFromMetadata} from "@angular/compiler";


@Injectable({
  providedIn: 'root'
})
export class HallViewingService {

  performanceName: String = 'Performance';
  eventName: String = 'Event';
  selected: Boolean[] = [];
  selectedNum: number[] = [];
  defUnits: DefinedUnit[] = [];
  cats: PriceCategory[] = [];
  points: Point[] = [];
  sectorSel: DefinedUnit;

  constructor(private ticketingService: TicketingService) {
  }

  clickSeat(seat: DefinedUnit): void {
    const index = this.defUnits.indexOf(seat);
    if (this.selected[index] === false) {
      this.selected[index] = true;
    } else {
      this.selected[index] = false;
    }
  }

  getBackColor(dunit: DefinedUnit) {
    return this.selected[this.defUnits.indexOf(dunit)] ? '#FF9824' : '#CFCFCF';
  }

  getHallSize(): Point {
    //TODO for real!
    return new Point(10, 10);
  }


  getSeats(): Point[] {
    //TODO for real!
    this.points[0] = new Point(1, 1);
    this.points[1] = new Point(2, 1);
    this.points[2] = new Point(4, 1);
    this.points[3] = new Point(5, 1);
    this.points[4] = new Point(6, 1);
    this.points[5] = new Point(7, 1);
    this.points[6] = new Point(9, 1);
    this.points[7] = new Point(10, 1);

    this.points[8] = new Point(1, 2);
    this.points[9] = new Point(2, 2);
    this.points[10] = new Point(4, 2);
    this.points[11] = new Point(5, 2);
    this.points[12] = new Point(6, 2);
    this.points[13] = new Point(7, 2);
    this.points[14] = new Point(9, 2);
    this.points[15] = new Point(10, 2);
    return this.points;
  }

  getEventName(): String {
    return this.eventName;
  }

  getPerformanceName(): String {
    return this.performanceName;
  }


  getCats(): PriceCategory[] {
    this.cats[0] = new PriceCategory(1, 10.90, 'Kategorie1', 0x0000FF);
    this.cats[1] = new PriceCategory(2, 11.20, 'Kategorie2', 0x008000);
    //this.cats[2] = new PriceCategory(3, 12.50, 'Kategorie3', 0xFF00FF);
    return this.cats;
  }

  getDefUnits(): DefinedUnit[] {
    this.defUnits[0] = new DefinedUnit(1, 'something', 1, 1, new Point(1, 1), new Point(1, 1), 1);
    this.defUnits[1] = new DefinedUnit(2, 'something', 1, 1, new Point(2, 1), new Point(2, 1), 1);
    this.defUnits[2] = new DefinedUnit(3, 'something', 1, 1, new Point(4, 1), new Point(4, 1), 1);
    this.defUnits[3] = new DefinedUnit(4, 'something', 1, 1, new Point(5, 1), new Point(5, 1), 1);
    this.defUnits[4] = new DefinedUnit(5, 'something', 0, 1, new Point(6, 1), new Point(6, 1), 1);
    this.defUnits[5] = new DefinedUnit(6, 'something', 0, 1, new Point(7, 1), new Point(7, 1), 1);
    this.defUnits[6] = new DefinedUnit(7, 'something', 1, 1, new Point(9, 1), new Point(9, 1), 1);
    this.defUnits[7] = new DefinedUnit(8, 'something', 0, 1, new Point(10, 1), new Point(10, 1), 1);

    this.defUnits[8] = new DefinedUnit(9, 'something', 1, 1, new Point(1, 2), new Point(1, 2), 2);
    this.defUnits[9] = new DefinedUnit(10, 'something', 1, 1, new Point(2, 2), new Point(2, 2), 2);
    this.defUnits[10] = new DefinedUnit(11, 'something', 0, 1, new Point(4, 2), new Point(4, 2), 2);
    this.defUnits[11] = new DefinedUnit(12, 'something', 0, 1, new Point(5, 2), new Point(5, 2), 2);
    this.defUnits[12] = new DefinedUnit(13, 'something', 1, 1, new Point(6, 2), new Point(6, 2), 2);
    this.defUnits[13] = new DefinedUnit(14, 'something', 0, 1, new Point(7, 2), new Point(7, 2), 2);
    this.defUnits[14] = new DefinedUnit(15, 'something', 1, 1, new Point(9, 2), new Point(9, 2), 2);
    this.defUnits[15] = new DefinedUnit(16, 'something', 1, 1, new Point(10, 2), new Point(10, 2), 2);

    this.defUnits[16] = new DefinedUnit(17, 'Sektor A', 2, 2, new Point(1, 3), new Point(2, 3), 2);
    this.defUnits[17] = new DefinedUnit(19, 'something', 0, 1, new Point(4, 3), new Point(4, 3), 2);
    this.defUnits[18] = new DefinedUnit(20, 'something', 0, 1, new Point(5, 3), new Point(5, 3), 2);
    this.defUnits[19] = new DefinedUnit(21, 'something', 1, 1, new Point(6, 3), new Point(6, 3), 2);
    this.defUnits[20] = new DefinedUnit(22, 'something', 0, 1, new Point(7, 3), new Point(7, 3), 2);
    this.defUnits[21] = new DefinedUnit(23, 'something', 1, 1, new Point(9, 3), new Point(9, 3), 2);
    this.defUnits[22] = new DefinedUnit(24, 'something', 1, 1, new Point(10, 3), new Point(10, 3), 2);

    for (let i = 0; i < this.defUnits.length; i++) {
      this.selected[i] = false;
    }

    for (let i = 0; i < this.defUnits.length; i++) {
      this.selectedNum[i] = 0;
    }

    return this.defUnits;
  }

  getHallName() {
    return 'Halle 1';
  }

  getStartAt() {
    return '12:30';
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
    return this.sectorSel != null ? true : false;
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
    if (value != null) {
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
    if (sectorNum != null) {
      const index = this.defUnits.indexOf(this.sectorSel);
      this.selectedNum[index] = sectorNum;
      if (sectorNum != 0) {
        this.selected[index] = true;
      } else {
        this.selected[index] = false;
      }
    }
    this.sectorSel = null;
  }

  endTransaction() {
    let dunits: number[] = [];
    let amount: number[] = [];
    let tmp = 0;
    for (let i = 0; i < this.defUnits.length; i++) {
      if (this.selected[i]) {
        dunits[tmp] = this.defUnits[i].id;
        if (this.defUnits[i].capacity > 1) {
          amount[tmp] = this.selectedNum[i];
        } else {
          amount[tmp] = 1;
        }
        tmp++;
      }
    }
    this.ticketingService.putTicketRequests(dunits, amount);
  }
}
