import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketPdfPrintComponent } from './ticket-pdf-print.component';

describe('TicketPdfPrintComponent', () => {
  let component: TicketPdfPrintComponent;
  let fixture: ComponentFixture<TicketPdfPrintComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TicketPdfPrintComponent ]
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
