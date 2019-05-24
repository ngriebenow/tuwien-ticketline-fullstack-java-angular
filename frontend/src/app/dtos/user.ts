export class User {
  constructor(
    public username: number,
    public password: number,
    public isLocked: boolean,
    public isAdmin: boolean
  ) {
  }
}
