import {Component, OnInit, Output} from '@angular/core';
import {EventService} from '../../services/event.service';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.scss']
})
export class EventComponent implements OnInit {

  constructor(private eventService: EventService) {}

  @Output() event: Event;

  getEvent(): Event {
    return this.event;
  }

  ngOnInit() {
    this.loadEvent(3);
  }


  /**
   * Loads the text of message and update the existing array of message
   * @param id the id of the message which details should be loaded
   */
  loadEvent(id: number) {
    this.eventService.getEventById(id).subscribe(
      event => this.event = event);
  }

}
