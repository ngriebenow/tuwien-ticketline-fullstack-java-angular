export class User {
  constructor(
    public username: string,
    public password: string,
    public failedLoginCounter: number,
    public enabled: string,
    public admin: string,
  ) {
  }
}
