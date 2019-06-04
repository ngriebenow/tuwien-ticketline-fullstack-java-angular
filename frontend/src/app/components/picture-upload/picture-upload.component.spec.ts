import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PictureUploadComponent } from './picture-upload.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {Globals} from '../../global/globals';

describe('PictureUploadComponent', () => {
  let component: PictureUploadComponent;
  let fixture: ComponentFixture<PictureUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        ReactiveFormsModule,
        RouterTestingModule
      ],
      declarations: [
        PictureUploadComponent,
      ],
      providers: [
        Globals,
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PictureUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
