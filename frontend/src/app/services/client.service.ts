import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Client} from '../dtos/client';
import {ClientFilter} from '../dtos/client-filter';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private userBaseUri: string = this.globals.backendUri + '/clients';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }


  /**
   * Loads specific client from the backend
   * @param id of client to load
   */
  getClientById(id: string): Observable<Client> {
    console.log('Load client for ' + id);
    return this.httpClient.get<Client>(this.userBaseUri + '/' + id);
  }

  getClientsFiltered(clientFilter: ClientFilter): Observable<Client[]> {

    console.log('getClientsFiltered');

    const paramsHttp = new HttpParams()
      .set('email', clientFilter.email)
      .set('name', clientFilter.name)
      .set('surname', clientFilter.surname)
      .set('page', String(clientFilter.page))
      .set('count', String(clientFilter.count));


    console.log('getClientsFiltered: ' + paramsHttp);

    return this.httpClient.get<Client[]>(this.userBaseUri, {params: paramsHttp});

  }

  /**
   * Persists client to the backend
   * @param client to persist
   */
  createClient(client: Client): Observable<Client> {
    console.log('Create client with name ' + client.name + ' ' + client.surname);
    return this.httpClient.post<Client>(this.userBaseUri, client);
  }

  /**
   * Update client in backend
   * @param client to persist
   */
  updateClient(client: Client): Observable<Client> {
    console.log('Update client with name ' + client.name + ' ' + client.surname);
    return this.httpClient.put<Client>(this.userBaseUri, client);
  }

}
