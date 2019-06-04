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
import {NewsComponent} from './components/news/news.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {httpInterceptorProviders} from './interceptors';
import {Globals} from './global/globals';
import {HallCreationComponent} from './components/hall-creation/hall-creation.component';
import {HallCreationMenuComponent} from './components/hall-creation-menu/hall-creation-menu.component';
import {HallCreationPlanComponent} from './components/hall-creation-plan/hall-creation-plan.component';
import {EventComponent} from './components/event/event.component';
import {EventFilterComponent} from './components/event-filter/event-filter.component';
import {IMyDpOptions, MyDatePickerModule} from 'mydatepicker';
import {InvoiceFilterComponent} from './components/invoice-filter/invoice-filter.component';
import {HallViewingComponent} from './components/hall-viewing/hall-viewing.component';
import {PictureDisplayComponent} from './components/picture-display/picture-display.component';
import {NewsDetailComponent} from './components/news-detail/news-detail.component';
import {NewsAddComponent} from './components/news-add/news-add.component';
import {PictureUploadComponent} from './components/picture-upload/picture-upload.component';
import {AlertComponent} from './components/alert/alert.component';
import {InvoiceDetailComponent} from './components/invoice-detail/invoice-detail.component';
import {BestEventsComponent} from './components/best-events/best-events.component';
import {UserFilterComponent} from './components/user-filter/user-filter.component';
import {ClientFilterComponent} from './components/client-filter/client-filter.component';
import {UserAddComponent} from './components/user-add/user-add.component';
import {UserEditComponent} from './components/user-edit/user-edit.component';
import {ClientAddComponent} from './components/client-add/client-add.component';
import {ClientEditComponent} from './components/client-edit/client-edit.component';
import {ConfirmationDialogComponent} from './components/confirmation-dialog/confirmation-dialog.component';

import { registerLocaleData } from '@angular/common';
import localeDe from '@angular/common/locales/de-AT';
import localeDeExtra from '@angular/common/locales/extra/de-AT';

import { LOCALE_ID } from '@angular/core';

registerLocaleData(localeDe, 'de-AT', localeDeExtra);

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    NewsComponent,
    HallCreationComponent,
    HallCreationMenuComponent,
    HallCreationPlanComponent,
    EventComponent,
    EventFilterComponent,
    InvoiceFilterComponent,
    HallViewingComponent,
    PictureDisplayComponent,
    NewsDetailComponent,
    NewsAddComponent,
    PictureUploadComponent,
    AlertComponent,
    InvoiceDetailComponent,
    BestEventsComponent,
    UserFilterComponent,
    ClientFilterComponent,
    UserAddComponent,
    UserEditComponent,
    ClientAddComponent,
    ClientEditComponent,
    ConfirmationDialogComponent,
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
  providers: [httpInterceptorProviders, Globals,
    { provide: LOCALE_ID, useValue: 'de-AT' } ],
  bootstrap: [AppComponent],
  entryComponents: [ ConfirmationDialogComponent ]
})
export class AppModule {


  public myDatePickerOptions: IMyDpOptions = {
    sunHighlight: false,
    dateFormat: 'dd.mm.yyyy',
  };

  public model: any = { date: { year: 2018, month: 10, day: 9 } };


}
