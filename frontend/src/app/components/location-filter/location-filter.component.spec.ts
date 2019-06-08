import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LocationFilterComponent } from './location-filter.component';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Globals} from '../../global/globals';

describe('LocationFilterComponent', () => {
  let component: LocationFilterComponent;
  let fixture: ComponentFixture<LocationFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LocationFilterComponent ],
      imports: [
        RouterTestingModule,
        HttpClientTestingModule,
        FormsModule,
        ReactiveFormsModule,
      ],
      providers: [
        Globals,
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LocationFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
