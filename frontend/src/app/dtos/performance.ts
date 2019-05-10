import {Event} from './event';

export class Performance {
  constructor(
    public id: number,
    public startAt: string,
    public event: Event,
    public name: string
  ) {
  }
}
