import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private eventBaseUri: string = this.globals.backendUri + '/events';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  getPerformancesById(id: number): Observable<Performance[]> {
    console.log('Load performances for event ' + id);
    /**TODO pageable*/
    return this.httpClient.get<Performance[]>(this.eventBaseUri + '/' + id + '/performances?page=1&count=1');
  }

  /**
   * Loads specific event from the backend
   * @param id of event to load
   */
  getEventById(id: number): Observable<Event> {
    console.log('Load event for ' + id);
    return this.httpClient.get<Event>(this.eventBaseUri + '/' + id);
  }


}
