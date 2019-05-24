import {Point} from './Point';
import {Location} from './location';
import {Unit} from './unit';

export class HallRequest {
  constructor(
    public id: number,
    public version: number,
    public name: string,
    public location: Location,
    public boundaryPoint: Point,
    public units: Unit[]
  ) {
  }
}
