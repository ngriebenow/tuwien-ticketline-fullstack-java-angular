import { Component, OnInit, OnDestroy} from '@angular/core';
import {LocationService} from '../../services/location.service';
import {Location} from '../../dtos/location';
import {Router} from '@angular/router';
import {AlertService} from '../../services/alert.service';

@Component({
  selector: 'app-location-add',
  templateUrl: './location-add.component.html',
  styleUrls: ['./location-add.component.scss']
})
export class LocationAddComponent implements OnInit, OnDestroy {

  private location: Location;

  constructor(private locationService: LocationService, private router: Router, private alertService: AlertService) { }

  ngOnInit() {
    if (this.locationService.getLocationToAdd() === null) {
      this.location = new Location(null, null, null, null, null, null);
      this.router.navigateByUrl('/locations');
    } else {
      this.location = this.locationService.getLocationToAdd();
    }
  }

  ngOnDestroy() {
    this.locationService.setLocationToAdd(null);
  }

  saveLocation(): void {
    if (this.validLocation()) {
      this.locationService.createLocation(this.location).subscribe(
        result => {
          this.router.navigateByUrl('/locations/' + result.id);
        }, error1 => {
          this.alertService.error('Ort konnte nicht gespeichert werden, bitte versuchen Sie es etwas später noch ein mal');
        }
      );
    }
  }

  validLocation(): boolean {
    if (
      this.location.name === null || this.location.name === '' ||
      this.location.street === null || this.location.street === '' ||
      this.location.place === null || this.location.place === '' ||
      this.location.country === null || this.location.country === '' ||
      this.location.postalCode === null || this.location.postalCode === ''
    ) {
      this.alertService.warning('Alle Felder müssen ausgefüllt sein!');
      return false;
    }
    return true;
  }
}
