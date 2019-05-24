import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';

import {Globals} from '../../global/globals';
import {UserFilterComponent} from './user-filter.component';

describe('UserFilterComponent', () => {
  let component: UserFilterComponent;
  let fixture: ComponentFixture<UserFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [UserFilterComponent],
      imports: [
        HttpClientTestingModule,
      ],
      providers: [
        Globals,
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
