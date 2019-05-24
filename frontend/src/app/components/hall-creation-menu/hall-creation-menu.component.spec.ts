import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HallCreationMenuComponent } from './hall-creation-menu.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {Globals} from '../../global/globals';

describe('HallCreationMenuComponent', () => {
  let component: HallCreationMenuComponent;
  let fixture: ComponentFixture<HallCreationMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HallCreationMenuComponent ],
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
    fixture = TestBed.createComponent(HallCreationMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
