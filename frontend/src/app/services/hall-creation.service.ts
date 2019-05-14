import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {Point} from '../dtos/Point';
import {Unit} from '../dtos/unit';
import {UnitType} from '../enums/unit-type';

@Injectable({
  providedIn: 'root'
})
export class HallCreationService {

  initialized: boolean;
  edited: boolean;

  hallName: string;
  hallSize: Point;
  maxHallSize: Point;

  seats: Point[];
  sectors: Unit[];
  aisles: Point[];

  selectedUnitType: UnitType;
  selectedUnitPosition: Point;

  constructor() {
    this.initialized = false;
    this.edited = false;
    this.hallSize = new Point(10, 10);
    this.maxHallSize = new Point(25, 25);
    this.seats = [];
    this.sectors = [];
    this.aisles = [];
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
   * does not delete any aisles or sectors
   */
  fillWithSeats(): void {
    if (this.hallSize.coordinateX <= this.maxHallSize.coordinateX && this.hallSize.coordinateY <= this.maxHallSize.coordinateY) {
      this.seats.length = 0;
      for (let i = 0; i < this.hallSize.coordinateX; i++) {
        for (let j = 0; j < this.hallSize.coordinateY; j++) {
          this.createSeat(new Point(i + 1, j + 1));
        }
      }
    }
  }

  selectUnitType(unitType: UnitType): void {
    this.selectedUnitPosition = null;
    this.selectedUnitType = unitType;
  }

  clickSeat(seat: Point): void {
    if (this.selectedUnitType === UnitType.aisle) {
      this.createAisle(seat);
      this.deleteSeat(seat);
    } else if (this.selectedUnitType === UnitType.sector) {
      this.createSector(seat);
    }
  }

  clickAisle(aisle: Point): void {
    if (this.selectedUnitType === UnitType.seat) {
      this.createSeat(aisle);
      this.deleteAisle(aisle);
    } else if (this.selectedUnitType === UnitType.sector) {
      this.createSector(aisle);
    }
  }

  clickSector(sector: Unit): void {
    if (this.selectedUnitType === UnitType.seat) {
      this.deleteSector(sector);
    } else if (this.selectedUnitType === UnitType.aisle) {
      this.deleteSector(sector);
    }
  }

  createSeat(position: Point): void {
    this.seats.push(position);
  }

  createAisle(position: Point): void {
    this.aisles.push(position);
  }

  /**
   * if selectedUnitPosition == null: save position
   * else create sector from selectedUnitPosition to position and
   * delete all sectors, seats and aisles that would overlap
   * @param position != null
   */
  createSector(position: Point): void {
    if (this.selectedUnitPosition === null) { // save first corner position
      this.selectedUnitPosition = position;
    } else { // create sector from first corner to position
      // calculate sector
      const lowerBoundary: Point = new Point(
        Math.max(position.coordinateX, this.selectedUnitPosition.coordinateX),
        Math.max(position.coordinateY, this.selectedUnitPosition.coordinateY)
      );
      const upperBoundary: Point = new Point(
        Math.min(position.coordinateX, this.selectedUnitPosition.coordinateX),
        Math.min(position.coordinateY, this.selectedUnitPosition.coordinateY)
      );
      const sector: Unit = new Unit(null, null, null, lowerBoundary, upperBoundary, null);

      // delete overlapping sectors
      for (let i = this.sectors.length - 1; i >= 0; i--) {
        if (this.sectorsOverlapping(this.sectors[i], sector)) {
          this.deleteSector(this.sectors[i]);
        }
      }
      // delete seats
      for (let i = this.seats.length - 1; i >= 0; i--) {
        if (this.pointInSector(this.seats[i], sector)) {
          this.seats.splice(i, 1);
        }
      }
      // delete aisles
      for (let i = this.aisles.length - 1; i >= 0; i--) {
        if (this.pointInSector(this.aisles[i], sector)) {
          this.aisles.splice(i, 1);
        }
      }

      this.sectors.push(new Unit(null, null, null, lowerBoundary, upperBoundary, null));
      this.selectedUnitPosition = null;
    }
  }

  /**
   * @return true if sector1 and sector2 overlap
   * @param sector1 !- null && lowerBoundary != null && upperBoundary != null
   * @param sector2 != null && lowerBoundary != null && upperBoundary != null
   */
  sectorsOverlapping(sector1: Unit, sector2: Unit): boolean {
    return !(
      sector1.lowerBoundary.coordinateX < sector2.upperBoundary.coordinateX ||
      sector2.lowerBoundary.coordinateX < sector1.upperBoundary.coordinateX ||
      sector1.lowerBoundary.coordinateY < sector2.upperBoundary.coordinateY ||
      sector2.lowerBoundary.coordinateY < sector1.upperBoundary.coordinateY
    );
  }

  /**
   * @return true if point is inside sector
   * @param point != null
   * @param sector != null && lowerBoundary != null && upperBoundary != null
   */
  pointInSector(point: Point, sector: Unit): boolean {
    return (
      point.coordinateX >= sector.upperBoundary.coordinateX &&
      point.coordinateX <= sector.lowerBoundary.coordinateX &&
      point.coordinateY >= sector.upperBoundary.coordinateY &&
      point.coordinateY <= sector.lowerBoundary.coordinateY
    );
  }

  /**
   * deletes seat
   * @param seat != null
   */
  deleteSeat(seat: Point): void {
    this.seats.splice(this.seats.indexOf(seat), 1);
  }

  /**
   * deletes aisle
   * @param aisle != null
   */
  deleteAisle(aisle: Point): void {
    this.aisles.splice(this.aisles.indexOf(aisle), 1);
  }

  /**
   * deletes sector and fills all places occupied by sector with seats
   * @param sector != null && lowerBoundary != null && upperBoundary != null
   */
  deleteSector(sector: Unit): void {
    this.sectors.splice(this.sectors.indexOf(sector), 1);
    for (let x = sector.upperBoundary.coordinateX; x <= sector.lowerBoundary.coordinateX; x++) {
      for (let y = sector.upperBoundary.coordinateY; y <= sector.lowerBoundary.coordinateY; y++) {
        this.seats.push(new Point(x, y));
      }
    }
  }

  getHallSize(): Point {
    return this.hallSize;
  }

  getMaxHallSize(): Point {
    return this.maxHallSize;
  }

  getSeats(): Observable<Point[]> { // todo change this back to non observable?
    return of(this.seats);
  }

  getAisles(): Point[] {
    return this.aisles;
  }

  getSectors(): Unit[] {
    return this.sectors;
  }
}
