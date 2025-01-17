import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule} from '@angular/common/http/testing';
import { EventFilterComponent } from './event-filter.component';
import { Globals} from '../../global/globals';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MyDatePickerModule } from 'mydatepicker';
import {RouterTestingModule} from '@angular/router/testing';
import {RouterModule} from '@angular/router';

describe('EventFilterComponent', () => {
  let component: EventFilterComponent;
  let fixture: ComponentFixture<EventFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        EventFilterComponent
      ],
      imports: [
        HttpClientTestingModule,
        ReactiveFormsModule,
        FormsModule,
        MyDatePickerModule,
        RouterModule,
        RouterTestingModule
      ],
      providers: [
        Globals,
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

