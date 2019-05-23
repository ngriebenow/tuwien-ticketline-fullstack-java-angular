import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HallViewingComponent } from './hall-viewing.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {Globals} from '../../global/globals';

describe('HallViewingComponent', () => {
  let component: HallViewingComponent;
  let fixture: ComponentFixture<HallViewingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HallViewingComponent ],
      imports: [
        HttpClientTestingModule,
        ReactiveFormsModule,
        FormsModule,
        RouterModule,
        RouterTestingModule,
        ],
      providers: [
        Globals,
      ]
     })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HallViewingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
