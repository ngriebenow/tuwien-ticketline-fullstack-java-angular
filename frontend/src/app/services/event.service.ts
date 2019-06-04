import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {EventFilter} from '../dtos/event-filter';
import {EventSearchResult} from '../dtos/event-search-result';
import {Event} from '../dtos/event';
import {Performance} from '../dtos/performance';
import {EventRanking} from '../dtos/event-ranking';
import set = Reflect.set;

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private eventBaseUri: string = this.globals.backendUri + '/events';
  private besteventsUri = '/best';
  private eventCategories = '/categories';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Loads specific performances by event id from the backend
   * @param id of event to load
   * @param queryParams the query params for the backend request
   */
  getPerformancesById(id: number, queryParams: {} = {}): Observable<Performance[]> {
    let paramsHttp = new HttpParams();

    Object.keys(queryParams).forEach(key => paramsHttp = paramsHttp.set(key, queryParams[key]));

    console.log('getPerformancesById: ' + paramsHttp);

    return this.httpClient.get<Performance[]>(this.eventBaseUri + '/' + id + '/performances', {params: paramsHttp});
  }

  /**
   * Loads specific event from the backend
   * @param id of event to load
   */
  getEventById(id: number): Observable<Event> {
    console.log('Load event for ' + id);
    return this.httpClient.get<Event>(this.eventBaseUri + '/' + id);
  }

  /**
   * Loads the best events from the backend
   * @param eventFilter which the events must fulfill
   */
  getBestEvents(eventFilter: EventFilter): Observable<EventRanking[]> {
    const paramsHttp = new HttpParams()
      .set('category', eventFilter.eventCategory)
      .set('limit', '10');


    console.log('getBestEvents: ' + paramsHttp);

    return this.httpClient.get<EventRanking[]>(this.eventBaseUri + this.besteventsUri, {params: paramsHttp});

  }

  /**
   * Loads all the event categories
   */
  getEventCategories(): Observable<String[]> {
    return this.httpClient.get<string[]>(this.eventBaseUri + this.eventCategories);
  }

  /**
   * Loads events from the backend
   * @param eventFilter which the events must fulfil
   * @param queryParams the query params for the backend request
   */
  getEventsFiltered(eventFilter: EventFilter, queryParams: {}): Observable<EventSearchResult[]> {

    console.log('getEventsFiltered');

    let price = '';
    if (eventFilter.priceInEuro !== null) {
      price = eventFilter.priceInEuro + '00';
    }

    let time = '';
    let date = '';
    if (eventFilter.startAtDate !== null) {
      date = eventFilter.startAtDate;

    }
    if (eventFilter.startAtTime !== null || true) {
      time = eventFilter.startAtTime;
    }

    let duration = '';
    if (eventFilter.duration !== null && eventFilter.duration !== 'null') {
      duration = eventFilter.duration;
    }

    let paramsHttp = new HttpParams()
      .set('name', eventFilter.name)
      .set('content', eventFilter.content)
      .set('duration', duration)
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
      .set('startAtTime', time);


    Object.keys(queryParams).forEach(key => paramsHttp = paramsHttp.set(key, queryParams[key]));


    console.log('getEventsFiltered: ' + paramsHttp);

    return this.httpClient.get<EventSearchResult[]>(this.eventBaseUri, {params: paramsHttp});

  }


}
