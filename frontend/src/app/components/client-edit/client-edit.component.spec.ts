import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RouterTestingModule} from '@angular/router/testing';
import {AuthGuard} from '../../guards/auth.guard';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {Globals} from '../../global/globals';
import {ClientEditComponent} from './client-edit.component';

describe('UserEditComponent', () => {
  let component: ClientEditComponent;
  let fixture: ComponentFixture<ClientEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{path: 'user-edit', canActivate: [AuthGuard], component: ClientEditComponent}]),
        HttpClientTestingModule,
        ReactiveFormsModule,
      ],
      declarations: [
        ClientEditComponent,
      ],
      providers: [
        Globals,
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
