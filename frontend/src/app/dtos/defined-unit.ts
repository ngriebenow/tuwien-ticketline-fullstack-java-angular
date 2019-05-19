import {Point} from './Point';

export class DefinedUnit {
  constructor(
    public id: number,
    public name: string,
    public free: number,
    public capacity: number,
    public point1: Point,
    public point2: Point,
    public priceCategory: number
  ) {
  }
}
