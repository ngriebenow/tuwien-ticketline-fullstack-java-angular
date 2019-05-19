import {Point} from './Point';
import {Location} from './location';

export class Hall {
  constructor(
    public id: number,
    public version: number,
    public name: string,
    public location: Location,
    public boundaryPoint: Point
    ) {
  }
}
