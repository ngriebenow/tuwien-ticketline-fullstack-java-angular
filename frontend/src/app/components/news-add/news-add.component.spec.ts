import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsAddComponent } from './news-add.component';
import {RouterTestingModule} from '@angular/router/testing';
import {AuthGuard} from '../../guards/auth.guard';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {Globals} from '../../global/globals';
import {PictureUploadComponent} from '../picture-upload/picture-upload.component';

describe('NewsAddComponent', () => {
  let component: NewsAddComponent;
  let fixture: ComponentFixture<NewsAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{path: 'news-add', canActivate: [AuthGuard], component: NewsAddComponent}]),
        HttpClientTestingModule,
        ReactiveFormsModule,
      ],
      declarations: [
        NewsAddComponent,
        PictureUploadComponent,
      ],
      providers: [
        Globals,
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewsAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
