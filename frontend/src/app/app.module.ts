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
import { EventComponent } from './components/event/event.component';
import { HallViewingComponent } from './components/hall-viewing/hall-viewing.component';
import { MyDatePickerModule } from 'mydatepicker';
import {IMyDpOptions} from 'mydatepicker';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    MessageComponent,
    EventComponent,
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
