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

  hallName: string;
  hallSize: Point;

  seats: Point[];
  aisles: Unit[];
  sectors: Unit[];

  constructor() {
    this.hallSize = new Point(10, 10);
    this.seats = [];
    this.fillWithSeats();
  }

  /**
   * loads hall from backend and sets initialized to true
   */
  loadHall(): void {
  }

  /**
   * checks and ends initialization process and sets initialized to true
   */
  completeInitializing(): void {
    this.hallName = 'hall_1';
    this.initialized = true;
  }

  /**
   * checks and ends editing process and sets edited to true
   */
  completeEditing(): void {
    this.edited = true;
  }

  /**
   * checks and ends sector creation process and calls saveHall()
   */
  completeSectors(): void {
  }

  /**
   * saves hall and all units to db
   */
  saveHall(): void {
  }

  /**
   * fills whole hall with seats
   */
  fillWithSeats(): void {
    this.seats.length = 0;
    for (let i = 0; i < this.hallSize.coordinateX; i++) {
      for (let j = 0; j < this.hallSize.coordinateY; j++) {
        this.seats.push(new Point(i + 1, j + 1));
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
