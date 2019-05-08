import {Artist} from './artist';

export class Event {
  constructor(
    public id: number,
    public name: string,
    public category: string,
    public content: string,
    public duration: string,
    public artists: Artist[]
    ) {
  }
}
