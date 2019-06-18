import {Component, NgModule, OnInit} from '@angular/core';
import {HallCreationService} from '../../services/hall-creation.service';
import {Point} from '../../dtos/Point';
import {Unit} from '../../dtos/unit';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ActivatedRoute, RouterModule} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {HallCreationState} from '../../enums/hall-creation-state';
import {HttpClientTestingModule} from '@angular/common/http/testing';

@Component({
  selector: 'app-hall-creation-plan',
  templateUrl: './hall-creation-plan.component.html',
  styleUrls: ['./hall-creation-plan.component.scss']
})
@NgModule({
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    RouterTestingModule,
    HttpClientTestingModule,
  ],
})
export class HallCreationPlanComponent implements OnInit {

  hallSize: Point;
  maxHallSize: Point;
  seats: Point[];
  sectors: Unit[];
  aisles: Point[];

  constructor(private hallCreationService: HallCreationService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.getHallSize();
    this.getMaxHallSize();
    this.getSeats();
    this.getSectors();
    this.getAisles();
  }

  clickSeat(seat: Point): void {
    this.hallCreationService.clickSeat(seat);
  }

  clickSector(sector: Unit): void {
    this.hallCreationService.clickSector(sector);
  }

  clickAisle(aisle: Point): void {
    this.hallCreationService.clickAisle(aisle);
  }


  getHallSize(): void {
    this.hallSize = this.hallCreationService.getHallSize();
  }

  getMaxHallSize(): void {
    this.maxHallSize = this.hallCreationService.getMaxHallSize();
  }

  getSeats(): void {
    this.seats = this.hallCreationService.getSeats();
  }

  getSectors(): void {
    this.sectors = this.hallCreationService.getSectors();
  }

  getAisles(): void {
    this.aisles = this.hallCreationService.getAisles();
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
  calcSectorSizeX(sector: Unit) {
    return this.getSectorSize(sector).coordinateX + 'px';
  }

  /**
   * calculates Y size of sector for css
   * @param sector != null && lowerBoundary != null && upperBoundary != null
   */
  calcSectorSizeY(sector: Unit) {
    return this.getSectorSize(sector).coordinateY + 'px';
  }

  /**
   * @return true if sector is big enough to show name
   * @param sector != null && lowerBoundary != null && upperBoundary != null
   */
  spaceForName(sector: Unit): boolean {
    return this.getSectorSize(sector).coordinateY > 40;
  }

  /**
   * @return true if sector is big enough for big font size
   * @param sector != null && lowerBoundary != null && upperBoundary != null
   */
  spaceForBigText(sector: Unit): boolean {
    return this.getSectorSize(sector).coordinateX > 100 &&
      this.getSectorSize(sector).coordinateY > 100;
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
   * @return size of sector in pixel
   * @param sector != null && lowerBoundary != null && upperBoundary != null
   */
  getSectorSize(sector: Unit): Point {
    return new Point(
      (sector.lowerBoundary.coordinateX - sector.upperBoundary.coordinateX + 1) * this.getUnitSize() - this.getSeatDistance(),
      (sector.lowerBoundary.coordinateY - sector.upperBoundary.coordinateY + 1) * this.getUnitSize() - this.getSeatDistance()
    );
  }

  /**
   * @return true if hallSize is valid and hall should be shown
   */
  showLegalHall(): boolean {
    return (
      this.hallSize.coordinateX <= this.maxHallSize.coordinateX &&
      this.hallSize.coordinateY <= this.maxHallSize.coordinateY &&
      this.hallSize.coordinateX > 0 &&
      this.hallSize.coordinateY > 0 &&
      !this.isSaving() &&
      !this.isLoading()
    );
  }

  /**
   * @return true if hallSize is invalid and wrong size should be shown
   */
  showIllegalSize(): boolean {
    return !this.showLegalHall() && !this.isLoading() && !this.isSaving();
  }

  /**
   * @return true if hall is loading
   */
  isLoading(): boolean {
    return this.hallCreationService.getState() === HallCreationState.Loading;
  }

  /**
   * @return true if hall is being saved
   */
  isSaving(): boolean {
    return this.hallCreationService.getState() === HallCreationState.Saving;
  }
}
