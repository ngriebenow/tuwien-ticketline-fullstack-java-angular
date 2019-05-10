import {Event} from './event';

export class EventRanking {
  constructor(
    public rank: number,
    public event: Event,
    public soldTickets: number
  ) {
  }
}
