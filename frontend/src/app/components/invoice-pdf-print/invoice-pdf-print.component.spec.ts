import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InvoicePdfPrintComponent } from './invoice-pdf-print.component';

describe('InvoicePdfPrintComponent', () => {
  let component: InvoicePdfPrintComponent;
  let fixture: ComponentFixture<InvoicePdfPrintComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InvoicePdfPrintComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InvoicePdfPrintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
