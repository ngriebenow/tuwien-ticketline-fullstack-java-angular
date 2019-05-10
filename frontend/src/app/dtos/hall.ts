import {Point} from './Point';

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
