import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {debounceTime, distinctUntilChanged} from 'rxjs/operators';
import * as _ from 'lodash';
import {UserService} from '../../services/user.service';
import {User} from '../../dtos/user';
import {UserFilter} from '../../dtos/user-filter';

@Component({
  selector: 'app-user-filter',
  templateUrl: './user-filter.component.html',
  styleUrls: ['./user-filter.component.scss']
})
export class UserFilterComponent implements OnInit {

  private users: User[];
  private page = 0;
  private count = 20;
  private queryParams: UserFilter;
  private isEnabled = null;

  private searchForm = this.formBuilder.group({
    userName: [''],
    userRole: [''],
    isEnabled: ['']
  });

  constructor(private userService: UserService, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.loadUsers();
    this.activateOnFormChange();
  }

  private loadUsers(): void {
    this.queryParams['page'] = this.page;
    this.queryParams['count'] = this.count;
    this.userService.getUsersFiltered(this.queryParams).subscribe(
      (user: User[]) => {
        this.users = user;
      },
      error => {
        // TODO: error handling
      }
    );
  }

  private activateOnFormChange(): void {
    this.searchForm.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged(),
    ).subscribe(values => {
      console.log(values);
      this.queryParams = new UserFilter(null, null, null);
      Object.entries<any>(values)
        .filter(entry => entry[1] !== null)
        .map(entry => [entry[0], entry[1].toString().trim()])
        .filter(entry => !_.isEmpty(entry[1]))
        .forEach(entry => {
          const [key, val] = entry;
          if (key === 'userName') {
            this.queryParams.username = val;
          }
          if (key === 'userRole') {
            this.queryParams.role = val;
          }
          if (key === 'isEnabled') {
            if (val === 'true') {
              this.queryParams.locked = false;
            }
            if (val === 'false') {
              this.queryParams.locked = true;
            }
          }
        });
      this.queryParams.locked = !this.isEnabled;
      this.loadUsers();
    });
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
    this.setIsEnabledToNull();
    this.searchForm.reset({}, {emitEvent: true});
  }

  private setIsEnabledToTrue(): void {
    this.isEnabled = true;
  }

  private setIsEnabledToFalse(): void {
    this.isEnabled = false;
  }

  private setIsEnabledToNull(): void {
    this.isEnabled = null;
  }


}
