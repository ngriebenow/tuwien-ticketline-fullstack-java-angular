import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {UserFilter} from '../dtos/user-filter';
import {User} from '../dtos/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userBaseUri: string = this.globals.backendUri + '/users';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }


  /**
   * Loads specific user from the backend
   * @param id of user to load
   */
  getUserById(id: string): Observable<User> {
    console.log('Load user for ' + id);
    return this.httpClient.get<User>(this.userBaseUri + '/' + id);
  }

  getUsersFiltered(userFilter: UserFilter): Observable<User[]> {

    console.log('getUsersFiltered');

    const paramsHttp = new HttpParams()
      .set('username', userFilter.username)
      .set('role', userFilter.role)
      .set('locked', String(userFilter.locked))
      .set('page', '0')
      .set('count', '100');


    console.log('getUsersFiltered: ' + paramsHttp);

    return this.httpClient.get<User[]>(this.userBaseUri, {params: paramsHttp});

  }

  /**
   * Persists user to the backend
   * @param user to persist
   */
  createUser(user: User): Observable<User> {
    console.log('Create user with username ' + user.username);
    return this.httpClient.post<User>(this.userBaseUri, user);
  }

  /**
   * Update user in backend
   * @param user to persist
   */
  updateUser(user: User): Observable<User> {
    console.log('Update user with username ' + user.username);
    return this.httpClient.put<User>(this.userBaseUri, user);
  }

}
