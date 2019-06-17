import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Location} from '../dtos/location';
import {Hall} from '../dtos/hall';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  private locationBaseUri: string = this.globals.backendUri + '/locations';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  getLocationById(id: Number): Observable<Location> {
    console.log('Load location with id ' + id);
    return this.httpClient.get<Location>(this.locationBaseUri + '/' + id);
  }

  getFiltered(queryParams: {} = {}): Observable<Location[]> {
    let params = new HttpParams();
    Object.keys(queryParams).forEach(key => params = params.set(key, queryParams[key]));
    console.log('get locations filtered ' + params);
    return this.httpClient.get<Location[]>(this.locationBaseUri, {params});
  }

  getHallsByLocationId(id: number): Observable<Hall[]> {
    console.log('Load halls of location with id ' + id);
    return this.httpClient.get<Hall[]>(this.locationBaseUri + '/' + id + '/halls');
  }
}
