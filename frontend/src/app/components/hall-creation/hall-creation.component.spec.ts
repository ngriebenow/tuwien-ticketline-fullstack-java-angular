import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HallCreationComponent } from './hall-creation.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import Global = NodeJS.Global;
import {Globals} from '../../global/globals';
import {HallCreationMenuComponent} from '../hall-creation-menu/hall-creation-menu.component';
import {HallCreationPlanComponent} from '../hall-creation-plan/hall-creation-plan.component';

describe('HallCreationComponent', () => {
  let component: HallCreationComponent;
  let fixture: ComponentFixture<HallCreationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HallCreationComponent ],
      imports: [
        HttpClientTestingModule,
        ReactiveFormsModule,
        RouterModule,
        RouterTestingModule,
        FormsModule,
        HallCreationMenuComponent,
        HallCreationPlanComponent,
      ],
      providers: [
        Globals,
      ]
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
