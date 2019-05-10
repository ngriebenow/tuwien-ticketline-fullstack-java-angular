import {Artist} from './artist';
import {Hall} from './hall';

export class Event {
  constructor(
    public id: number,
    public name: string,
    public category: string,
    public content: string,
    public duration: string,
    public hall: Hall,
    public artists: Artist[]
    ) {
  }
}
