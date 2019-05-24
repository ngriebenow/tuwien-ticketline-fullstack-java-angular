import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Picture} from '../dtos/picture';

@Injectable({
  providedIn: 'root'
})
export class PictureService {

  private newsPicturesBaseUri: string = this.globals.backendUri + '/newspictures';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }


  getPicture(id: number): Observable<Blob> {
    console.log('Load picture with id ' + id);
    return this.httpClient.get(this.newsPicturesBaseUri + '/' + id, { responseType: 'blob' });
  }
}
