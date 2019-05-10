import {Artist} from './artist';
import {Hall} from './hall';
import {PriceCategory} from './price-category';

export class Event {
  constructor(
    public id: number,
    public name: string,
    public category: string,
    public content: string,
    public duration: string,
    public hall: Hall,
    public artists: Artist[],
    public priceCategories: PriceCategory[]
    ) {
  }
}
