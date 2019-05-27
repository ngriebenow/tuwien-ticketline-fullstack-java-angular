import {Component, NgModule, OnInit} from '@angular/core';
import {HallViewingService} from '../../services/hall-viewing.service';
import {ActivatedRoute, RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';
import {Point} from 'src/app/dtos/Point';
import {DefinedUnit} from 'src/app/dtos/defined-unit';
import {PriceCategory} from 'src/app/dtos/price-category';
import {Performance} from '../../dtos/performance';
import {Event} from '../../dtos/event';
import {AlertService} from '../../services/alert.service';


@Component({
  selector: 'app-hall-viewing',
  templateUrl: './hall-viewing.component.html',
  styleUrls: ['./hall-viewing.component.scss']
})
@NgModule({
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    RouterTestingModule,
  ]
})
export class HallViewingComponent implements OnInit {

  hallSize: Point;
  defUnits: DefinedUnit[] = [];
  cats: PriceCategory[] = [];
  sectorNum: number;
  event: Event;
  performance: Performance;
  sectorSel: DefinedUnit;

  constructor(private route: ActivatedRoute,
              private alertService: AlertService,
              private hallViewingService: HallViewingService) {
  }

  ngOnInit() {
    this.event = this.hallViewingService.getEvent();
    this.performance = this.hallViewingService.getPerformance();

    if (this.event != null) {
      this.cats = this.event.priceCategories;
      this.hallSize = this.event.hall.boundaryPoint;
    }

    this.getDefinedUnits();
  }

  getDefinedUnits(): void {
    this.hallViewingService.getDefinedUnits(this.performance).subscribe(
      defUnits => this.defUnits = defUnits as DefinedUnit[],
      error => {
        this.alertService.error('Sitzplätze konnten nicht geladen werden.');
      }
      ).add(
      () => this.setSelectedAndNum(this.defUnits)
    );
    for (let i = 0; i < this.defUnits.length; i++) {
      console.log("Initi: " + this.defUnits[i].selected);
    }
  }

  setSelectedAndNum(dunits: DefinedUnit[]) {
    for (let i = 0; i < dunits.length; i++) {
      dunits[i].selected = false;
      dunits[i].num = 0;
    }
  }

  clickSeat(seat: DefinedUnit) {
    if (seat.selected) {
      seat.num = 0;
    } else {
      seat.num = 1;
    }
    seat.selected = !seat.selected;
    console.log('SeatNum: ' + seat.num + ' Selected: ' + seat.selected);
  }

  getBackColor(dunit: DefinedUnit) {
    return dunit.selected ? '#FF9824' : '#CFCFCF';
  }


  getEventName(): String {
    return this.event.name;
  }

  getPerformanceName(): String {
    return this.performance.name;
  }

  getHallName() {
    return this.event.hall.name;
  }

  getStartAt() {
    return this.performance.startAt;
  }

  selectionNotEmpty() {
    let any = false;
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

  getChosenNum(dunit: DefinedUnit) {
    return this.defUnits[this.defUnits.indexOf(dunit)].num;
  }

  sectorIsSelected(dunit: DefinedUnit) {
    console.log('selected: ' + dunit.selected === null);
    return dunit.selected === null || dunit.selected;
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

  updateSelectedNum() {
    this.defUnits[this.defUnits.indexOf(this.sectorSel)].num = this.sectorNum;
  }

  getNumOfSelectedSec() {
    const index = this.defUnits.indexOf(this.sectorSel);
    return this.defUnits[index].num > 0 ? this.defUnits[index].num : 0;
  }

  checkValue() {
    if (this.sectorNum !== null) {
      const index = this.defUnits.indexOf(this.sectorSel);
      if (this.sectorNum > this.defUnits[index].free) {
        this.sectorNum = this.defUnits[index].free;
      }
      if (this.sectorNum < 0) {
        this.sectorNum = 0;
      }
      return this.sectorNum;
    }
    return 0;
  }

  sectorDone() {
    if (this.sectorNum !== null) {
      const index = this.defUnits.indexOf(this.sectorSel);
      this.defUnits[index].num = this.sectorNum;
      if (this.sectorNum !== 0) {
        this.defUnits[index].selected = true;
      } else {
        this.defUnits[index].selected = false;
      }
    }
    this.sectorSel = null;
  }

  endTransaction() {
    localStorage.setItem('definedUnits', JSON.stringify(this.defUnits));
  }

  getCatColor(id: number) {
    let tmp: number;
    for (let i = 0; i < this.cats.length; i++) {
      if (this.cats[i].id === id) {
        tmp = this.cats[i].color + 0x1000000;
        return '#' + tmp.toString(16);
      }
    }
  }

  showName(dunit: DefinedUnit) {
    let tmp = dunit.upperBoundary.coordinateY - dunit.lowerBoundary.coordinateY;
    if (tmp === 0) {
      tmp = 1;
    }
    return tmp * this.getUnitSize();
  }

  /**
   * calculats the height for the category-btn
   * @param id of the category
   */
  calcCatHeight(id: number) {
    let min = 27;
    let max = 0;
    let num = 0;
    for (let y = 0; y < this.defUnits.length; y++) {
      if (this.defUnits[y].priceCategoryId === id) {
        if (this.defUnits[y].lowerBoundary.coordinateY < min) {
          min = this.defUnits[y].lowerBoundary.coordinateY;
        }
        if (this.defUnits[y].lowerBoundary.coordinateY > max) {
          max = this.defUnits[y].lowerBoundary.coordinateY;
        }
      }
    }
    if (max === min) {
      return (this.getUnitSize() - this.getSeatDistance()) + 'px';
    } else if ((max - min) === 1) {
      return ((((this.getUnitSize() - this.getSeatDistance()) * 2)) + this.getSeatDistance()) + 'px';
    } else {
      num = max - min;
      return (((this.getUnitSize() - this.getSeatDistance()) * (num + 1)) + (this.getSeatDistance() * num)) + 'px';
    }

  }

  /**
   * calculates the top (px values of the top end of the btn) of the categorie
   * @param id of the category
   */
  calcCatPosition(id: number) {
    let min = 27;
    for (let y = 0; y < this.defUnits.length; y++) {
      if (this.defUnits[y].priceCategoryId === id) {
        if (this.defUnits[y].lowerBoundary.coordinateY < min) {
          min = this.defUnits[y].lowerBoundary.coordinateY;
        }
      }
    }
    return (min * this.getUnitSize() - this.getUnitSize() + 60 + this.getSeatDistance()) + 'px';
  }

  /**
   * calculates the position for the name of the categorie
   * on the right side of the hall plan
   * @param id of the category
   */
  calcCatNamePosition(id: number) {
    let min = 27;
    let max = 0;
    for (let y = 0; y < this.defUnits.length; y++) {
      if (this.defUnits[y].priceCategoryId === id) {
        if (this.defUnits[y].lowerBoundary.coordinateY < min) {
          min = this.defUnits[y].lowerBoundary.coordinateY;
        }
        if (this.defUnits[y].lowerBoundary.coordinateY > max) {
          max = this.defUnits[y].lowerBoundary.coordinateY;
        }
      }
    }
    if (min === max) {
      return min * this.getUnitSize() - this.getUnitSize() + 60 + this.getSeatDistance() + ((this.getUnitSize()) / 4) + 'px';
    }
    return (min * this.getUnitSize() - this.getUnitSize() + 60 + this.getSeatDistance() + ((this.getUnitSize()
      + this.getSeatDistance()) * (max - min) / 2)) + 'px';
  }


  /**
   * calculates the line height for the text (X) in a reserved seat
   */
  calcLineHeight() {
    return ((this.getUnitSize() - this.getSeatDistance()) + 5) + 'px';
  }

  /**
   * calculates the font size for a reserved seat
   * */
  calcFontSize() {
    return ((this.getUnitSize() - this.getSeatDistance()) + 10) + 'px';
  }

  /**
   * calculate position from left of one unit for css
   * @param p != null
   */
  calcPositionLeft(p: Point) {
    let left: number = p.coordinateX * this.getUnitSize() - this.getUnitSize() + 20 + this.getSeatDistance() / 2;
    if (this.hallSize.coordinateY > this.hallSize.coordinateX) {
      left += (this.hallSize.coordinateY - this.hallSize.coordinateX) * this.getUnitSize() / 2;
    }
    return left + 'px';
  }

  /**
   * calculate position from top of one unit for css
   * @param p != null
   */
  calcPositionTop(p: Point) {
    return (p.coordinateY * this.getUnitSize() - this.getUnitSize() + 60 + this.getSeatDistance()) + 'px';
  }

  /**
   * calculate size of one seat for css
   */
  calcSeatSize() {
    return (this.getUnitSize() - this.getSeatDistance()) + 'px';
  }

  /**
   * @return size of one unit (seat + space to next seat) in pixel
   */
  getUnitSize(): number {
    return Math.min(800 / this.hallSize.coordinateX, 800 / this.hallSize.coordinateY);
  }

  /**
   * @return space between two seats in pixel
   */
  getSeatDistance(): number {
    return this.getUnitSize() / 10;
  }

  /**
   * calculates X size of sector for css
   * @param sector != null && lowerBoundary != null && upperBoundary != null
   */
  calcSectorSizeX(sector: DefinedUnit) {
    return ((sector.upperBoundary.coordinateX - sector.lowerBoundary.coordinateX + 1) * this.getUnitSize() - this.getSeatDistance()) + 'px';
  }

  /**
   * calculates Y size of sector for css
   * @param sector != null && lowerBoundary != null && upperBoundary != null
   */
  calcSectorSizeY(sector: DefinedUnit) {
    return ((sector.upperBoundary.coordinateY - sector.lowerBoundary.coordinateY + 1) * this.getUnitSize() - this.getSeatDistance()) + 'px';
  }

}
