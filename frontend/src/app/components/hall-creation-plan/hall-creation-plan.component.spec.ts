import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HallCreationPlanComponent } from './hall-creation-plan.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {Globals} from '../../global/globals';

describe('HallCreationPlanComponent', () => {
  let component: HallCreationPlanComponent;
  let fixture: ComponentFixture<HallCreationPlanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HallCreationPlanComponent ],
      imports: [
        HttpClientTestingModule,
        ReactiveFormsModule,
        RouterModule,
        RouterTestingModule,
        FormsModule,
      ],
      providers: [
        Globals,
      ]
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
