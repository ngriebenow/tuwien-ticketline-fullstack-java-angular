import {Component, NgModule, OnInit, Output} from '@angular/core';
import {EventService} from '../../services/event.service';
import {ActivatedRoute, RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';

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
    private eventService: EventService) {}

  @Output() event: Event;
  @Output() performances: Performance[] = [];


  ngOnInit() {
    this.getEvent();
  }


  getEvent(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.loadEvent(id);
    this.loadPerformances(id);
  }

  /**
   * Loads the text of message and update the existing array of message
   * @param id the id of the message which details should be loaded
   */
  loadEvent(id: number) {
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
