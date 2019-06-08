import {EventEmitter, Injectable, Output} from '@angular/core';
import {Point} from '../dtos/Point';
import {Unit} from '../dtos/unit';
import {UnitType} from '../enums/unit-type';
import {Direction} from '../enums/direction';
import {Hall} from '../dtos/hall';
import {HallService} from './hall.service';
import {HallRequest} from '../dtos/hall-request';
import {Router} from '@angular/router';
import {AlertService} from './alert.service';

@Injectable({
  providedIn: 'root'
})
export class HallCreationService {

  private loadedExisting: boolean;
  private initialized: boolean;
  private edited: boolean;

  private readonly hall: Hall;
  private readonly hallSize: Point;
  private readonly maxHallSize: Point;

  private readonly seats: Point[];
  private readonly sectors: Unit[];
  private readonly aisles: Point[];

  private selectedUnitType: UnitType; // saves unit type that is selected in menu
  private selectedUnitPosition: Point; // saves first sector coordinate for sector creation
  selectedSector: Unit; // saves sector for sector editing

  @Output() changeSector: EventEmitter<Unit> = new EventEmitter();

  constructor(private hallService: HallService, private router: Router, private alertService: AlertService) {
    console.log('constructor');
    this.maxHallSize = new Point(27, 27); // set max hall size here
    this.hallSize = new Point(null, null);
    this.hall = new Hall(null, null, null, null, null);
    this.seats = [];
    this.sectors = [];
    this.aisles = [];
    this.createNewHall();
  }

  /**
   * @return Unit with lower and upperBoundary according to p1 and p2
   * @param p1 != null
   * @param p2 != null
   */
  static calculateSectorBoundaries(p1: Point, p2: Point): Unit {
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
  static sectorsOverlapping(sector1: Unit, sector2: Unit): boolean {
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
  static pointInSector(point: Point, sector: Unit): boolean {
    return (
      point.coordinateX >= sector.upperBoundary.coordinateX &&
      point.coordinateX <= sector.lowerBoundary.coordinateX &&
      point.coordinateY >= sector.upperBoundary.coordinateY &&
      point.coordinateY <= sector.lowerBoundary.coordinateY
    );
  }

  /**
   * Call this or loadExistingHall at start of hall creation process
   * creates new hall
   */
  createNewHall(): void {
    console.log('create new hall');
    this.loadedExisting = false;
    this.initialized = false;
    this.edited = false;
    this.hallSize.coordinateX = 10;
    this.hallSize.coordinateY = 10;
    this.hall.id = null;
    this.hall.version = null;
    this.hall.name = null;
    this.hall.location = null;
    this.hall.boundaryPoint = this.hallSize;
    this.seats.length = 0;
    this.sectors.length = 0;
    this.aisles.length = 0;
    this.fillWithSeats();
  }

  /**
   * Call this or createNewHall at start of hall creation process
   * loads hall from backend and sets initialized to true
   */
  loadExistingHall(id: number): void {
    this.hallService.getHallById(id).then(
      result => result.subscribe(
        (loadedHall: Hall) => {
          this.hallSize.coordinateX = loadedHall.boundaryPoint.coordinateX;
          this.hallSize.coordinateY = loadedHall.boundaryPoint.coordinateY;
          this.hall.id = loadedHall.id;
          this.hall.version = loadedHall.version;
          this.hall.name = loadedHall.name;
          this.hall.location = loadedHall.location;
          this.hall.boundaryPoint = this.hallSize;
          this.loadExistingHall_loadUnits(id);
        },
        error => {
          console.log(error);
          this.alertService.error('Der Saal konnte nicht geladen werden.');
          this.createNewHall();
        }
      )
    );
  }

  /**
   * loads units of hall from db
   * @param id of hall
   */
  loadExistingHall_loadUnits(id: number): void {
    this.seats.length = 0;
    this.sectors.length = 0;
    this.aisles.length = 0;
    this.hallService.getUnitsByHallId(id).subscribe(
      (dbUnits: Unit[]) => {
        for (let i = 0; i < dbUnits.length; i++) {
          if (dbUnits[i].capacity === 1) {
            this.seats.push(dbUnits[i].upperBoundary);
          } else if (dbUnits[i].capacity > 1) {
            this.sectors.push(dbUnits[i]);
          }
        }
        this.createAisles();
        this.loadedExisting = true;
        this.initialized = true;
        this.edited = false;
      },
      error => {
        console.log(error);
        this.alertService.error('Der Saal konnte nicht geladen werden.');
      }
    );
  }

  /**
   * creates an aisle at every empty position of hall
   */
  createAisles(): void {
    for (let y = 0; y < this.hallSize.coordinateY; y++) {
      for (let x = 0; x < this.hallSize.coordinateX; x++) {
        let found = false;
        const aisle: Point = new Point(x + 1, y + 1);
        for (let s = 0; !found && s < this.sectors.length; s++) {
          if (HallCreationService.pointInSector(aisle, this.sectors[s])) {
            found = true;
          }
        }
        for (let s = 0; !found && s < this.seats.length; s++) {
          if (x + 1 === this.seats[s].coordinateX && y + 1 === this.seats[s].coordinateY) {
            found = true;
          }
        }
        if (!found) {
          this.aisles.push(aisle);
        }
      }
    }
  }

  /**
   * checks and ends initialization process and sets initialized to true
   */
  completeInitializing(): void {
    if (
      0 < this.hallSize.coordinateX &&
      0 < this.hallSize.coordinateY &&
      this.hallSize.coordinateX <= this.maxHallSize.coordinateX &&
      this.hallSize.coordinateY <= this.hallSize.coordinateY
    ) {
      if (!this.hall.name || !this.hall.name.trim()) {
        this.alertService.warning('Bitte Saalnamen angeben.');
      } else {
        this.initialized = true;
      }
    } else {
      this.alertService.warning('Der Saal muss zwischen 1x1 und ' + this.maxHallSize.coordinateX + 'x' +
        this.maxHallSize.coordinateY + ' groß sein.');
    }
  }

  /**
   * checks and ends editing process and sets edited to true
   * if there are sectors: selects one and goes to sector mode
   * else: saves hall to db
   */
  completeEditing(): void {
    // todo 39247 keep highlight on selector buttons
    if (this.sectors.length > 0) {
      this.changeSelectedSector(this.sectors[0]);
      this.edited = true;
    } else if (this.seats.length > 0) {
      this.saveHall();
    }
  }

  /**
   * checks and ends sector creation process and calls saveHall()
   */
  completeSectors(): void {
    if (this.validateSectors()) {
      this.saveHall();
    }
  }

  /**
   * validates all sectors name and capacity
   * @return true if validation is successful
   */
  validateSectors(): boolean {
    for (let i = 0; i < this.sectors.length; i++) {
      if (
        !this.sectors[i].name ||
        !this.sectors[i].name.trim() ||
        this.sectors[i].capacity === null ||
        this.sectors[i].capacity < 2
      ) {
        this.alertService.warning('Bitte für alle Sektoren einen Namen und Kapazität größer 1 angeben.');
        return false;
      }
    }
    return true;
  }

  /**
   * saves hall and all units to db
   */
  saveHall(): void {
    const hallToSave: HallRequest = this.prepareHallRequest();
    if (this.loadedExisting) {
      this.hallService.putHall(hallToSave).subscribe(
        () => {
          console.log('Updated hall successfully!');
          this.alertService.success('Der Saal wurde erfolgreich gepeichert.');
          this.backToMenu();
        },
        error => {
          console.log(error);
          this.alertService.error('Der Saal konnte nicht gespeichert werden.');
        }
      );
    } else {
      this.hallService.postHall(hallToSave).subscribe(
        savedHall => {
          console.log('Saved hall successfully with id: ' + savedHall.id);
          this.alertService.success('Der Saal wurde erfolgreich gepeichert.');
          this.backToMenu();
        },
        error => {
          console.log(error);
          this.alertService.error('Der Saal konnte nicht gespeichert werden.');
        }
      );
    }
  }

  /**
   * create hallRequest with all values of this.hall and all units except aisles
   */
  prepareHallRequest(): HallRequest {
    const units: Unit[] = [];
    for (const s of this.seats) {
      units.push(new Unit(null, null, 'seat', s, s, 1));
    }
    for (const s of this.sectors) {
      units.push(s);
    }
    return new HallRequest(
      this.hall.id,
      this.hall.version,
      this.hall.name,
      this.hall.location,
      this.hallSize,
      units
    );
  }

  /**
   * returns back to home menu
   */
  backToMenu(): void {
    this.createNewHall();
    this.router.navigateByUrl('');
  }

  cancelHallCreation(): void {
    // todo 39256 cancel popup and functionality
    this.createNewHall();
    this.backToMenu();
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
   * if hallSize in direction < maxHallSize:
   * adds a row or column of seats to plan at given direction
   * @param direction must be valid
   */
  expandPlanTo(direction: Direction): void {
    if (this.hallSize.coordinateX < this.maxHallSize.coordinateX) {
      if (direction === Direction.left) {
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
      }
    }
    if (this.hallSize.coordinateY < this.maxHallSize.coordinateY) {
      if (direction === Direction.top) {
        this.shiftVertical(1);
        this.hallSize.coordinateY++;
        for (let i = 0; i < this.hallSize.coordinateX; i++) {
          this.createSeat(new Point(i + 1, 1));
        }
      } else if (direction === Direction.bottom) {
        this.hallSize.coordinateY++;
        for (let i = 0; i < this.hallSize.coordinateX; i++) {
          this.createSeat(new Point(i + 1, this.hallSize.coordinateY));
        }
      }
    }
  }

  /**
   * if hallSize in direction > 1:
   * deletes a row or column from plan at given direction
   * @param direction must be valid
   */
  shrinkPlanFrom(direction: Direction): void {
    if (this.hallSize.coordinateX > 1) {
      if (direction === Direction.left) {
        this.deleteColumn(1);
        this.shiftHorizontal(-1);
        this.hallSize.coordinateX--;
      } else if (direction === Direction.right) {
        this.deleteColumn(this.hallSize.coordinateX);
        this.hallSize.coordinateX--;
      }
    }
    if (this.hallSize.coordinateY > 1) {
      if (direction === Direction.top) {
        this.deleteRow(1);
        this.shiftVertical(-1);
        this.hallSize.coordinateY--;
      } else if (direction === Direction.bottom) {
        this.deleteRow(this.hallSize.coordinateY);
        this.hallSize.coordinateY--;
      }
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
      if (HallCreationService.sectorsOverlapping(this.sectors[i], rowUnit)) {
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
      if (HallCreationService.sectorsOverlapping(this.sectors[i], rowUnit)) {
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
    if (this.initialized && !this.edited) {
      if (this.selectedUnitType === UnitType.aisle) {
        this.createAisle(seat);
        this.deleteSeat(seat);
      } else if (this.selectedUnitType === UnitType.sector) {
        this.createSector(seat);
      }
    }
  }

  /**
   * converts aisle to seat or sector according to selected unitType
   * @param aisle != null
   */
  clickAisle(aisle: Point): void {
    if (this.initialized && !this.edited) {
      if (this.selectedUnitType === UnitType.seat) {
        this.createSeat(aisle);
        this.deleteAisle(aisle);
      } else if (this.selectedUnitType === UnitType.sector) {
        this.createSector(aisle);
      }
    }
  }

  /**
   * converts sector to seats according to selected unitType
   * @param sector != null && lowerBoundary != null && upperBoundary != null
   */
  clickSector(sector: Unit): void {
    if (this.initialized && !this.edited) {
      if (this.selectedUnitType === UnitType.seat) {
        this.deleteSector(sector);
      } else if (this.selectedUnitType === UnitType.aisle) {
        this.deleteSector(sector);
      }
    } else if (this.initialized && this.edited) {
      this.changeSelectedSector(sector);
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
      const sector: Unit = HallCreationService.calculateSectorBoundaries(position, this.selectedUnitPosition);
      // delete overlapping sectors
      for (let i = this.sectors.length - 1; i >= 0; i--) {
        if (HallCreationService.sectorsOverlapping(this.sectors[i], sector)) {
          this.deleteSector(this.sectors[i]);
        }
      }
      // delete seats
      for (let i = this.seats.length - 1; i >= 0; i--) {
        if (HallCreationService.pointInSector(this.seats[i], sector)) {
          this.seats.splice(i, 1);
        }
      }
      // delete aisles
      for (let i = this.aisles.length - 1; i >= 0; i--) {
        if (HallCreationService.pointInSector(this.aisles[i], sector)) {
          this.aisles.splice(i, 1);
        }
      }
      // save sector
      this.sectors.push(sector);
      this.selectedUnitPosition = null;
    }
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

  /**
   * changes saved sector and sends it to all subscribers
   * @param sector if != null: assign, else: resend existing sector
   */
  changeSelectedSector(sector: Unit): void {
    if (sector != null) {
      this.selectedSector = sector;
    }
    this.changeSector.emit(this.selectedSector);
  }

  getInitialized(): boolean {
    return this.initialized;
  }

  getEdited(): boolean {
    return this.edited;
  }

  getHallSize(): Point {
    return this.hallSize;
  }

  setHallSize(newSize: Point): void {
    this.hallSize.coordinateX = newSize.coordinateX;
    this.hallSize.coordinateY = newSize.coordinateY;
  }

  getMaxHallSize(): Point {
    return this.maxHallSize;
  }

  getHall(): Hall {
    return this.hall;
  }

  getSeats(): Point[] {
    return this.seats;
  }

  getAisles(): Point[] {
    return this.aisles;
  }

  getSectors(): Unit[] {
    return this.sectors;
  }
}
