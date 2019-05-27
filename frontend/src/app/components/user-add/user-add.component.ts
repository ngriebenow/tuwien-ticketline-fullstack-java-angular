import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {User} from '../../dtos/user';
import {UserService} from '../../services/user.service';
import {FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.scss']
})
export class UserAddComponent implements OnInit {

  public queryParams: User;

  public searchForm = this.formBuilder.group({
    username: [''],
    password: [''],
    admin: [''],
    enabled: ['']
  });

  constructor(private router: Router, private userService: UserService, private formBuilder: FormBuilder) {

  }

  ngOnInit() {
    this.queryParams = new User('', '', 0, 'false', 'false');
  }


  /**
   * Sends user creation request
   */
  createUser() {
    this.queryParams.failedLoginCounter = 0;
    console.log(this.queryParams);
    this.userService.createUser(this.queryParams).subscribe(
      () => {
        this.router.navigate(['/user-filter']);
      }
    );
  }

  public setLocked(inp: string): void {
    console.log('setLocked');
    this.queryParams.enabled = inp;
  }

  public setAdmin(inp: string): void {
    console.log('setAdmin');
    this.queryParams.admin = inp;
  }

}
