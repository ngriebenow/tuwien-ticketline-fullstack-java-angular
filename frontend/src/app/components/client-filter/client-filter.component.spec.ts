import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';

import {Globals} from '../../global/globals';
import {ClientFilterComponent} from './client-filter.component';

describe('ClientFilterComponent', () => {
  let component: ClientFilterComponent;
  let fixture: ComponentFixture<ClientFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ClientFilterComponent],
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
    fixture = TestBed.createComponent(ClientFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
