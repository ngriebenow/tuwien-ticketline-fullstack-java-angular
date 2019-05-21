import {Component, Input, NgModule, OnInit, Output} from '@angular/core';
import {EventFilter} from '../../dtos/event-filter';
import {EventService} from '../../services/event.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {EventSearchResult} from "../../dtos/event-search-result";

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



  @Input() eventFilter: EventFilter = new EventFilter( '', '', '', '', '', '', '', '', '', '', '', '', '');

  @Output() eventSearchResults: EventSearchResult[] = [];

  ngOnInit() {
    console.log('ngOnInit');
    this.loadEvents();
  }


  /**
   * Loads the events
   */
  loadEvents(): void {
    console.log('loadEvents');
    this.eventService.getEventsFiltered(this.eventFilter).subscribe(
      (events: EventSearchResult[]) => this.eventSearchResults = events);
  }

}
