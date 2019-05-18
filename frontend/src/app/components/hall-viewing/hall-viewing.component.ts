import {Component, NgModule, OnInit} from '@angular/core';
import {HallViewingService} from '../../services/hall-viewing.service';
import {ActivatedRoute, RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';
import {Point} from 'src/app/dtos/Point';
import {DefinedUnit} from 'src/app/dtos/defined-unit';
import {Performance} from 'src/app/dtos/performance';


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
  units: DefinedUnit[];
  performance: Performance;
  cats: number[];


  constructor(private route: ActivatedRoute,
              private hallViewingService: HallViewingService) {
    this.cats = this.hallViewingService.getCats();
    this.seats = this.hallViewingService.getSeats();
  }

  ngOnInit() {
    this.getPerformance();
    this.getHallSize();
    this.getSeats();
    //this.getUnits();
  }

  getPerformance(): void {

  }

  getHallSize(): void{
    this.hallSize = this.hallViewingService.getHallSize();
  }

  getSeats(): void {
    this.hallViewingService.getSeats();
  }
  /*
  getUnits(): void {
    this.units = this.hallViewingService.getUnits();
  }*/

  clickSeat(seat: Point): void {
    this.hallViewingService.clickSeat(seat);
  }

  clickUnit(unit: DefinedUnit): void {
    this.hallViewingService.clickUnit(unit);
  }

  getEventName(): String{
    return this.hallViewingService.getEventName();
  }

  getPerformanceName(): String{
    return this.hallViewingService.getPerformanceName();
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
   * calculates X size of sector for css
   * @param sector != null && lowerBoundary != null && upperBoundary != null
   */
  /*calcSectorSizeX(sector: DefinedUnit) {
    return ((sector.lowerBoundary.coordinateX - sector.upperBoundary.coordinateX + 1) * this.getUnitSize() - this.getSeatDistance()) + 'px';
  }

  /**
   * calculates Y size of sector for css
   * @param sector != null && lowerBoundary != null && upperBoundary != null

  calcSectorSizeY(sector: DefinedUnit) {
    return ((sector.lowerBoundary.coordinateY - sector.upperBoundary.coordinateY + 1) * this.getUnitSize() - this.getSeatDistance()) + 'px';
  }*/

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


}
