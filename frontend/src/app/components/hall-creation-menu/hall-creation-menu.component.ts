import {Component, NgModule, OnInit} from '@angular/core';
import {HallCreationService} from '../../services/hall-creation.service';
import {Point} from '../../dtos/Point';
import {UnitType} from '../../enums/unit-type';
import {Direction} from '../../enums/direction';
import {Hall} from '../../dtos/hall';
import {Unit} from '../../dtos/unit';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';

@Component({
  selector: 'app-hall-creation-menu',
  templateUrl: './hall-creation-menu.component.html',
  styleUrls: ['./hall-creation-menu.component.scss']
})
@NgModule({
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    RouterTestingModule,
  ],
})
export class HallCreationMenuComponent implements OnInit {

  hallSize: Point;
  maxHallSize: Point;
  hall: Hall;
  unitType: typeof UnitType = UnitType;
  direction: typeof Direction = Direction;

  selectedSector: Unit;

  // todo delete after locations are implemented
  id = 1;

  constructor(private hallCreationService: HallCreationService) {
  }

  ngOnInit() {
    this.hallSize = this.hallCreationService.getHallSize();
    this.getHall();
    this.getMaxHallSize();
    this.getSelectedSector();
  }

  initializationMode(): boolean {
    return (this.hallCreationService.getInitialized() === false && this.hallCreationService.getEdited() === false);
  }

  editingMode(): boolean {
    return (this.hallCreationService.getInitialized() === true && this.hallCreationService.getEdited() === false);
  }

  sectorMode(): boolean {
    return (this.hallCreationService.getInitialized() === true && this.hallCreationService.getEdited() === true);
  }

  /**
   * checks and ends initialization process and sets initialized to true
   */
  completeInitializing(): void {
    this.hallCreationService.completeInitializing();
  }

  /**
   * checks and ends editing process and sets edited to true
   */
  completeEditing(): void {
    this.hallCreationService.completeEditing();
  }

  /**
   * checks and ends sector creation process and calls saveHall()
   */
  completeSectors(): void {
    this.hallCreationService.completeSectors();
  }

  cancelHallCreation(): void {
    this.hallCreationService.cancelHallCreation();
  }

  updatePlan(): void {
    if (this.hallSize.coordinateX < 1) {
      this.hallSize.coordinateX = 1;
    } else if (this.hallSize.coordinateX > this.maxHallSize.coordinateX) {
      this.hallSize.coordinateX = this.maxHallSize.coordinateX;
    }
    if (this.hallSize.coordinateY < 1) {
      this.hallSize.coordinateY = 1;
    } else if (this.hallSize.coordinateY > this.maxHallSize.coordinateY) {
      this.hallSize.coordinateY = this.maxHallSize.coordinateY;
    }
    this.hallCreationService.fillWithSeats();
  }

  expandPlanTo(direction: Direction): void {
    this.hallCreationService.expandPlanTo(direction);
  }

  shrinkPlanFrom(direction: Direction): void {
    this.hallCreationService.shrinkPlanFrom(direction);
  }

  selectUnitType(unitType: UnitType): void {
    this.hallCreationService.selectUnitType(unitType);
  }

  getMaxHallSize(): void {
    this.maxHallSize = this.hallCreationService.getMaxHallSize();
  }

  getHall(): void {
    this.hall = this.hallCreationService.getHall();
  }

  getSelectedSector(): void {
    this.hallCreationService.changeSector.subscribe(selectedSectorX => this.selectedSector = selectedSectorX);
  }

  clipCapacity(): void {
    if (this.selectedSector.capacity < 2) {
      this.selectedSector.capacity = 2;
    } else if (this.selectedSector.capacity > Number.MAX_SAFE_INTEGER) {
      this.selectedSector.capacity = Number.MAX_SAFE_INTEGER;
    }
  }

  // todo delete after locations are implemented
  loadHall() {
    console.log(this.id);
    this.hallCreationService.loadExistingHall(this.id);
    // this.router.navigateByUrl('/hall-creation', {skipLocationChange: true});
  }
}
