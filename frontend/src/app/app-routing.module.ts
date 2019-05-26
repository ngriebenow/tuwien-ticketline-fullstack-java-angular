import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './guards/auth.guard';
import {NewsComponent} from './components/news/news.component';
import {HallCreationComponent} from './components/hall-creation/hall-creation.component';
import {EventComponent} from './components/event/event.component';
import {EventFilterComponent} from './components/event-filter/event-filter.component';
import {InvoiceFilterComponent} from './components/invoice-filter/invoice-filter.component';
import {InvoiceDetailComponent} from './components/invoice-detail/invoice-detail.component';
import {HallViewingComponent} from './components/hall-viewing/hall-viewing.component';
import {UserFilterComponent} from './components/user-filter/user-filter.component';
import {BestEventsComponent} from './components/best-events/best-events.component';
import {ClientFilterComponent} from './components/client-filter/client-filter.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'news', canActivate: [AuthGuard], component: NewsComponent},
  {path: 'hall-creation', canActivate: [AuthGuard], component: HallCreationComponent},
  {path: 'events/:id', canActivate: [AuthGuard], component: EventComponent},
  {path: 'event-filter', canActivate: [AuthGuard], component: EventFilterComponent},
  {path: 'best-events', canActivate: [AuthGuard], component: BestEventsComponent},
  {path: 'invoice-filter', canActivate: [AuthGuard], component: InvoiceFilterComponent},
  {path: 'invoices/:id', canActivate: [AuthGuard], component: InvoiceDetailComponent},
  {path: 'finalize-transaction', canActivate: [AuthGuard], component: InvoiceDetailComponent},
  {path: 'hall-viewing', canActivate: [AuthGuard], component: HallViewingComponent},
  {path: 'user-filter', canActivate: [AuthGuard], component: UserFilterComponent},
  {path: 'client-filter', canActivate: [AuthGuard], component: ClientFilterComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}

