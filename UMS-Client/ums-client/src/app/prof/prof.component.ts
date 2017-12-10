import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-prof',
  templateUrl: './prof.component.html',
  styleUrls: ['./prof.component.css']
})
export class ProfComponent implements OnInit {

  constructor( public router : Router, public auth: AuthService, public http: HttpClient) { }

  kursevi = [];
  studenti = [];
  vrstaIspita = [];
  kurs = null;

  public selectCourse(index : number) : void {
    var that = this;
    this.kurs = this.kursevi[index];
    this.http.get('http://localhost:3000/api/courses/students?course=' + this.kurs.id)
    .subscribe((res : any) => {that.studenti = res},
              (err : any) => {});
  }

  ngOnInit() {
    var that = this;
    let username = localStorage.getItem('user');
    this.http.get('http://localhost:3000/api/courses?username=' + username)
      .subscribe((res : any) => {that.kursevi = res},
                (err : any) => {});
  }
}
