import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LocationAddComponent } from './location-add.component';
import {RouterTestingModule} from '@angular/router/testing';
import {RouterModule} from '@angular/router';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {Globals} from '../../global/globals';
import {FormsModule} from '@angular/forms';

describe('LocationAddComponent', () => {
  let component: LocationAddComponent;
  let fixture: ComponentFixture<LocationAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LocationAddComponent ],
      imports: [
        RouterTestingModule,
        RouterModule,
        HttpClientTestingModule,
        FormsModule,
      ],
      providers: [
        Globals,
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LocationAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
