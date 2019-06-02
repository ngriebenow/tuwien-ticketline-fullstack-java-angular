import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {News} from '../../dtos/news';
import {Router} from '@angular/router';
import {NewsService} from '../../services/news.service';
import {NewsComponent} from '../../components/news/news.component';
import {PictureTransferService} from '../../services/picture-transfer.service';

@Component({
  selector: 'app-news-add',
  templateUrl: './news-add.component.html',
  styleUrls: ['./news-add.component.scss']
})
export class NewsAddComponent implements OnInit {

  newsForm: FormGroup;
  // After first submission attempt, form validation will start
  submitted = false;
  constructor(private router: Router, private newsService: NewsService,
              private formBuilder: FormBuilder,
              private pictureTransferService: PictureTransferService) {
      this.newsForm = this.formBuilder.group({
      title: ['', [Validators.required]],
      summary: ['', [Validators.required]],
      text: ['', [Validators.required]],
    });
  }

  ngOnInit() {
  }

  /**
   * Starts form validation and builds a news dto for sending a creation request if the form is valid.
   * If the procedure was successful, the form will be cleared.
   */
  addNews() {
    this.submitted = true;
    if (this.newsForm.valid) {
      Promise.all(this.pictureTransferService.uploadData()).then(function (result) {
        const news: News = new News(null,
          this.newsForm.controls.title.value,
          this.newsForm.controls.summary.value,
          this.newsForm.controls.text.value,
          new Date().toISOString(),
          this.pictureTransferService.getIds(),
          false
        );
        this.createNews(news);
        this.clearForm();
        this.pictureTransferService.clearData();
      }.bind(this));
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
        this.router.navigate(['/news']);
      }
    );
  }
  /**
   * Clears the form
   */
  private clearForm() {
    this.newsForm.reset();
    this.submitted = false;
  }

}
