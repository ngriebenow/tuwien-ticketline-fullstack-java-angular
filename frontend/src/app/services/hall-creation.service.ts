import {Injectable} from '@angular/core';
import { Observable, of } from 'rxjs';
import {Hall} from '../dtos/hall';
import {Point} from '../dtos/Point';
import {Location} from '../dtos/location';
import {Unit} from '../dtos/unit';

@Injectable({
  providedIn: 'root'
})
export class HallCreationService {

  initialized = false;
  edited = false;

  hall: Hall = new Hall(-1, -1, 'xy', new Location(3, 'yz', 'yz', 'yz', 'yz', 'yz'), new Point(5, 5));
  hallName = 'xy';
  hallSize: Point;

  seats: Point[];
  aisles: Unit[] = [];
  sectors: Unit[] = [];
  point1: Point = new Point(1, 1);
  point2: Point = new Point(1, 2);
  unit1: Unit = new Unit(-1, -1, '', this.point1, this.point1, 1);

  constructor() {
    this.hallSize = new Point(10, 10);
    this.completeInitializing();
  }

  // ends initialization process and sets initialized to true
  completeInitializing(): void {
    this.hallName = 'hall_1';
    this.seats = [];
    for (let i = 1; i < this.hallSize.coordinateX + 1; i++) {
      for (let j = 1; j < this.hallSize.coordinateY + 1; j++) {
        this.seats.push(new Point(i, j));
      }
    }
    this.initialized = true;
  }

  // ends editing process and sets edited to true
  completeEditing(): void {
    this.edited = true;
  }

  // ends sector creation process and calls saveHall()
  completeSectors(): void {
  }

  // saves hall and all units to db
  saveHall(): void {
  }

  updateSeats(): void {
    // for (let i = 0; i < this.seats.length; i++) {
      // this.seats.pop();
    // }
    this.seats.length = 0;
    for (let i = 1; i < this.hallSize.coordinateX / 1 + 1; i++) {
      for (let j = 1; j < this.hallSize.coordinateY / 1 + 1; j++) {
        this.seats.push(new Point(i, j));
      }
    }
  }

  getHallSize(): Point {
    return this.hallSize;
  }

  getSeats(): Observable<Point[]> {
    return of (this.seats);
  }
}
