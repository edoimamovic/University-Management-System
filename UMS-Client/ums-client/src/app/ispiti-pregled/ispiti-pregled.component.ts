import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-ispiti-pregled',
  templateUrl: './ispiti-pregled.component.html',
  styleUrls: ['./ispiti-pregled.component.css']
})
export class IspitiPregledComponent implements OnInit {

  constructor( public router : Router, public auth: AuthService, public http: HttpClient) { }

  kursevi = [];
  studenti = [];
  ispiti = [];
  filtriraniIspiti = [];
  kurs = null;

  selectCourse(id){
    this.filtriraniIspiti = this.ispiti.filter(x => x.id == id);
  }
  
  ngOnInit() {
    var that = this;
    let username = localStorage.getItem('user');
    this.http.get('http://localhost:3000/api/courses?username=' + username)
      .subscribe((res : any) => {that.kursevi = res},
                (err : any) => {});

    this.http.get('http://localhost:3000/api/ispiti?username=' + username)
    .subscribe((res : any) => {that.ispiti = res},
              (err : any) => {});
  }
}
