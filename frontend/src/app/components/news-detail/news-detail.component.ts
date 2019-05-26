import {Component, Input, OnInit} from '@angular/core';
import {News} from '../../dtos/news';
import {NewsService} from '../../services/news.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-news-detail',
  templateUrl: './news-detail.component.html',
  styleUrls: ['./news-detail.component.scss']
})
export class NewsDetailComponent implements OnInit {

  news: News;
  constructor(private newsService: NewsService,
              private route: ActivatedRoute) { }
  ngOnInit() {
    this.getNews();
  }
  /**
   * Load news with id specified in route
   */
  getNews() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.loadNewsDetailed(id);
  }
  loadNewsDetailed(id: number) {
    this.newsService.getNewsById(id).subscribe(
      news => this.news = news);
  }

}
