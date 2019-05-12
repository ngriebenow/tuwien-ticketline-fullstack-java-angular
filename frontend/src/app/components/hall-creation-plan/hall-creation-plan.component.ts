import { Component, OnInit } from '@angular/core';
import { HallCreationService } from '../../services/hall-creation.service';
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

  constructor(private hallCreationService: HallCreationService) {
  }

  ngOnInit() {
    this.getHallSize();
    this.getSeats();
  }

  getHallSize(): void {
    this.hallSize = this.hallCreationService.getHallSize();
  }

  getSeats(): void {
    this.hallCreationService.getSeats().subscribe(seats => this.seats = seats);
  }

  /**
   * calculate position from left of one unit for css
   */
  calcPositionLeft(p: Point) {
    let left: number = p.coordinateX * this.getUnitSize() - this.getUnitSize() + 20;
    if (this.hallSize.coordinateY > this.hallSize.coordinateX) {
      left += (this.hallSize.coordinateY - this.hallSize.coordinateX) * this.getUnitSize() / 2;
    }
    return left + 'px';
  }

  /**
   * calculate position from top of one unit for css
   */
  calcPositionTop(p: Point) {
    return (p.coordinateY * this.getUnitSize() - this.getUnitSize() + 80) + 'px';
  }

  /**
   * calculate size of one seat for css
   */
  calcSeatSize() {
    return (this.getUnitSize() - 20) + 'px';
  }

  /**
   * calculates size of sector for css
   */
  calcSectorSize(sector: Unit) {
    return null; // todo calc size + 'px'
  }

  /**
   * returns size of one unit (seat + space to next seat)
   */
  getUnitSize(): number {
    return Math.min(780 / this.hallSize.coordinateX, 780 / this.hallSize.coordinateY);
  }
}
