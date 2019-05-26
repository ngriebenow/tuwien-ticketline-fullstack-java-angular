import {Component, NgModule, OnInit, Output} from '@angular/core';
import {EventService} from '../../services/event.service';
import {ActivatedRoute, RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';
import {AlertService} from '../../services/alert.service';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.scss']
})
@NgModule({
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    RouterTestingModule
  ]
})
export class EventComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private eventService: EventService,
    private alertService: AlertService) {}

  @Output() event: Event;
  @Output() performances: Performance[] = [];

  page = 0;
  count = 20;
  private queryParams = {}

  ngOnInit() {
    this.getEvent();
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

  nextPage(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.page++;
    this.loadPerformances(id);
  }

  previousPage(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    if (this.page > 0) {
      this.page--;
      this.loadPerformances(id);
    }
  }

  /**
   * Get the event by id including its performances
   * @param id the id of the event
   */
  getEvent(): void {
    console.log('getEvent');
    const id = +this.route.snapshot.paramMap.get('id');
    this.loadEvent(id);
    this.loadPerformances(id);
  }

  /**
   * Loads the event by id
   * @param id the id of the event
   */
  loadEvent(id: number) {
    console.log('loadEvent');
    this.eventService.getEventById(id).subscribe(
      event => this.event = event);
  }

  /**
   * Loads the performances of a given event id
   * @param id the id of the event whose performances should be loaded
   */
  loadPerformances(id: number) {

    this.queryParams['page'] = this.page;
    this.queryParams['count'] = this.count;

    console.log('loadPerformances with page ' + this.page + ' and count ' + this.count);


    this.eventService.getPerformancesById(id, this.queryParams).subscribe(
      performances => {
        this.performances = performances as Performance[];
      },
      error => {
        this.alertService.error('Ladefehler, bitte versuchen Sie es etwas später noch ein mal');
      }
    );

  }

}
