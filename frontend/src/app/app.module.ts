import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './components/header/header.component';
import {FooterComponent} from './components/footer/footer.component';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {MessageComponent} from './components/message/message.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {httpInterceptorProviders} from './interceptors';
import {Globals} from './global/globals';
import { HallCreationComponent } from './components/hall-creation/hall-creation.component';
import { HallCreationMenuComponent } from './components/hall-creation-menu/hall-creation-menu.component';
import { HallCreationPlanComponent } from './components/hall-creation-plan/hall-creation-plan.component';
import { EventComponent } from './components/event/event.component';
import { EventFilterComponent } from './components/event-filter/event-filter.component';
import { MyDatePickerModule } from 'mydatepicker';
import {IMyDpOptions} from 'mydatepicker';
import {HallViewingComponent} from './components/hall-viewing/hall-viewing.component';
import { EventTopTenComponent } from './components/event-top-ten/event-top-ten.component';
import { BestEventsComponent } from './components/best-events/best-events.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    MessageComponent,
    HallCreationComponent,
    HallCreationMenuComponent,
    HallCreationPlanComponent,
    EventComponent,
    EventFilterComponent,
    HallViewingComponent,
    EventTopTenComponent,
    BestEventsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    MyDatePickerModule
  ],
  providers: [httpInterceptorProviders, Globals],
  bootstrap: [AppComponent]
})
export class AppModule {

  public myDatePickerOptions: IMyDpOptions = {
    sunHighlight: false,
    dateFormat: 'dd.mm.yyyy',
  };

  public model: any = { date: { year: 2018, month: 10, day: 9 } };


}
