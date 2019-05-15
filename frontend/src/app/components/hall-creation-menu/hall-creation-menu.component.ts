import { Component, OnInit } from '@angular/core';
import {HallCreationService} from '../../services/hall-creation.service';
import {Point} from '../../dtos/Point';
import {UnitType} from '../../enums/unit-type';
import {Direction} from '../../enums/direction';

@Component({
  selector: 'app-hall-creation-menu',
  templateUrl: './hall-creation-menu.component.html',
  styleUrls: ['./hall-creation-menu.component.scss']
})
export class HallCreationMenuComponent implements OnInit {

  hallSize: Point;
  unitType: typeof UnitType = UnitType;
  direction: typeof Direction = Direction;

  constructor(private hallCreationService: HallCreationService) { }

  ngOnInit() {
    this.hallSize = this.hallCreationService.getHallSize();
  }

  initializationMode(): boolean {
    return (this.hallCreationService.initialized === false && this.hallCreationService.edited === false);
  }

  editingMode(): boolean {
    return (this.hallCreationService.initialized === true && this.hallCreationService.edited === false);
  }

  sectorMode(): boolean {
    return (this.hallCreationService.initialized === true && this.hallCreationService.edited === true);
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
  }

  updatePlan(): void {
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

  getMaxHallSize(): Point {
    return this.hallCreationService.getMaxHallSize();
  }
}
