import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-predmeti',
  templateUrl: './predmeti.component.html',
  styleUrls: ['./predmeti.component.css']
})
export class PredmetiComponent implements OnInit {
  constructor( public router : Router, public auth: AuthService, public http: HttpClient) { }
  
  profesori = [];
  odsjeci = [];
  poruka = "";

  predmet = {
    naziv: null,
    profesor: null,
    ects: null,
    brVjezbi: null,
    brPredavanja: null,
    izborni: false,
    opis: null,
    sifra: null,
    odsjek: null
  }

  public kreirajPredmet() : void {
    var that = this;
    this.http.post('http://localhost:3000/api/predmet', this.predmet)
    .subscribe((res : any) => {that.poruka = "Uspješno dodan ispitni rok."},
              (err : any) => {that.poruka = "Došlo je do greške."});
  }

  ngOnInit() {
    var that = this;
    let username = localStorage.getItem('user');

    this.http.get('http://localhost:3000/api/profesori')
    .subscribe((res : any) => {that.profesori = res},
              (err : any) => {});

    this.http.get('http://localhost:3000/api/odsjek')
    .subscribe((res : any) => {that.odsjeci = res},
              (err : any) => {});
  }
}
