import {Component, Input, OnInit, Output} from '@angular/core';
import {EventFilter} from "../../dtos/event-filter";
import {EventRanking} from "../../dtos/event-ranking";
import {EventService} from "../../services/event.service";

@Component({
  selector: 'app-best-events',
  templateUrl: './best-events.component.html',
  styleUrls: ['./best-events.component.scss']
})
export class BestEventsComponent implements OnInit {

  constructor(private eventService: EventService) {

  }

  @Input() eventFilter: EventFilter = new EventFilter( '', '', '', null, '', '', '', '', '', '', '', '', '', '', '');

  @Output() eventRankings: EventRanking[] = [];

  getColor(cat: boolean): string {
    if (cat) {
      return "#EAEAEA";
    } else {
      return '#8FBEFF';
    }
  }

  getTextColor(cat: boolean): string {
    if (cat) {
      return "#CFCFCF";
    } else {
      return '#FFFFFF';
    }
  }

  getGraphColor(first: boolean): string {
    if (first) {
      return "#FF9824";
    } else {
      return '#CFCFCF';
    }
  }

  calcVerticalOffsetMark(index: number): string {
    let width = document.getElementById('graph').offsetWidth;
    let maxwidth = width * 0.8;
    let indexwidth = maxwidth*index/3. + 30;
    return indexwidth.toString() + "px";


  }

  getMark(index: number): number {
    let maxtickets = this.eventRankings[0].soldTickets;
    let marking = maxtickets * index / 4;
    return marking;
  }


  /**
   * Loads the event rankings
   */
  loadEventRankings(): void {
    console.log('loadEventRankings');
    this.eventService.getBestEvents(this.eventFilter).subscribe(
      (events: EventRanking[]) => this.eventRankings = events);
  }



  calcVerticalOffset(index: number): string {
    let offset: number = index * 53 + 38;
    return offset.toString() + "px";
  }

  calcBarWidth(ranking: EventRanking): string {
    let width = document.getElementById('graph').offsetWidth;
    let maxwidth = width/this.eventRankings[0].soldTickets * 0.9;
    let barwidth = maxwidth*ranking.soldTickets;
    return barwidth.toString() + "px";
  }


  ngOnInit() {
    this.loadEventRankings();
  }

}
