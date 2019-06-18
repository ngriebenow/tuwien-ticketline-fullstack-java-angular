import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {User} from '../../dtos/user';
import {UserService} from '../../services/user.service';
import {FormBuilder} from '@angular/forms';
import {AlertService} from '../../services/alert.service';

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

  constructor(private router: Router,
              private userService: UserService,
              private formBuilder: FormBuilder,
              private alertService: AlertService) {

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
    if (this.queryParams.username === '') {
      this.alertService.warning('Es muss ein Nutzername festgelegt werden!');
    } else if (this.queryParams.password === '') {
      this.alertService.warning('Es muss ein Passwort festgelegt werden!');
    } else {
      this.userService.createUser(this.queryParams).subscribe(
        () => {
          this.router.navigate(['/user-filter']);
        },
        error => {
          this.alertService.warning('Nutzername ist schon vorhanden - wählen Sie einen Anderen!');
        }
      );
    }
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
