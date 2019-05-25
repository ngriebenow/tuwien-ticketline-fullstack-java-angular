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

  /**
   * Returns the color for the selctor
   * @param first: true if it is the first option
   */
  getColor(cat: boolean): string {
    if (cat) {
      return "#EAEAEA";
    } else {
      return '#8FBEFF';
    }
  }

  /**
   * Returns the text color for the selctor
   * @param first: true if it is the first option
   */
  getTextColor(cat: boolean): string {
    if (cat) {
      return "#CFCFCF";
    } else {
      return '#FFFFFF';
    }
  }

  /**
   * Returns the color for the graph
   * @param first: true if it is the first option
   */
  getGraphColor(first: boolean): string {
    if (first) {
      return "#FF9824";
    } else {
      return '#CFCFCF';
    }
  }

  /**
   * Calculates the vertical offset for the ticks on the x axis
   * @param index the index-th tick on the x axis
   */
  calcVerticalOffsetMark(index: number): string {
    let width = document.getElementById('graph').offsetWidth;
    let maxwidth = width * 0.8;
    let indexwidth = maxwidth*index/3. + 30;
    return indexwidth.toString() + "px";


  }

  /**
   * Calculates the number of sold tickets which represents the index-th mark
   * @param index the index-th tick on the x axis
   */
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


  /**
   * Calculates the vertical offset for the event bars
   * @param index the index-th ranking starting from the top
   */
  calcVerticalOffset(index: number): string {
    let offset: number = index * 53 + 38;
    return offset.toString() + "px";
  }

  /**
   * Calculates the bar width of one event ranking
   * @param ranking: the event ranking
   */
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
