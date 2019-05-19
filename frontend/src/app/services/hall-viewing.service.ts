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

  performanceName: String =  'Performance';
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

  cats: PriceCategory[] = [];
  getCats(): PriceCategory[]{
    this.cats[0] = new PriceCategory(1, 10.90, 'Kategorie 1',0x0000FF);
    this.cats[1] = new PriceCategory(2, 11.20, 'Kategorie 2', 0x008000);
    this.cats[2] = new PriceCategory(3, 12.50, 'Kategorie 3', 0xFF00FF);
    return this.cats;
  }

  defUnits: DefinedUnit[] = []
  getDefUnits(): DefinedUnit[]{
    this.defUnits[0] = new DefinedUnit(1, 'something', 1, 1, new Point(1,1), new Point(1,1), 1);
    this.defUnits[1] = new DefinedUnit(2, 'something',1, 1, new Point(2,1), new Point(2,1), 1);
    this.defUnits[2] = new DefinedUnit(3, 'something',1, 1, new Point(4,1), new Point(4,1), 1);
    this.defUnits[3] = new DefinedUnit(4, 'something',1, 1, new Point(5,1), new Point(5,1), 1);
    this.defUnits[4] = new DefinedUnit(5, 'something',0, 1, new Point(6,1), new Point(6,1), 1);
    this.defUnits[5] = new DefinedUnit(6, 'something',0, 1, new Point(7,1), new Point(7,1), 1);
    this.defUnits[6] = new DefinedUnit(7, 'something',1, 1, new Point(9,1), new Point(9,1), 1);
    this.defUnits[7] = new DefinedUnit(8, 'something',0, 1, new Point(10,1), new Point(10,1), 1);

    this.defUnits[8] = new DefinedUnit(9, 'something',1, 1, new Point(1,2), new Point(1,2), 2);
    this.defUnits[9] = new DefinedUnit(10, 'something',1, 1, new Point(2,2), new Point(2,2), 2);
    this.defUnits[10] = new DefinedUnit(11, 'something',0, 1, new Point(4,2), new Point(4,2), 2);
    this.defUnits[11] = new DefinedUnit(12, 'something',0, 1, new Point(5,2), new Point(5,2), 2);
    this.defUnits[12] = new DefinedUnit(13, 'something',1, 1, new Point(6,2), new Point(6,2), 2);
    this.defUnits[13] = new DefinedUnit(14, 'something',0, 1, new Point(7,2), new Point(7,2), 2);
    this.defUnits[14] = new DefinedUnit(15, 'something',1, 1, new Point(9,2), new Point(9,2), 2);
    this.defUnits[15] = new DefinedUnit(16, 'something',1, 1, new Point(10,2), new Point(10,2), 2);

    this.defUnits[16] = new DefinedUnit(17, 'Sektor A',1, 2, new Point(1,3), new Point(2,3), 3);
    this.defUnits[17] = new DefinedUnit(19, 'something',0, 1, new Point(4,3), new Point(4,3), 3);
    this.defUnits[18] = new DefinedUnit(20, 'something',0, 1, new Point(5,3), new Point(5,3), 3);
    this.defUnits[19] = new DefinedUnit(21, 'something',1, 1, new Point(6,3), new Point(6,3), 3);
    this.defUnits[20] = new DefinedUnit(22, 'something',0, 1, new Point(7,3), new Point(7,3), 3);
    this.defUnits[21] = new DefinedUnit(23, 'something',1, 1, new Point(9,3), new Point(9,3), 3);
    this.defUnits[22] = new DefinedUnit(24, 'something',1, 1, new Point(10,3), new Point(10,3), 3);
    return this.defUnits;
  }

  getHallName(){
    return 'Halle 1';
  }

  getStartAt(){
    return '12:30';
  }

}
