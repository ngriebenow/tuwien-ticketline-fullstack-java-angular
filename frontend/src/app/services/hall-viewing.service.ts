import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {DefinedUnit} from '../dtos/defined-unit';
import {Event} from '../dtos/event';
import {Performance} from '../dtos/performance';
import {PriceCategory} from '../dtos/price-category';
import {Hall} from '../dtos/hall';
import {Unit} from '../dtos/unit';
import {Point} from '../dtos/Point';


@Injectable({
  providedIn: 'root'
})
export class HallViewingService {

  performanceName: String =  'Perofmance';
  eventName: String = 'Event';


  constructor() {
  }

  getHallSize(): Point {
    //TODO for real!
    return new Point(10,10);
  }

  points: Point[] = [];

  getSeats(): Point[]{
    //TODO for real!
    this.points[0] = new Point(1,1);
    this.points[1] = new Point(2,1);
    this.points[2] = new Point(4,1);
    this.points[3] = new Point(5,1);
    this.points[4] = new Point(6,1);
    this.points[5] = new Point(7,1);
    this.points[6] = new Point(9,1);
    this.points[7] = new Point(10,1);

    this.points[8] = new Point(1,2);
    this.points[9] = new Point(2,2);
    this.points[10] = new Point(4,2);
    this.points[11] = new Point(5,2);
    this.points[12] = new Point(6,2);
    this.points[13] = new Point(7,2);
    this.points[14] = new Point(9,2);
    this.points[15] = new Point(10,2);

    return this.points;
  }

  getUnits(): DefinedUnit[]{
    //TODO fro real!
    return new DefinedUnit[60];
  }

  clickSeat(seat: Point): void{

  }

  clickUnit(unit: DefinedUnit): void{

  }

  getEventName(): String{
    return this.eventName;
  }

  getPerformanceName(): String{
    return this.performanceName;
  }

  cats: number[] = [];
  getCats(): number[]{
    this.cats[0] = 10.90;
    this.cats[1] = 11.30;
    this.cats[2] = 12.50;
    return this.cats;
  }
}
