import {Component, NgModule, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {HallCreationPlanComponent} from '../hall-creation-plan/hall-creation-plan.component';
import {HallCreationMenuComponent} from '../hall-creation-menu/hall-creation-menu.component';

@Component({
  selector: 'app-hall-creation',
  templateUrl: './hall-creation.component.html',
  styleUrls: ['./hall-creation.component.scss']
})
@NgModule({
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    RouterTestingModule,
    HallCreationPlanComponent,
    HallCreationMenuComponent
  ],
})
export class HallCreationComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
