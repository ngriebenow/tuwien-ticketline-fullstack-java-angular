import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {EventFilter} from '../dtos/event-filter';
import {EventSearchResult} from "../dtos/event-search-result";

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private eventBaseUri: string = this.globals.backendUri + '/events';

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

  getEventsFiltered(eventFilter: EventFilter): Observable<EventSearchResult[]> {

    console.log('getEventsFiltered');

    var price: string = "";
    if (eventFilter.priceInEuro.length > 0) {
      price = eventFilter.priceInEuro + '00';
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
      .set('page', '0')
      .set('count', '100');

    console.log('getEventsFiltered: ' + eventFilter.name);

    return this.httpClient.get<EventSearchResult[]>(this.eventBaseUri, {params: paramsHttp});

  }


}
