import { Component, OnInit } from '@angular/core';
import {LocationService} from '../../services/location.service';
import {FormBuilder} from '@angular/forms';
import {AlertService} from '../../services/alert.service';

@Component({
  selector: 'app-location-filter',
  templateUrl: './location-filter.component.html',
  styleUrls: ['./location-filter.component.scss']
})
export class LocationFilterComponent implements OnInit {

  locations: Location[] = [];
  page = 0;
  count = 20;
  private queryParams = {};

  searchForm = this.formBuilder.group({
    name: [''],
    street: [''],
    postalCode: [''],
    place: [''],
    country: ['']
  });

  constructor(
    private locationService: LocationService,
    private formBuilder: FormBuilder,
    private alertService: AlertService
  ) { }

  ngOnInit() {
  }

  private loadLocations(): void {
    this.queryParams['page'] = this.page;
    this.queryParams['count'] = this.count;
    this.locationService.getFiltered(this.queryParams).subscribe(
      (locations: Location[]) => {
        this.locations = locations;
      },
      error => {
        this.alertService.error('Ladefehler, bitte versuchen Sie es etwas später noch ein mal');
      }
    );
  }
}
