import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PictureDisplayComponent } from './picture-display.component';
import {RouterTestingModule} from '@angular/router/testing';
import {AuthGuard} from '../../guards/auth.guard';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {Globals} from '../../global/globals';
import {Lightbox, LightboxConfig, LightboxEvent} from 'ngx-lightbox';

describe('PictureDisplayComponent', () => {
  let component: PictureDisplayComponent;
  let fixture: ComponentFixture<PictureDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        ReactiveFormsModule,
      ],
      declarations: [
        PictureDisplayComponent,
      ],
      providers: [
        Globals,
        Lightbox,
        LightboxConfig,
        LightboxEvent
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PictureDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
