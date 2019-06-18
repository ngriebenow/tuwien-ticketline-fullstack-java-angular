import {Component, NgModule, OnInit, OnDestroy} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ActivatedRoute, RouterModule} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {HallCreationPlanComponent} from '../hall-creation-plan/hall-creation-plan.component';
import {HallCreationMenuComponent} from '../hall-creation-menu/hall-creation-menu.component';
import {HallCreationService} from '../../services/hall-creation.service';
import {LocationFilterComponent} from '../location-filter/location-filter.component';

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
    HallCreationMenuComponent,
    LocationFilterComponent,
  ],
})
export class HallCreationComponent implements OnInit, OnDestroy {

  constructor(private hallCreationService: HallCreationService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.hallCreationService.openedPage();
  }

  ngOnDestroy() {
    this.hallCreationService.leavePage();
  }

}
