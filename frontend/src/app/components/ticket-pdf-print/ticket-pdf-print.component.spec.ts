import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketPdfPrintComponent } from './ticket-pdf-print.component';
import {Globals} from '../../global/globals';
import {CurrencyPipe, DatePipe} from '@angular/common';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('TicketPdfPrintComponent', () => {
  let component: TicketPdfPrintComponent;
  let fixture: ComponentFixture<TicketPdfPrintComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ TicketPdfPrintComponent ],
      providers: [
        Globals,
        CurrencyPipe,
        DatePipe
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TicketPdfPrintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
