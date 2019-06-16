import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Location} from '../../dtos/location';
import {AlertService} from '../../services/alert.service';
import {LocationService} from '../../services/location.service';
import {Hall} from '../../dtos/hall';

@Component({
  selector: 'app-location-detail',
  templateUrl: './location-detail.component.html',
  styleUrls: ['./location-detail.component.scss']
})
export class LocationDetailComponent implements OnInit {

  location: Location;
  halls: Hall[] = [];
  page = 0;
  count = 20;

  constructor(
    private route: ActivatedRoute,
    private locationService: LocationService,
    private alertService: AlertService
  ) {
  }

  ngOnInit() {
    const route = this.route.snapshot;
    this.loadLocation(+route.paramMap.get('id'));
  }

  private loadLocation(id: number): void {
    this.locationService.getLocationById(id).subscribe(
      (location: Location) => {
        this.location = location;
        this.loadHalls();
        console.log('loaded location with name: ' + this.location.name);
      },
      error => {
        this.alertService.error('Ort konnte nicht vollständig geladen werden.');
        console.log(error);
      }
    );
  }

  private loadHalls(): void {
    this.locationService.getHallsByLocationId(this.location.id).subscribe(
      (halls: Hall[]) => {
        this.halls = halls;
      },
      error => {
        this.alertService.error('Ort konnte nicht vollständig geladen werden.');
      }
    );
  }

  private nextPage(): void {
    this.page++;
    this.loadHalls();
  }

  private previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadHalls();
    }
  }


}
