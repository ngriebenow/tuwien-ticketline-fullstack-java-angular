import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {NewsService} from '../../services/news.service';
import {News} from '../../dtos/news';
import {NgbPaginationConfig} from '@ng-bootstrap/ng-bootstrap';
import * as _ from 'lodash';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.scss']
})
export class NewsComponent implements OnInit {

  error = false;
  errorMessage = '';
  newsForm: FormGroup;
  // After first submission attempt, form validation will start
  submitted = false;
  private news: News[];

  constructor(private newsService: NewsService, private ngbPaginationConfig: NgbPaginationConfig, private formBuilder: FormBuilder,
              private cd: ChangeDetectorRef, private authService: AuthService) {
    this.newsForm = this.formBuilder.group({
      title: ['', [Validators.required]],
      summary: ['', [Validators.required]],
      text: ['', [Validators.required]],
    });
  }

  ngOnInit() {
    this.loadNews();
  }

  /**
   * Returns true if the authenticated user is an admin
   */
  isAdmin(): boolean {
    return this.authService.getUserRole() === 'ADMIN';
  }

  /**
   * Starts form validation and builds a news dto for sending a creation request if the form is valid.
   * If the procedure was successful, the form will be cleared.
   */
  addNews() {
    this.submitted = true;
    if (this.newsForm.valid) {
      const news: News = new News(null,
        this.newsForm.controls.title.value,
        this.newsForm.controls.summary.value,
        this.newsForm.controls.text.value,
        new Date().toISOString()
      );
      this.createNews(news);
      this.clearForm();
    } else {
      console.log('Invalid input');
    }
  }

  /**
   * Sends news creation request
   * @param news the news which should be created
   */
  createNews(news: News) {
    this.newsService.createNews(news).subscribe(
      () => {
        this.loadNews();
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  getNews(): News[] {
    return this.news;
  }

  /**
   * Shows the specified news details. If it is necessary, the details text will be loaded
   * @param id the id of the news which details should be shown
   */
  getNewsDetails(id: number) {
    if (_.isEmpty(this.news.find(x => x.id === id).text)) {
      this.loadNewsDetails(id);
    }
  }

  /**
   * Loads the text of news and update the existing array of news
   * @param id the id of the news which details should be loaded
   */
  loadNewsDetails(id: number) {
    this.newsService.getNewsById(id).subscribe(
      (news: News) => {
        const result = this.news.find(x => x.id === id);
        result.text = news.text;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  /**
   * Error flag will be deactivated, which clears the error news
   */
  vanishError() {
    this.error = false;
  }

  /**
   * Loads the specified page of news from the backend
   */
  private loadNews() {
    // Backend pagination starts at page 0, therefore page must be reduced by 1
    this.newsService.getNews().subscribe(
      (news: News[]) => {
        this.news = news;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }


  private defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (error.error.message !== 'No news available') {
      this.errorMessage = error.error.message;
    } else {
      this.errorMessage = error.error.error;
    }
  }

  private clearForm() {
    this.newsForm.reset();
    this.submitted = false;
  }
}
