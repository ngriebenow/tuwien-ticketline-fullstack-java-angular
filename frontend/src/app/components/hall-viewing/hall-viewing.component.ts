import {Component, NgModule, OnInit} from '@angular/core';
import {HallViewingService} from '../../services/hall-viewing.service';
import {ActivatedRoute, RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';
import {Point} from 'src/app/dtos/Point';
import {DefinedUnit} from 'src/app/dtos/defined-unit';
import {Performance} from 'src/app/dtos/performance';
import {PriceCategory} from 'src/app/dtos/price-category';


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
    RouterTestingModule
  ]
})
export class HallViewingComponent implements OnInit {

  hallSize: Point;
  seats: Point[];
  defUnits: DefinedUnit[];
  cats: PriceCategory[];
  sectorNum: number;

  constructor(private route: ActivatedRoute,
              private hallViewingService: HallViewingService) {
    this.cats = this.hallViewingService.getCats();
    this.seats = this.hallViewingService.getSeats();
    this.defUnits = this.hallViewingService.getDefUnits();
  }

  ngOnInit() {
    this.getHallSize();
    this.getSeats();
  }

  clickSeat(seat: DefinedUnit) {
    this.hallViewingService.clickSeat(seat);
  }

  getBackColor(dunit: DefinedUnit) {
    return this.hallViewingService.getBackColor(dunit);
  }


  getEventName(): String {
    return this.hallViewingService.getEventName();
  }

  getPerformanceName(): String {
    return this.hallViewingService.getPerformanceName();
  }

  getHallName() {
    return this.hallViewingService.getHallName();
  }

  getStartAt() {
    return this.hallViewingService.getStartAt();
  }

  getHallSize(): void {
    this.hallSize = this.hallViewingService.getHallSize();
  }

  getSeats(): void {
    this.hallViewingService.getSeats();
  }

  selectionNotEmpty() {
    return this.hallViewingService.selectionNotEmpty();
  }

  getTicketSum() {
    return this.hallViewingService.getTicketSum();
  }

  sectorSelected(dunit: DefinedUnit) {
    this.hallViewingService.sectorSelected(dunit);
  }

  anySectorSelected() {
    return this.hallViewingService.anySectorSelected();
  }

  getSelectedSectorName() {
    return this.hallViewingService.getSelectedSectorName();
  }

  getSelectedSectorCap() {
    return this.hallViewingService.getSelectedSectorCap();
  }

  getSelectedSectorFree() {
    return this.hallViewingService.getSelectedSectorFree();
  }

  updateSelectedNum() {
    this.hallViewingService.updateSelectedNum(this.sectorNum);
  }

  getNumOfSelectedSec() {
    return this.hallViewingService.getNumOfSelectedSec();
  }

  getChosenNum(dunit: DefinedUnit) {
    return this.hallViewingService.getChosenNum(dunit);
  }

  checkValue() {
    this.sectorNum = this.sectorNum = this.hallViewingService.checkValue(this.sectorNum);
  }

  sectorIsSelected(dunit: DefinedUnit) {
    return this.hallViewingService.sectorIsSelected(dunit);
  }

  sectorDone() {
    this.hallViewingService.sectorDone(this.sectorNum);
  }

  endTransaction() {
    this.hallViewingService.endTransaction();
  }

  getCatColor(id: number) {
    let tmp: number;
    for (let i = 0; i < this.cats.length; i++) {
      if (this.cats[i].id === id) {
        tmp = this.cats[i].color + 0x1000000;
        return '#' + tmp.toString(16).substr(1);
      }
    }
  }

  showName(dunit: DefinedUnit) {
    let tmp = dunit.point2.coordinateY - dunit.point1.coordinateY;
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
      if (this.defUnits[y].priceCategory === id) {
        if (this.defUnits[y].point1.coordinateY < min) {
          min = this.defUnits[y].point1.coordinateY;
        }
        if (this.defUnits[y].point1.coordinateY > max) {
          max = this.defUnits[y].point1.coordinateY;
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
      if (this.defUnits[y].priceCategory === id) {
        if (this.defUnits[y].point1.coordinateY < min) {
          min = this.defUnits[y].point1.coordinateY;
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
      if (this.defUnits[y].priceCategory === id) {
        if (this.defUnits[y].point1.coordinateY < min) {
          min = this.defUnits[y].point1.coordinateY;
        }
        if (this.defUnits[y].point1.coordinateY > max) {
          max = this.defUnits[y].point1.coordinateY;
        }
      }
    }
    if (min === max) {
      return min * this.getUnitSize() - this.getUnitSize() + 60 + this.getSeatDistance() + ((this.getUnitSize()) / 4) + 'px';
    }
    return (min * this.getUnitSize() - this.getUnitSize() + 60 + this.getSeatDistance() + ((this.getUnitSize() - this.getSeatDistance()) * (max - min) / 2)) + 'px';
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
    return ((sector.point2.coordinateX - sector.point1.coordinateX + 1) * this.getUnitSize() - this.getSeatDistance()) + 'px';
  }

  /**
   * calculates Y size of sector for css
   * @param sector != null && lowerBoundary != null && upperBoundary != null
   */
  calcSectorSizeY(sector: DefinedUnit) {
    return ((sector.point2.coordinateY - sector.point1.coordinateY + 1) * this.getUnitSize() - this.getSeatDistance()) + 'px';
  }

}
