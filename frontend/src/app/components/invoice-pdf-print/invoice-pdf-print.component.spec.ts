import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InvoicePdfPrintComponent } from './invoice-pdf-print.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {Globals} from '../../global/globals';

describe('InvoicePdfPrintComponent', () => {
  let component: InvoicePdfPrintComponent;
  let fixture: ComponentFixture<InvoicePdfPrintComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InvoicePdfPrintComponent ],
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
    fixture = TestBed.createComponent(InvoicePdfPrintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
});
