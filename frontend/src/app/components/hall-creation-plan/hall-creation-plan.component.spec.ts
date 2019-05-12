import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HallCreationPlanComponent } from './hall-creation-plan.component';

describe('HallCreationPlanComponent', () => {
  let component: HallCreationPlanComponent;
  let fixture: ComponentFixture<HallCreationPlanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HallCreationPlanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HallCreationPlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
