export class Alert {

  alertType: AlertType;
  message: string;

  constructor(message: string, alertType?: AlertType) {
    this.message = message;
    this.alertType = alertType;
  }
}

export enum AlertType {
  Success,
  Error,
  Info,
  Warning
}
