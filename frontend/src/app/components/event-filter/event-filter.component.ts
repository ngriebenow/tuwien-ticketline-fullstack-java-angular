import {Component, Input, NgModule, OnInit, Output} from '@angular/core';
import {EventFilter} from '../../dtos/event-filter';
import {EventService} from '../../services/event.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {EventSearchResult} from '../../dtos/event-search-result';
import {Performance} from '../../dtos/performance';
import {IMyDateModel, IMyDpOptions} from 'mydatepicker';
import {AlertService} from '../../services/alert.service';
import {PerformanceSearchResult} from '../../dtos/performance-search-result';
import {Router} from '@angular/router';
import {CustomDatePipe} from '../../pipes/custom-date.pipe';
import {CommonModule, DatePipe} from '@angular/common';

@Component({
  selector: 'app-event-filter',
  templateUrl: './event-filter.component.html',
  styleUrls: ['./event-filter.component.scss']
})
@NgModule({
  declarations: [
    CustomDatePipe
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CustomDatePipe
  ],
  exports: [
    CustomDatePipe
  ]
})
export class EventFilterComponent implements OnInit {


  constructor(private router: Router,
              private eventService: EventService,
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
      return '#CFCFCF';
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

  getLocalDate(dateString: string) {

    let date =  new Date(dateString + "Z");

    console.log(date);

    return new DatePipe('en-US').transform(date, 'dd.MM.yyyy HH:mm');

    //let pipe = new CustomDatePipe();

    //return pipe.transform(dateString,'fromUTC');

  }


  showHall(esr: EventSearchResult, p: PerformanceSearchResult) {

    const per: Performance = new Performance(p.id, p.startAt, p.name, null);

    localStorage.setItem('performance', JSON.stringify(per));

    this.eventService.getEventById(esr.id).subscribe(
      e => localStorage.setItem('event', JSON.stringify(e))
    ).add( () => this.eventLoaded(esr.id, p.id));


  }

  eventLoaded(eid: number, pid: number) {
    this.router.navigate(['/events', eid, 'performances', pid, 'hall-viewing']);
  }

  CheckNumberField(elid: string): void {

    const price: string = (<HTMLInputElement>document.getElementById(elid)).value.toString();

    if (price === '' || price.match(/^\d+$/) !== null) {
      this.loadEvents();
    } else {
      (<HTMLInputElement>document.getElementById(elid)).value = '';
      this.alertService.warning('Es sind nur Zahlen für dieses Feld erlaubt.');
    }

  }


  /**
   * Loads the events
   */
  loadEvents(): void {


    this.queryParams['page'] = this.page;
    this.queryParams['count'] = this.count;

    console.log('loadEvents with page ' + this.page + ' and count ' + this.count);

    this.eventService.getEventsFiltered(this.eventFilter, this.queryParams).subscribe(
      (events: EventSearchResult[]) => {
        this.eventSearchResults = events;
      },
          error => {
          this.alertService.error('Ladefehler, bitte versuchen Sie es etwas später noch ein mal');
      }
    );
  }


}
