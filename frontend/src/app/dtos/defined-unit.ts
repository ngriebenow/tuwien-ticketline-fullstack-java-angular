import {Point} from './Point';

export class DefinedUnit {
  constructor(
    public id: number,
    public name: string,
    public free: number,
    public capacity: number,
    public lowerBoundary: Point,
    public upperBoundary: Point,
    public priceCategoryId: number,
    public selected: boolean = false,
    public num: number = 0,
  ) {
  }
}
