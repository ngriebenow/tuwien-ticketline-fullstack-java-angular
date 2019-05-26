import {Component, NgModule, OnInit, Output} from '@angular/core';
import {EventService} from '../../services/event.service';
import {ActivatedRoute, RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';
import {TicketingService} from '../../services/ticketing.service';
import {Event} from '../../dtos/event';
import {Performance} from '../../dtos/performance';
//import {TicketingLocalStorageService} from '../../services/ticketing-local-storage.service';

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
    private ticketingService: TicketingService,
    ) {}
    //private ticketinLocalStorage: TicketingLocalStorageService

  @Output() event: Event;
  @Output() performances: Performance[] = [];


  ngOnInit() {
    this.getEvent();
    /*const newToDo = 'newtodo';
    this.ticketinLocalStorage.storeOnLocalStorage(newToDo);
    */
  }

  savePerformance(id: number) {
    console.log("savePerformance with first price category " + this.event.priceCategories[0].priceInCents);

    localStorage.setItem('event', JSON.stringify(this.event));

    //this.ticketingService.setEvent(this.event);
    console.log("details: " + id);
    for (let i = 0; i < this.performances.length; i++) {
      if(this.performances[i].id == id){
        localStorage.setItem('performance', JSON.stringify(this.performances[i]));
        //this.ticketingService.setPerformance(this.performances[i]);
      }
    }
  }


  getEvent(): void {
    console.log('getEvent');
    const id = +this.route.snapshot.paramMap.get('id');
    this.loadEvent(id);
    this.loadPerformances(id);
  }

  /**
   * Loads the text of message and update the existing array of message
   * @param id the id of the message which details should be loaded
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
    this.eventService.getPerformancesById(id).subscribe(
      performances => this.performances = performances as Performance[]);
  }


}
