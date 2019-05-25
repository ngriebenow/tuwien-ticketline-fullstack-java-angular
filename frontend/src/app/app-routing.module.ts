import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './guards/auth.guard';
import {NewsComponent} from './components/news/news.component';
import {HallCreationComponent} from './components/hall-creation/hall-creation.component';
import {EventComponent} from './components/event/event.component';
import {EventFilterComponent} from './components/event-filter/event-filter.component';
import {HallViewingComponent} from './components/hall-viewing/hall-viewing.component';
import {NewsDetailComponent} from './components/news-detail/news-detail.component';
import {NewsAddComponent} from './components/news-add/news-add.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'news', canActivate: [AuthGuard], component: NewsComponent},
  {path: 'news/:id', canActivate: [AuthGuard], component: NewsDetailComponent},
  {path: 'news-add', canActivate: [AuthGuard], component: NewsAddComponent},
  {path: 'hall-creation', canActivate: [AuthGuard], component: HallCreationComponent},
  {path: 'events', canActivate: [AuthGuard], component: EventComponent},
  {path: 'events/:id', canActivate: [AuthGuard], component: EventComponent},
  {path: 'event-filter', canActivate: [AuthGuard], component: EventFilterComponent},
  {path: 'hall-viewing', canActivate: [AuthGuard], component: HallViewingComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}

