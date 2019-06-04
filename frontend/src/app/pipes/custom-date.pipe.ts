import { Pipe, PipeTransform } from '@angular/core';
import { DatePipe } from '@angular/common';

@Pipe({ name: 'CustomDatePipe' })
export class CustomDatePipe implements PipeTransform {

  transform(date: Date | string, zone: string): string {
    console.log("raw is " + date);
    date = new Date(date+"Z");
    let zoneFormat = '';
    if (zone === 'toUTC') {
      zoneFormat = '0000';
    } else if (zone === 'fromUTC') {

      console.log("date is " + date);
      console.log("off is  " + new Date().getTimezoneOffset());

      date = new Date(date.getTime() + (1000*60*(new Date().getTimezoneOffset())));

    }
    //console.log("date is " + date);
    return new DatePipe('en-US').transform(date, 'dd.MM.yyyy HH:mm');
  }
}
