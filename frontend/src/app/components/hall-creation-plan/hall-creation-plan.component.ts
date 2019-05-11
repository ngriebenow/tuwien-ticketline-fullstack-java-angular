import { Component, OnInit } from '@angular/core';
import { HallCreationService } from '../../services/hall-creation.service';
import {Point} from '../../dtos/Point';
import {max, min} from "rxjs/operators";

@Component({
  selector: 'app-hall-creation-plan',
  templateUrl: './hall-creation-plan.component.html',
  styleUrls: ['./hall-creation-plan.component.scss']
})
export class HallCreationPlanComponent implements OnInit {

  hallSize: Point;
  seats: Point[];

  constructor(private hallCreationService: HallCreationService) {
    this.getSeats();
  }

  ngOnInit() {
  }

  getHallSize(): void {
    this.hallSize = this.hallCreationService.getHallSize();
  }

  getSeats(): void {
    this.seats = this.hallCreationService.getSeats();
    //this.seats = [];
    //this.seats.push(new Point(4, 5));
    //this.seats.push(new Point(1, 1));
  }

  calcPositionLeft(n: number) {
    let left: number = this.seats[n].coordinateX * this.unitSize() - this.unitSize() + 20;
    if (this.hallSize.coordinateY > this.hallSize.coordinateX) {
      left = left + (this.hallSize.coordinateY - this.hallSize.coordinateX) * this.unitSize() / 2;
    }
    return left + 'px';
  }

  calcPositionTop(n: number) {
    return (this.seats[n].coordinateY * this.unitSize() - this.unitSize() + 80) + 'px';
  }

  calcSize() {
    this.getHallSize();
    return (this.unitSize() - 20) + 'px';
  }

  unitSize() {
    this.getHallSize();
    return Math.min(780 / this.hallSize.coordinateX, 780 / this.hallSize.coordinateY);
  }
}
