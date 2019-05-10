import {Component, OnInit, Output} from '@angular/core';
import {EventService} from '../../services/event.service';
import {Message} from "../../dtos/message";

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.scss']
})
export class EventComponent implements OnInit {

  constructor(private eventService: EventService) {}

  @Output() event: Event;
  @Output() performances: Performance[] = [];


  ngOnInit() {
    this.loadEvent(3);
    this.loadPerformances(3);
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
