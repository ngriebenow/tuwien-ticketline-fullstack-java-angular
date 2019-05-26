export class News {
  constructor(
    public id: number,
    public title: string,
    public summary: string,
    public text: string,
    public publishedAt: string,
    public pictureIds: number[],
    public read: boolean) {
  }
}