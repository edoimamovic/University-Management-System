import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes, CanActivate } from '@angular/router';
import {HttpClientModule } from '@angular/common/http';
import {JwtHelper } from 'angular2-jwt';
import { FormsModule } from '@angular/forms';


import { AppComponent } from './app.component';
import { MainComponent } from './main/main.component';
import { SigninComponent } from './signin/signin.component';

import { AuthService } from './auth.service';
import { AuthGuardService } from './auth-guard.service';

const appRoutes: Routes = [
  { path: '', component: SigninComponent },
  { path: 'main', component: MainComponent, canActivate: [AuthGuardService] },
];

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    SigninComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [AuthService,
    AuthGuardService,
    JwtHelper,],
  bootstrap: [AppComponent]
})
export class AppModule { }
