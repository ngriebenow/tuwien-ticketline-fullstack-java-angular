import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HallViewingComponent } from './hall-viewing.component';

describe('HallViewingComponent', () => {
  let component: HallViewingComponent;
  let fixture: ComponentFixture<HallViewingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HallViewingComponent ]
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
