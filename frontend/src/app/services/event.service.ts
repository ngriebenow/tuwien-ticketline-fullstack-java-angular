import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {EventFilter} from '../dtos/event-filter';
import {EventSearchResult} from "../dtos/event-search-result";
import {EventRanking} from "../dtos/event-ranking";

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private eventBaseUri: string = this.globals.backendUri + '/events';
  private besteventsUri: string = '/best';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  getPerformancesById(id: number): Observable<Performance[]> {
    console.log('Load performances for event ' + id);
    return this.httpClient.get<Performance[]>(this.eventBaseUri + '/' + id + '/performances');
  }

  /**
   * Loads specific event from the backend
   * @param id of event to load
   */
  getEventById(id: number): Observable<Event> {
    console.log('Load event for ' + id);
    return this.httpClient.get<Event>(this.eventBaseUri + '/' + id);
  }

  getBestEvents(eventFilter: EventFilter): Observable<EventRanking[]> {
    const paramsHttp = new HttpParams()
      .set('eventCategory', eventFilter.eventCategory);


    console.log('getBestEvents: ' + paramsHttp);

    return this.httpClient.get<EventRanking[]>(this.eventBaseUri + this.besteventsUri, {params: paramsHttp});

  }

  getEventsFiltered(eventFilter: EventFilter): Observable<EventSearchResult[]> {

    console.log('getEventsFiltered');

    let price: string = "";
    if (eventFilter.priceInEuro != null) {
      price = eventFilter.priceInEuro + '00';
    }

    let time: string = "";
    let date: string = "";
    if (eventFilter.startAtDate != null) {
      date = eventFilter.startAtDate;

    }
    if (eventFilter.startAtTime != null || true) {
      time = eventFilter.startAtTime;
    }

    const paramsHttp = new HttpParams()
      .set('name', eventFilter.name)
      .set('content', eventFilter.content)
      .set('duration', eventFilter.duration)
      .set('eventCategory', eventFilter.eventCategory)
      .set('artistName', eventFilter.artistName)
      .set('priceInCents', price)
      .set('hallName', eventFilter.hallName)
      .set('hallId', eventFilter.hallId)
      .set('locationId', eventFilter.locationId)
      .set('locationName', eventFilter.locationName)
      .set('locationCountry', eventFilter.locationCountry)
      .set('locationStreet', eventFilter.locationStreet)
      .set('locationPlace', eventFilter.locationPlace)
      .set('startAtDate', date)
      .set('startAtTime', time)
      .set('page', '0')
      .set('count', '100');




    console.log('getEventsFiltered: ' + paramsHttp);

    return this.httpClient.get<EventSearchResult[]>(this.eventBaseUri, {params: paramsHttp});

  }


}
