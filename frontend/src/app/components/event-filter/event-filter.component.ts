import {Component, Input, NgModule, OnInit, Output} from '@angular/core';
import {EventFilter} from '../../dtos/event-filter';
import {EventService} from '../../services/event.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {EventSearchResult} from "../../dtos/event-search-result";
import {IMyDateModel, IMyDpOptions} from 'mydatepicker';
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


  constructor(private eventService: EventService) {

  }

  myDatePickerOptions: IMyDpOptions = {
    dateFormat: 'dd-mm-yyyy',
  };

  @Input() eventFilter: EventFilter = new EventFilter( '', '', '', null, '', '', '', '', '', '', '', '', '', '', '');

  @Output() eventSearchResults: EventSearchResult[] = [];

  ngOnInit() {
    console.log('ngOnInit');
    this.loadEvents();
  }


  onDateChanged(event: IMyDateModel) {
    this.eventFilter.startAtDate = event.formatted;
    this.loadEvents();
    // event properties are: event.date, event.jsdate, event.formatted and event.epoc
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
