import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Location} from '../dtos/location';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  private locationBaseUri: string = this.globals.backendUri + '/locations';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  getFiltered(queryParams: {} = {}): Observable<Location[]> {
    let params = new HttpParams();
    Object.keys(queryParams).forEach(key => params = params.set(key, queryParams[key]));
    console.log('get locations filtered ' + params);
    return this.httpClient.get<Location[]>(this.locationBaseUri, {params});
  }
}
