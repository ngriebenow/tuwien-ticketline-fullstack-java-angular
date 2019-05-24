import {Component, Input, OnInit, Output} from '@angular/core';
import {EventFilter} from "../../dtos/event-filter";
import {EventSearchResult} from "../../dtos/event-search-result";
import {EventRanking} from "../../dtos/event-ranking";
import {EventService} from "../../services/event.service";

@Component({
  selector: 'app-best-events',
  templateUrl: './best-events.component.html',
  styleUrls: ['./best-events.component.scss']
})
export class BestEventsComponent implements OnInit {

  constructor(private eventService: EventService) { }

  @Input() eventFilter: EventFilter = new EventFilter( '', '', '', null, '', '', '', '', '', '', '', '', '', '', '');

  @Output() eventRankings: EventRanking[] = [];

  getColor(cat: boolean): string {
    if (cat) {
      return "#EAEAEA";
    } else {
      return '#8FBEFF';
    }
  }


  /**
   * Loads the event rankings
   */
  loadEventRankings(): void {
    console.log('loadEventRankings');
    this.eventService.getBestEvents(this.eventFilter).subscribe(
      (events: EventRanking[]) => this.eventRankings = events);
  }


  ngOnInit() {
  }

}
