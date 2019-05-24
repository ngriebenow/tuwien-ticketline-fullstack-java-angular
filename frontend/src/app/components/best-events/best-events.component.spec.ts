import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BestEventsComponent } from './best-events.component';

describe('BestEventsComponent', () => {
  let component: BestEventsComponent;
  let fixture: ComponentFixture<BestEventsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BestEventsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BestEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
