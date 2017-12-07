import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {
  constructor( public router : Router, public auth: AuthService, public http: HttpClient) { }
  
  odsjeci = [];
  poruka = "";

  user = {
    ime: null,
    prezime: null,
    username: null,
    password: null,
    odsjek: null,
    uloga: null
  }

  public kreirajKorisnika() : void {
    var that = this;
    this.http.post('http://localhost:3000/api/users', this.user)
    .subscribe((res : any) => {that.poruka = "Uspješno dodan student."},
              (err : any) => {that.poruka = "Došlo je do greške."});
  }

  ngOnInit() {
    var that = this;
    let username = localStorage.getItem('user');

    this.http.get('http://localhost:3000/api/odsjek')
    .subscribe((res : any) => {that.odsjeci = res},
              (err : any) => {});
  }
}
