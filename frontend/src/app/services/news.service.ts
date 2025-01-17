import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {News} from '../dtos/news';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  private newsBaseUri: string = this.globals.backendUri + '/news';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Loads all news from the backend
   * @param onlyNew to specifiy if all or just unread news should be loaded
   */
  getNews(onlyNew: boolean): Observable<News[]> {
    let params = new HttpParams();
    if (onlyNew) {
      params = params.set('onlyNew', onlyNew.toString());
    }

    return this.httpClient.get<News[]>(this.newsBaseUri, {params} );
  }

  /**
   * Loads specific news from the backend
   * @param id of news to load
   */
  getNewsById(id: number): Observable<News> {
    console.log('Load news details for ' + id);
    return this.httpClient.get<News>(this.newsBaseUri + '/' + id);
  }

  /**
   * Persists news to the backend
   * @param news to persist
   */
  createNews(news: News): Observable<News> {
    console.log('Create news with title ' + news.title);
    return this.httpClient.post<News>(this.newsBaseUri, news);
  }
}
