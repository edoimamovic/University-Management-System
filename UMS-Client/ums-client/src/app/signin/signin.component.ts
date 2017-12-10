import { Component, OnInit } from '@angular/core';
import {HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { JwtHelper } from 'angular2-jwt';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  constructor(public httpClient : HttpClient, public router : Router, public jwtHelper: JwtHelper, public auth: AuthService) {
   }

   username = "";
   password = "";
   loginValidacija = "";

   private loginCallback = (x : any) => {
    localStorage.setItem('token', x.token);
    localStorage.setItem('user', this.username);
    localStorage.setItem('role', x.uloga);
    let decoded = this.jwtHelper.decodeToken(x.token);
    let role = decoded.role;

    localStorage.setItem("role", role);

    if (localStorage.getItem('role') === 'profesor'){
      this.router.navigate(['./prof']);
    }
    else if (localStorage.getItem('role') === 'studentska'){
      this.router.navigate(['./dashboard-ssluzba']);
    }

  };


  public signIn() {
    if (!this.username || !this.password){
      return;
    }

    this.auth.signIn(this.username, this.password, this.loginCallback,
    err => {
      this.loginValidacija = err.error.message;
    })
  }

  ngOnInit() {
  }

}
