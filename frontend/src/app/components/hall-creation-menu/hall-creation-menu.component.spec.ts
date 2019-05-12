import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HallCreationMenuComponent } from './hall-creation-menu.component';

describe('HallCreationMenuComponent', () => {
  let component: HallCreationMenuComponent;
  let fixture: ComponentFixture<HallCreationMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HallCreationMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HallCreationMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
