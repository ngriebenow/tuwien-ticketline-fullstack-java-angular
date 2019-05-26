export class User {
  constructor(
    public username: string,
    public password: string,
    public isLocked: boolean,
    public isAdmin: boolean
  ) {
  }
}
