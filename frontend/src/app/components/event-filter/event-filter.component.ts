import {Component, Input, NgModule, OnInit, Output} from '@angular/core';
import {EventFilter} from '../../dtos/event-filter';
import {EventService} from '../../services/event.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-event-filter',
  templateUrl: './event-filter.component.html',
  styleUrls: ['./event-filter.component.scss']
})
@NgModule({
  imports: [
    FormsModule,
    ReactiveFormsModule
  ]
})
export class EventFilterComponent implements OnInit {


  constructor(private eventService: EventService) { }

  @Input() eventFilter: EventFilter = new EventFilter('', '', '', '', '', '', '', '', '', '', '', '', '', '')

  @Output() events: Event[] = [];

  ngOnInit() {
    console.log('ngOnInit');
    this.loadEvents();
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

}
