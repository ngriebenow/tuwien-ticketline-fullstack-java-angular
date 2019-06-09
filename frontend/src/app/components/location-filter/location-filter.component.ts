import {Component, OnInit} from '@angular/core';
import {LocationService} from '../../services/location.service';
import {FormBuilder} from '@angular/forms';
import {AlertService} from '../../services/alert.service';
import {Location} from '../../dtos/location';
import {debounceTime, distinctUntilChanged} from 'rxjs/operators';
import * as _ from 'lodash';

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
  ) {
  }

  ngOnInit() {
    this.loadLocations();
    this.activateOnFormChange();
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

  private activateOnFormChange(): void {
    this.searchForm.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged(),
    ).subscribe(values => {
      this.queryParams = {};
      Object.entries<any>(values)
        .filter(entry => entry[1] !== null)
        .map(entry => [entry[0], entry[1].toString().trim()])
        .filter(entry => !_.isEmpty(entry[1]))
        .forEach(entry => {
          const [key, val] = entry;
          this.queryParams[key] = val;
        });
      this.loadLocations();
    });
  }

  private nextPage(): void {
    this.page++;
    this.loadLocations();
  }

  private previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadLocations();
    }
  }

  /**
   * Returns the color for the selctor
   * @param cat: true if it is the first option
   */
  getColor(cat: boolean): string {
    if (cat) {
      return '#EAEAEA';
    } else {
      return '#8FBEFF';
    }
  }

  /**
   * Returns the text color for the selctor
   * @param cat: true if it is the first option
   */
  getTextColor(cat: boolean): string {
    if (cat) {
      return '#CFCFCF';
    } else {
      return '#FFFFFF';
    }
  }
}
