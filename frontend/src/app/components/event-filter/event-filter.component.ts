import {Component, Input, NgModule, NO_ERRORS_SCHEMA, OnInit, Output} from '@angular/core';
import {EventFilter} from '../../dtos/event-filter';
import {EventService} from '../../services/event.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Observable} from "rxjs";

@Component({
  selector: 'app-event-filter',
  templateUrl: './event-filter.component.html',
  styleUrls: ['./event-filter.component.scss']
})
@NgModule({
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
})
export class EventFilterComponent implements OnInit {


  constructor(private eventService: EventService) { }



  @Input() eventFilter: EventFilter = new EventFilter('', '', '', '', '', '', '', '', '', '', '', '', '', '');

  @Output() events: Event[] = [];
  @Output() performances: Performance[] = [];

  ngOnInit() {
    console.log('ngOnInit');
    this.loadEvents();
    this.loadPerformances(2);
  }


  /**
   * Loads the events
   * @param id the id of the event whose performances should be search for
   */
  loadEvents(): void {
    console.log('loadEvents');
    this.eventService.getEventsFiltered(this.eventFilter).subscribe(
      (events: Event[]) => this.events = events);
  }

  /**
   * Loads the performances of a given event id
   * @param id the id of the event whose performances should be loaded
   */
  loadPerformances(id: number): Performance[] {
    console.log('loadPerformances');

    let pers: Performance[] = [];





    // return this.eventService.getPerformancesById(id);

    this.eventService.getPerformancesById(id).subscribe(
      performances => pers = performances as Performance[]);

    return pers;
  }

}
