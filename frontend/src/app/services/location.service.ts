import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  private locationBaseUri: string = this.globals.backendUri + '/locations';

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  getFiltered(queryParams: {} = {}): Observable<Location[]> {
    let params = new HttpParams();
    Object.keys(queryParams).forEach(key => params = params.set(key, queryParams[key]));
    return this.httpClient.get<Location[]>(this.locationBaseUri, {params});
  }
}
