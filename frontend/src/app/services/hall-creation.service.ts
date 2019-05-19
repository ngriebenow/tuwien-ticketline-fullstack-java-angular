import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {Point} from '../dtos/Point';
import {Unit} from '../dtos/unit';
import {UnitType} from '../enums/unit-type';
import {Direction} from '../enums/direction';

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
    this.maxHallSize = new Point(27, 27); // set max hall size here
    this.seats = [];
    this.sectors = [];
    this.aisles = [];
    this.fillWithSeats();
    this.completeInitializing();
  }

  /**
   * loads hall from backend and sets initialized to true
   */
  loadHall(): void {
    // todo load hall from backend
    this.initialized = true;
  }

  /**
   * checks and ends initialization process and sets initialized to true
   */
  completeInitializing(): void {
    // todo validate hallSize (0 < hallSize <= maxHallSize)
    // todo create name field
    // todo validate name field
    this.initialized = true;
  }

  /**
   * checks and ends editing process and sets edited to true
   */
  completeEditing(): void {
    // todo disable resize buttons before hall gets to big/small
    // todo validate (seats && sectors != null)
    this.edited = true;
  }

  /**
   * checks and ends sector creation process and calls saveHall()
   */
  completeSectors(): void {
    // todo validate sectors (name, capacity)
  }

  /**
   * saves hall and all units to db
   */
  saveHall(): void {
    // todo save hall to backend
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

  /**
   * adds a row or column of seats to plan at given direction
   * @param direction must be valid
   */
  expandPlanTo(direction: Direction): void {
    if (direction === Direction.top) {
      this.shiftVertical(1);
      this.hallSize.coordinateY++;
      for (let i = 0; i < this.hallSize.coordinateX; i++) {
        this.createSeat(new Point(i + 1, 1));
      }
    } else if (direction === Direction.left) {
      this.shiftHorizontal(1);
      this.hallSize.coordinateX++;
      for (let i = 0; i < this.hallSize.coordinateY; i++) {
        this.createSeat(new Point(1, i + 1));
      }
    } else if (direction === Direction.right) {
      this.hallSize.coordinateX++;
      for (let i = 0; i < this.hallSize.coordinateY; i++) {
        this.createSeat(new Point(this.hallSize.coordinateX, i + 1));
      }
    } else if (direction === Direction.bottom) {
      this.hallSize.coordinateY++;
      for (let i = 0; i < this.hallSize.coordinateX; i++) {
        this.createSeat(new Point(i + 1, this.hallSize.coordinateY));
      }
    }
  }

  /**
   * deletes a row or column from plan at given direction
   * @param direction must be valid
   */
  shrinkPlanFrom(direction: Direction): void {
    if (direction === Direction.top) {
      this.deleteRow(1);
      this.shiftVertical(-1);
      this.hallSize.coordinateY--;
    } else if (direction === Direction.left) {
      this.deleteColumn(1);
      this.shiftHorizontal(-1);
      this.hallSize.coordinateX--;
    } else if (direction === Direction.right) {
      this.deleteColumn(this.hallSize.coordinateX);
      this.hallSize.coordinateX--;
    } else if (direction === Direction.bottom) {
      this.deleteRow(this.hallSize.coordinateY);
      this.hallSize.coordinateY--;
    }
  }

  /**
   * deletes all seats, aisles and sectors in given row
   * @param row > 0 && row <= hallSize.coordinateY
   */
  deleteRow(row: number): void {
    // delete sectors
    const rowUnit = new Unit(null, null, null, new Point(this.hallSize.coordinateX, row), new Point(1, row), null);
    for (let i = this.sectors.length - 1; i >= 0; i--) {
      if (this.sectorsOverlapping(this.sectors[i], rowUnit)) {
        this.deleteSector(this.sectors[i]);
      }
    }
    // delete seats and aisles
    for (let column = 0; column < this.hallSize.coordinateX; column++) {
      const position = new Point(column + 1, row);
      for (let i = this.seats.length - 1; i >= 0; i--) {
        if (position.coordinateX === this.seats[i].coordinateX && position.coordinateY === this.seats[i].coordinateY) {
          this.deleteSeat(this.seats[i]);
        }
      }
      for (let i = this.aisles.length - 1; i >= 0; i--) {
        if (position.coordinateX === this.aisles[i].coordinateX && position.coordinateY === this.aisles[i].coordinateY) {
          this.deleteAisle(this.aisles[i]);
        }
      }
    }
  }

  /**
   * deletes all seats, aisles and sectors in given column
   * @param column > 0 && column <= hallSize.coordinateX
   */
  deleteColumn(column: number): void {
    // delete sectors
    const rowUnit = new Unit(null, null, null, new Point(column, this.hallSize.coordinateY), new Point(column, 1), null);
    for (let i = this.sectors.length - 1; i >= 0; i--) {
      if (this.sectorsOverlapping(this.sectors[i], rowUnit)) {
        this.deleteSector(this.sectors[i]);
      }
    }
    // delete seats and aisles
    for (let row = 0; row < this.hallSize.coordinateY; row++) {
      const position = new Point(column, row + 1);
      for (let i = this.seats.length - 1; i >= 0; i--) {
        if (position.coordinateX === this.seats[i].coordinateX && position.coordinateY === this.seats[i].coordinateY) {
          this.deleteSeat(this.seats[i]);
        }
      }
      for (let i = this.aisles.length - 1; i >= 0; i--) {
        if (position.coordinateX === this.aisles[i].coordinateX && position.coordinateY === this.aisles[i].coordinateY) {
          this.deleteAisle(this.aisles[i]);
        }
      }
    }
  }

  /**
   * shifts whole hall vertically
   * @param n amount by witch hall is shifted. n > 0: shift to bottom. n < 0: shift to top
   */
  shiftVertical(n: number): void {
    for (let i = 0; i < this.seats.length; i++) {
      this.seats[i].coordinateY += n;
    }
    for (let i = 0; i < this.aisles.length; i++) {
      this.aisles[i].coordinateY += n;
    }
    for (let i = 0; i < this.sectors.length; i++) {
      this.sectors[i].upperBoundary.coordinateY += n;
      this.sectors[i].lowerBoundary.coordinateY += n;
    }
  }

  /**
   * shifts whole hall horizontally
   * @param n amount by witch hall is shifted. n > 0: shift to right. n < 0: shift to left
   */
  shiftHorizontal(n: number): void {
    for (let i = 0; i < this.seats.length; i++) {
      this.seats[i].coordinateX += n;
    }
    for (let i = 0; i < this.aisles.length; i++) {
      this.aisles[i].coordinateX += n;
    }
    for (let i = 0; i < this.sectors.length; i++) {
      this.sectors[i].upperBoundary.coordinateX += n;
      this.sectors[i].lowerBoundary.coordinateX += n;
    }
  }

  /**
   * saves unitType selected in menu to execute click functions accordingly
   * resets selectedUnitPosition
   * @param unitType must be valid
   */
  selectUnitType(unitType: UnitType): void {
    this.selectedUnitPosition = null;
    this.selectedUnitType = unitType;
  }

  /**
   * converts seat to aisle or sector according to selected unitType
   * @param seat != null
   */
  clickSeat(seat: Point): void {
    if (this.selectedUnitType === UnitType.aisle) {
      this.createAisle(seat);
      this.deleteSeat(seat);
    } else if (this.selectedUnitType === UnitType.sector) {
      this.createSector(seat);
    }
  }

  /**
   * converts aisle to seat or sector according to selected unitType
   * @param aisle != null
   */
  clickAisle(aisle: Point): void {
    if (this.selectedUnitType === UnitType.seat) {
      this.createSeat(aisle);
      this.deleteAisle(aisle);
    } else if (this.selectedUnitType === UnitType.sector) {
      this.createSector(aisle);
    }
  }

  /**
   * converts sector to seats according to selected unitType
   * @param sector != null && lowerBoundary != null && upperBoundary != null
   */
  clickSector(sector: Unit): void {
    if (this.selectedUnitType === UnitType.seat) {
      this.deleteSector(sector);
    } else if (this.selectedUnitType === UnitType.aisle) {
      this.deleteSector(sector);
    }
  }

  /**
   * creates seat at given position
   * @param position != null
   */
  createSeat(position: Point): void {
    this.seats.push(position);
  }

  /**
   * creates aisle at given position
   * @param position != null
   */
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
      const sector: Unit = this.calculateSectorBoundaries(position, this.selectedUnitPosition);
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
      // save sector
      this.sectors.push(sector);
      this.selectedUnitPosition = null;
    }
  }

  /**
   * @return Unit with lower and upperBoundary according to p1 and p2
   * @param p1 != null
   * @param p2 != null
   */
  calculateSectorBoundaries(p1: Point, p2: Point): Unit {
    const lowerBoundary: Point = new Point(
      Math.max(p1.coordinateX, p2.coordinateX),
      Math.max(p1.coordinateY, p2.coordinateY)
    );
    const upperBoundary: Point = new Point(
      Math.min(p1.coordinateX, p2.coordinateX),
      Math.min(p1.coordinateY, p2.coordinateY)
    );
    return new Unit(null, null, null, lowerBoundary, upperBoundary, null);
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
