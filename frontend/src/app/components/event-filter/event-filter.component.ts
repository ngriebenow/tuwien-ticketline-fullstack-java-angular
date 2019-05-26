import {Component, Input, NgModule, OnInit, Output} from '@angular/core';
import {EventFilter} from '../../dtos/event-filter';
import {EventService} from '../../services/event.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {EventSearchResult} from '../../dtos/event-search-result';
import {IMyDateModel, IMyDpOptions} from 'mydatepicker';
import {AlertService} from '../../services/alert.service';
import {Event} from '../../dtos/event';
import {Performance} from '../../dtos/performance';
import {analyzeFileForInjectables} from "@angular/compiler";
import {takeWhile} from "rxjs/operators";
import {PerformanceSearchResult} from "../../dtos/performance-search-result";

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


  constructor(private eventService: EventService,
              private alertService: AlertService) {

  }

  page = 0;
  count = 20;
  private queryParams = {};

  myDatePickerOptions: IMyDpOptions = {
    dateFormat: 'dd.mm.yyyy',
  };

  @Input() eventFilter: EventFilter = new EventFilter( '', '', '', null, '', '', '', '', '', '', '', '', '', '', '');

  @Output() eventSearchResults: EventSearchResult[] = [];



  ngOnInit() {
    console.log('ngOnInit');
    this.loadEvents();
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
      return "#CFCFCF";
    } else {
      return '#FFFFFF';
    }
  }

  /**
   * Reloads the event whenever the date has changed
   * @param event: the event
   */
  onDateChanged(event: IMyDateModel) {
    this.eventFilter.startAtDate = event.formatted;
    this.loadEvents();
    // event properties are: event.date, event.jsdate, event.formatted and event.epoc
  }

  nextPage(): void {
    this.page++;
    this.loadEvents();
  }

  previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadEvents();
    }
  }


  showHall(e: EventSearchResult, p: PerformanceSearchResult) {

    let ev: Event;
    let pe: Performance;

    for (let i = 0; i < 50; i++) {
      this.eventService.getEventById(e.id).subscribe(
        x => ev = x);
      this.eventService.getPerformancesById(p.id).subscribe(
        x => pe = x.find(y => y.id === p.id));
      setTimeout(() => {while (true){}}, 100);
      if (ev != null && pe != null) {
        break;
      }
    }
    if (ev == null || pe == null) {
      this.alertService.error("Es ist ein Problem beim Laden aufgetreten. Versuchen Sie es später erneut.");
      return;
    }

    //= this.eventService.getEventById(e.id).to

    localStorage.setItem('event', JSON.stringify(ev));
    localStorage.setItem('performance', JSON.stringify(pe));

  }


  /**
   * Loads the events
   */
  loadEvents(): void {


    this.queryParams['page'] = this.page;
    this.queryParams['count'] = this.count;

    console.log('loadEvents with page ' + this.page + ' and count ' + this.count);

    this.eventService.getEventsFiltered(this.eventFilter,this.queryParams).subscribe(
      (events: EventSearchResult[]) => {
        this.eventSearchResults = events;
      },
          error => {
          this.alertService.error('Ladefehler, bitte versuchen Sie es etwas später noch ein mal');
      }
    );
  }


}
