// import { async, ComponentFixture, TestBed } from '@angular/core/testing';
// import { RouterModule } from '@angular/router';
// import {HttpClientTestingModule} from '@angular/common/http/testing';

// import {Globals} from '../../global/globals';
// import { InvoiceDetailComponent } from './invoice-detail.component';
// import {Invoice} from '../../dtos/invoice';
// import {Ticket} from '../../dtos/ticket';
// import {Client} from '../../dtos/client';

// class MockTicketingService {
//   getTransientInvoice(): Invoice {
//     const client = new Client(0, 'Karl', 'Lrak', 'karl@lrak.com');
//     const tickets = [new Ticket(0, 'title', 'eventName', 'perfName', '', 'cat1', 1200, 'loc', 'hall', 0, 0)];
//     return new Invoice(0, 'asdf', 1, false, false, null, client, tickets);
//   }
// }

// describe('InvoiceDetailComponent', () => {
//   let service: MockTicketingService;
//   let component: InvoiceDetailComponent;
//   let fixture: ComponentFixture<InvoiceDetailComponent>;

//   beforeEach(async(() => {
//     TestBed.configureTestingModule({
//       declarations: [ InvoiceDetailComponent ],
//       imports: [
//         HttpClientTestingModule,
//         RouterModule.forRoot([]),
//       ],
//       providers: [
//         Globals,
//       ]
//     })
//     .compileComponents();
//   }));

//   beforeEach(() => {
//     service = new MockTicketingService();
//     fixture = TestBed.createComponent(InvoiceDetailComponent);
//     component = fixture.componentInstance;
//     fixture.detectChanges();
//   });

//   it('should create', () => {
//     expect(component).toBeTruthy();
//   });
// });
