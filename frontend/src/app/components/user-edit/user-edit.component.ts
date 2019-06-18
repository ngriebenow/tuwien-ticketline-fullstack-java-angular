import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../../dtos/user';
import {UserService} from '../../services/user.service';
import {FormBuilder} from '@angular/forms';
import {ConfirmationDialogService} from '../../services/confirmation-dialog.service';
import {AlertService} from '../../services/alert.service';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss']
})
export class UserEditComponent implements OnInit {

  public queryParams: User;
  public admin: String;

  public searchForm = this.formBuilder.group({
    username: [''],
    password: [''],
    admin: [''],
    enabled: ['']
  });

  constructor(private router: Router,
              private userService: UserService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private confirm: ConfirmationDialogService,
              private alertService: AlertService) {
    this.queryParams = new User('', '', 0, '', '');
  }

  ngOnInit() {
    this.userService.getUserById(this.route.snapshot.paramMap.get('user')).subscribe(
      (user: User) => {
        this.queryParams = user;
        this.admin = user.admin;
        console.log(user);
      },
      error => {
        this.alertService.warning('Nutzer konnte nicht geladen werden!');
      }
    );
  }

  maybeUpdate() {
    if (this.admin === 'false' && this.queryParams.admin === 'true') {
      const onYes = () => {
        this.updateUser();
      };
      const onNo = () => {
        this.setAdmin('false');
      };
      this.confirm.open('User wirklich zu Admin ändern?', onYes, onNo);
    } else {
      this.updateUser();
    }
  }

  /**
   * Sends user creation request
   */
  updateUser() {
    this.queryParams.failedLoginCounter = 0;
    console.log(this.queryParams);
    this.userService.updateUser(this.queryParams).subscribe(
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

  public isAdmin() {
    return this.admin === 'true';
  }
}
