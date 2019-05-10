import {Point} from './Point';

export class Unit {
  constructor(
    public id: number,
    public hallId: number,
    public name: string,
    public lowerBoundary: Point,
    public upperBoundary: Point,
    public capacity: number
  ) {
  }
}
