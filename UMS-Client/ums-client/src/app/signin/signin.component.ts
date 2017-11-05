import { Component, OnInit } from '@angular/core';
import {HttpClient } from '@angular/common/http';
import { Router, CanActivate } from '@angular/router';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  constructor(public httpClient : HttpClient, public router : Router) { 
   }

   username = "";
   password = "";
   loginValidacija = "";

  public signIn() {
    if (!this.username || !this.password){
      return;
    }
    var that = this;
    this.httpClient.post('http://localhost:3000/api/users/authenticate', {username: this.username, password: this.password})
    .subscribe((x : any) => {
      localStorage.setItem('token', x.token);
      localStorage.setItem('user', this.username);
      this.router.navigate(['./main']);
    },
    err => {
      this.loginValidacija = err.error.message;
    })
  }

  ngOnInit() {
  }

}
