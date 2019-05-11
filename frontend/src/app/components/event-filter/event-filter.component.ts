import {Component, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-event-filter',
  templateUrl: './event-filter.component.html',
  styleUrls: ['./event-filter.component.scss']
})
export class EventFilterComponent implements OnInit {

  constructor() { }

  @Output() events: Event[] = [];

  ngOnInit() {
    this.loadEvents();
  }

  /**
   * Loads the events
   * @param id the id of the event whose performances should be search for
   */
  loadEvents() {
    this.eventService.getPerformancesById(id).subscribe(
      performances => this.performances = performances as Performance[]);
  }

}
