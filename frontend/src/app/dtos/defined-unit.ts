import {Point} from './Point';

export class DefinedUnit {
  constructor(
    public id: number,
    public free: number,
    public point1: Point,
    public point2: Point,
    public priceCategory: number
  ) {
  }
}
