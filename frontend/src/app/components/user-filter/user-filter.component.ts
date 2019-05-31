import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {User} from '../../dtos/user';
import {UserFilter} from '../../dtos/user-filter';
import {Router} from '@angular/router';

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
  public admin: string;

  public searchForm = this.formBuilder.group({
    userName: [''],
    userRole: [''],
    isEnabled: [''],
    isAdmin: ['']
  });

  constructor(private userService: UserService, private formBuilder: FormBuilder, private router: Router) {
    this.queryParams = new UserFilter('', '', '', 0, 20);
    this.isLocked = '';
    this.admin = '';
  }

  ngOnInit() {
    this.loadUsers();
  }

  public loadUsers(): void {
    this.queryParams.page = this.page;
    this.queryParams.count = this.count;
    this.userService.getUsersFiltered(this.queryParams).subscribe(
      (user: User[]) => {
        this.users = user;
        user.forEach(function (value) {
          console.log(value);
        });
      },
      error => {
        // TODO: error handling
      }
    );
  }

  public nextPage(): void {
    this.page++;
    this.loadUsers();
  }

  public previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadUsers();
    }
  }

  public setIsLocked(inp: string): void {
    this.isLocked = inp;
  }

  public setAdmin(inp: string): void {
    this.admin = inp;
  }

  public addUser(): void {
    this.router.navigate(['/user-add']);
  }


}
