import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Hall} from '../dtos/hall';
import {HallRequest} from '../dtos/hall-request';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Unit} from '../dtos/unit';

@Injectable({
  providedIn: 'root'
})
export class HallService {

  private hallBaseUri: string = this.globals.backendUri + '/halls';

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  async getHallById(id: number): Promise<Observable<Hall>> {
    console.log('Load hall with id ' + id);
    return await this.httpClient.get<Hall>(this.hallBaseUri + '/' + id);
  }

  postHall(hallRequest: HallRequest): Observable<Hall> {
    console.log('Save hall');
    return this.httpClient.post<Hall>(this.hallBaseUri, hallRequest);
  }

  putHall(hallRequest: HallRequest): Observable<Hall> {
    console.log('Update hall with id ' + hallRequest.id);
    return this.httpClient.put<Hall>(this.hallBaseUri, hallRequest);
  }

  getUnitsByHallId(id: number): Observable<Unit[]> {
    console.log('Load units of hall with id ' + id);
    return this.httpClient.get<Unit[]>(this.hallBaseUri + '/' + id + '/units');
  }
}
