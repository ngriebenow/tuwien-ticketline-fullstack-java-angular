export class UserFilter {
  constructor(
    public username: string,
    public role: string,
    public locked: string,
    public page: number,
    public count: number
    ) {
  }
}
