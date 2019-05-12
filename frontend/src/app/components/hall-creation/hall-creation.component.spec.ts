import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HallCreationComponent } from './hall-creation.component';

describe('HallCreationComponent', () => {
  let component: HallCreationComponent;
  let fixture: ComponentFixture<HallCreationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HallCreationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HallCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
