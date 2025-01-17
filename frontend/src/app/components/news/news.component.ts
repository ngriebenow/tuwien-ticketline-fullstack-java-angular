import {ChangeDetectorRef, Component, NgModule, OnInit} from '@angular/core';
import {NewsService} from '../../services/news.service';
import {News} from '../../dtos/news';
import {NgbPaginationConfig} from '@ng-bootstrap/ng-bootstrap';
import * as _ from 'lodash';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {ActivatedRoute, RouterModule} from '@angular/router';
import {Router} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.scss']
})
export class NewsComponent implements OnInit {

  error = false;
  errorMessage = '';
  newsForm: FormGroup;

  private news: News[];

  constructor(private newsService: NewsService, private ngbPaginationConfig: NgbPaginationConfig, private formBuilder: FormBuilder,
              private cd: ChangeDetectorRef, private authService: AuthService, private route: ActivatedRoute,
              private router: Router) {
    this.newsForm = this.formBuilder.group({
      title: ['', [Validators.required]],
      summary: ['', [Validators.required]],
      text: ['', [Validators.required]],
    });
  }

  ngOnInit() {
    this.loadNews(this.onlyNew());
  }
  /**
   * get if component shows only new news.
   * @return onlyNew.
   */
  onlyNew(): boolean {
    let onlyNew: boolean;
    this.route
    .queryParams
    .subscribe(params => {
      onlyNew = params['onlyNew'];
    });
    return onlyNew;
  }

  /**
   * Returns true if the authenticated user is an admin
   */
  isAdmin(): boolean {
    return this.authService.getUserRole() === 'ADMIN';
  }
  /**
   * Sends news creation request
   */
  addNews() {
    this.router.navigate(['/news-add']);
  }
  getNews(): News[] {
    return this.news;
  }
  /**
   * Loads the text of news and pictureIds and update the existing array of news
   * @param id the id of the news which details should be loaded
   */
  selectNewsDetails(id: number) {
    this.router.navigate(['/news/' + id]);
  }
  /**
   * navigate to the main menu
   */
  navigateMainMenu() {
    this.router.navigate(['/']);
  }

  /**
   * Error flag will be deactivated, which clears the error news
   */
  vanishError() {
    this.error = false;
  }

  /**
   * Loads the specified page of news from the backend.
   * @param onlyNew to specify if all news are only unread should be loaded.
   */
  private loadNews(onlyNew: boolean) {
    // Backend pagination starts at page 0, therefore page must be reduced by 1
    this.newsService.getNews(onlyNew).subscribe(
      (news: News[]) => {
        this.news = news;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }
  /**
   * Set the error message
   */
  private defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (error.error.message !== 'No news available') {
      this.errorMessage = error.error.message;
    } else {
      this.errorMessage = error.error.error;
    }
  }
}
