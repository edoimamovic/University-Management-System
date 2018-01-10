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
import { ProfAuthGuardService } from './prof-auth-guard.service';
import { ProfComponent } from './prof/prof.component';
import { IspitiComponent } from './ispiti/ispiti.component';
import { IspitiPregledComponent } from './ispiti-pregled/ispiti-pregled.component';
import { PredmetiComponent } from './predmeti/predmeti.component';
import { AddUserComponent } from './add-user/add-user.component';
import { KurseviComponent } from './kursevi/kursevi.component';
import { DashboardSsluzbaComponent } from './dashboard-ssluzba/dashboard-ssluzba.component';
import { DashboardProfComponent } from './dashboard-prof/dashboard-prof.component';
import { DashboardStudComponent } from './dashboard-stud/dashboard-stud.component';
import { StudPredmetiComponent } from './stud-predmeti/stud-predmeti.component';
import { StudPrijaveComponent } from './stud-prijave/stud-prijave.component';
import { StudPotvrdeComponent } from './stud-potvrde/stud-potvrde.component';
import { StudOcjeneComponent } from './stud-ocjene/stud-ocjene.component';
import { StudIspitiComponent } from './stud-ispiti/stud-ispiti.component';

const appRoutes: Routes = [
  { path: '', component: SigninComponent, canActivate: [ProfAuthGuardService] },
  { path: 'main', component: MainComponent, canActivate: [AuthGuardService] },
  { path: 'prof', component: ProfComponent, canActivate: [AuthGuardService] },
  { path: 'ispiti', component: IspitiComponent, canActivate: [AuthGuardService] },
  { path: 'ispiti-pregled', component: IspitiPregledComponent, canActivate: [AuthGuardService] },
  { path: 'predmeti', component: PredmetiComponent, canActivate: [AuthGuardService] },
  { path: 'add-user', component: AddUserComponent, canActivate: [AuthGuardService] },
  { path: 'kursevi', component: KurseviComponent, canActivate: [AuthGuardService] },
  { path: 'dashboard-ssluzba', component: DashboardSsluzbaComponent, canActivate: [AuthGuardService] },
  { path: 'dashboard-prof', component: DashboardProfComponent, canActivate: [AuthGuardService] },
  { path: 'dashboard-stud', component: DashboardStudComponent, canActivate: [AuthGuardService] },
  { path: 'stud-predmeti', component: StudPredmetiComponent, canActivate: [AuthGuardService] },
  { path: 'stud-prijave', component: StudPrijaveComponent, canActivate: [AuthGuardService] },
  { path: 'stud-potvrde', component: StudPotvrdeComponent, canActivate: [AuthGuardService] },
  { path: 'stud-ocjene', component: StudOcjeneComponent, canActivate: [AuthGuardService] },
  { path: 'stud-ispiti', component: StudIspitiComponent, canActivate: [AuthGuardService] },
];

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    SigninComponent,
    ProfComponent,
    IspitiComponent,
    IspitiPregledComponent,
    PredmetiComponent,
    AddUserComponent,
    KurseviComponent,
    DashboardSsluzbaComponent,
    DashboardProfComponent,
    DashboardStudComponent,
	StudPredmetiComponent,
	StudPrijaveComponent,
	StudPotvrdeComponent,
	StudOcjeneComponent,
	StudIspitiComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [AuthService,
    AuthGuardService,
    ProfAuthGuardService,
    JwtHelper,],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(public auth: AuthService){}

 }
