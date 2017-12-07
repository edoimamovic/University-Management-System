import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-ispiti',
  templateUrl: './ispiti.component.html',
  styleUrls: ['./ispiti.component.css']
})
export class IspitiComponent implements OnInit {
  constructor( public router : Router, public auth: AuthService, public http: HttpClient) { }
  
  kursevi = [];
  studenti = [];
  kurs = null;
  sale = [];
  poruka = "";

  ispit = {
    termin: null,
    datum: null,
    vrijeme: null,
    datumPrijave: null,
    vrijemePrijave: null,
    kurs: null,
    sala: null
  }

  public kreirajIspit() : void {
    var that = this;
    this.http.post('http://localhost:3000/api/ispit', {kurs: this.ispit.kurs, rokPrijave: this.ispit.datumPrijave + " " + this.ispit.vrijemePrijave, vrijemeOdrzavanja: this.ispit.datum + " " + this.ispit.vrijeme, sala: this.ispit.sala})
    .subscribe((res : any) => {that.studenti = res; that.poruka = "Uspješno dodan ispitni rok."},
              (err : any) => {that.poruka = "Došlo je do greške."});
  }

  ngOnInit() {
    var that = this;
    let username = localStorage.getItem('user');
    this.http.get('http://localhost:3000/api/courses?username=' + username)
      .subscribe((res : any) => {that.kursevi = res},
                (err : any) => {});

    this.http.get('http://localhost:3000/api/sala')
    .subscribe((res : any) => {that.sale = res},
              (err : any) => {});
  }
}
