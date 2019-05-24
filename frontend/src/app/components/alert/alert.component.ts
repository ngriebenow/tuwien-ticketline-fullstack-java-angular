import { Component, OnInit } from '@angular/core';
import {AlertService} from '../../services/alert.service';
import {Alert, AlertType} from '../../dtos/alert';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss']
})
export class AlertComponent implements OnInit {

  private alerts: Alert[] = [];

  constructor(private alertService: AlertService) { }

  ngOnInit() {
    this.alertService.getAlert().subscribe(alert => {
      if (!alert.message) {
        this.alerts = [];
      } else {
        this.alerts.push(alert);
      }
    });
  }

  getCssClass(alert: Alert) {
    switch (alert.alertType) {
      case AlertType.Success:
        return 'success';
      case AlertType.Info:
        return 'info';
      case AlertType.Warning:
        return 'warning';
      case AlertType.Error:
        return 'danger';
    }
  }

  removeAlert(alert: Alert) {
    this.alerts = this.alerts.filter(element => element !== alert);
  }

}
