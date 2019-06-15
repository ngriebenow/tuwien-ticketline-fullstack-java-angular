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
import {AlertService} from '../../services/alert.service';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.scss']
})
export class NewsComponent implements OnInit {

  error = false;
  newsForm: FormGroup;
  onlyNew: boolean;
  page = 0;
  count = 20;
  private queryParams = {};

  news: News[];

  constructor(private newsService: NewsService, private ngbPaginationConfig: NgbPaginationConfig, private formBuilder: FormBuilder,
              private cd: ChangeDetectorRef, private authService: AuthService, private route: ActivatedRoute,
              private router: Router,
              private alertService: AlertService) {
    this.newsForm = this.formBuilder.group({
      title: ['', [Validators.required]],
      summary: ['', [Validators.required]],
      text: ['', [Validators.required]],
    });
    this.news = [];
  }

  ngOnInit() {
    const element: HTMLElement = document.getElementsByClassName('news-container')[0] as HTMLElement;
    element.addEventListener('scroll', function(e) {
      const visibleHeight = element.clientHeight;
      const scrollableHeight = element.scrollHeight;
      const position = element.scrollTop;
      if (position + visibleHeight === scrollableHeight) {
        this.nextPage();
      }
    }.bind(this));
    this.route
    .queryParams
    .subscribe(params => {
      this.onlyNew = params['onlyNew'];
      this.loadNews();
    });
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
  /**
   * Get the loaded news.
   * @return news
   */
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
   * Increase page and load further entries.
   */
  nextPage() {
    this.page++;
    this.loadNews();
  }
  /**
   * Loads the specified page of news from the backend.
   */
  private loadNews() {
    this.queryParams['page'] = 0;
    this.queryParams['count'] = (this.page + 1) * this.count;

    this.newsService.getNews(this.onlyNew, this.queryParams).subscribe(
      (news: News[]) => {
        this.news = news;
      },
      error => {
        this.alertService.error('Ladefehler, bitte versuchen Sie es etwas später noch ein mal');
      }
    );
  }
}
