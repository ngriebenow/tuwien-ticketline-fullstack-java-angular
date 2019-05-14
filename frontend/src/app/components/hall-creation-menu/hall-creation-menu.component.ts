import { Component, OnInit } from '@angular/core';
import {HallCreationService} from '../../services/hall-creation.service';
import {Point} from '../../dtos/Point';
import {UnitType} from '../../enums/unit-type';

@Component({
  selector: 'app-hall-creation-menu',
  templateUrl: './hall-creation-menu.component.html',
  styleUrls: ['./hall-creation-menu.component.scss']
})
export class HallCreationMenuComponent implements OnInit {

  hallSize: Point;
  unitType: typeof UnitType = UnitType;

  constructor(private hallCreationService: HallCreationService) { }

  ngOnInit() {
    this.hallSize = this.hallCreationService.getHallSize();
  }

  updatePlan(): void {
    this.hallCreationService.fillWithSeats();
  }

  selectUnitType(unitType: UnitType): void {
    this.hallCreationService.selectUnitType(unitType);
  }

  getMaxHallSize(): Point {
    return this.hallCreationService.getMaxHallSize();
  }
}
