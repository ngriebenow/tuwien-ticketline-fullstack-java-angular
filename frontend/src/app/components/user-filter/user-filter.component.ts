import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {User} from '../../dtos/user';
import {UserFilter} from '../../dtos/user-filter';

@Component({
  selector: 'app-user-filter',
  templateUrl: './user-filter.component.html',
  styleUrls: ['./user-filter.component.scss']
})
export class UserFilterComponent implements OnInit {

  public users: User[];
  public page = 0;
  public count = 20;
  public queryParams: UserFilter;
  public isLocked: string;

  public searchForm = this.formBuilder.group({
    userName: [''],
    userRole: [''],
    isEnabled: ['']
  });

  constructor(private userService: UserService, private formBuilder: FormBuilder) {
    this.queryParams = new UserFilter('', '', '', 0, 20);
    this.isLocked = '';
  }

  ngOnInit() {
    this.loadUsers();
  }

  private loadUsers(): void {
    this.queryParams.page = this.page;
    this.queryParams.count = this.count;
    this.userService.getUsersFiltered(this.queryParams).subscribe(
      (user: User[]) => {
        this.users = user;
        this.users.forEach(function (value) {
          console.log(value);
        });
      },
      error => {
        // TODO: error handling
      }
    );
  }

  private nextPage(): void {
    this.page++;
    this.loadUsers();
  }

  private previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadUsers();
    }
  }

  private resetSearchForm(): void {
    this.page = 0;
    this.setIsLockedToNull();
    this.searchForm.reset({}, {emitEvent: true});
  }

  private setIsLockedToTrue(): void {
    this.isLocked = 'true';
  }

  private setIsLockedToFalse(): void {
    this.isLocked = 'false';
  }

  private setIsLockedToNull(): void {
    this.isLocked = '';
  }

  public update(): void {
    this.loadUsers();
  }


}
