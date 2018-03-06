import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { UIRouterModule } from '@uirouter/angular';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import {AppComponent} from './app.component';
import {NavComponent} from './nav/nav.component';

import { appRoutes } from './routes';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { ReimbursementComponent } from './components/reimbursement/reimbursement.component';
import { User } from './beans/user';
import { ManagersComponent } from './components/managers/managers.component';
import { AddReimbursementsComponent } from './components/add-reimbursements/add-reimbursements.component';
import { FilterPipe } from './filter.pipe';

@NgModule({
  imports: [
    BrowserModule,
    HttpModule,
    HttpClientModule,
    NgbModule.forRoot(),
    RouterModule.forRoot(appRoutes),
    FormsModule
  ],
  declarations: [
    AppComponent,
    NavComponent,
    LoginComponent,
    HomeComponent,
    ReimbursementComponent,
    ManagersComponent,
    AddReimbursementsComponent,
    FilterPipe,
   ],
  providers: [
    User
   ],
  bootstrap: [AppComponent]
})
export class AppModule { }
