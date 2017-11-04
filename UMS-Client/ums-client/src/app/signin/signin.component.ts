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

  public signIn() {
    var that = this;
    this.httpClient.post('http://localhost:3000/api/users/authenticate', {username: this.username, password: this.password})
    .subscribe((x : any) => {
      localStorage.setItem('token', x.token);

      this.router.navigate(['./main']);
    });
  }

  ngOnInit() {
  }

}
