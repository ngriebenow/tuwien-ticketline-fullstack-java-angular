import {Injectable} from '@angular/core';
import {Router, NavigationStart} from '@angular/router';
import {Observable, Subject} from 'rxjs';

import {Alert, AlertType} from '../dtos/alert';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  private subject = new Subject<Alert>();

  constructor(private router: Router) {
    router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.clear();
      }
    });
  }

  getAlert(): Observable<Alert> {
    return this.subject.asObservable();
  }

  success(message: string) {
    this.sendAlert(new Alert(message, AlertType.Success));
  }

  info(message: string) {
    this.sendAlert(new Alert(message, AlertType.Info));
  }

  warning(message: string) {
    this.sendAlert(new Alert(message, AlertType.Warning));
  }

  error(message: string) {
    this.sendAlert(new Alert(message, AlertType.Error));
  }

  private sendAlert(alert: Alert) {
    this.subject.next(alert);
  }

  private clear() {
    this.subject.next(new Alert(''));
  }
}
