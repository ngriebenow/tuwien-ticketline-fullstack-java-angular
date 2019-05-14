import {Component, OnInit} from '@angular/core';
import {HallCreationService} from '../../services/hall-creation.service';
import {Point} from '../../dtos/Point';
import {Unit} from '../../dtos/unit';

@Component({
  selector: 'app-hall-creation-plan',
  templateUrl: './hall-creation-plan.component.html',
  styleUrls: ['./hall-creation-plan.component.scss']
})
export class HallCreationPlanComponent implements OnInit {

  hallSize: Point;
  seats: Point[];
  sectors: Unit[];
  aisles: Point[];

  constructor(private hallCreationService: HallCreationService) {
  }

  ngOnInit() {
    this.getHallSize();
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

  getMaxHallSize(): Point {
    return this.hallCreationService.getMaxHallSize();
  }

  getSeats(): void {
    this.hallCreationService.getSeats().subscribe(seats => this.seats = seats);
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
    return ((sector.lowerBoundary.coordinateX - sector.upperBoundary.coordinateX + 1) * this.getUnitSize() - this.getSeatDistance()) + 'px';
  }

  /**
   * calculates Y size of sector for css
   * @param sector != null && lowerBoundary != null && upperBoundary != null
   */
  calcSectorSizeY(sector: Unit) {
    return ((sector.lowerBoundary.coordinateY - sector.upperBoundary.coordinateY + 1) * this.getUnitSize() - this.getSeatDistance()) + 'px';
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
   * @return true if hallSize is valid
   */
  legalHallSize(): boolean {
    if (this.hallSize.coordinateX <= this.getMaxHallSize().coordinateX &&
      this.hallSize.coordinateY <= this.getMaxHallSize().coordinateY &&
      this.hallSize.coordinateX > 0 &&
      this.hallSize.coordinateY > 0
    ) {
      return true;
    } else {
      return false;
    }
  }
}
