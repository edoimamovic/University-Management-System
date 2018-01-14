import { Component, OnInit } from '@angular/core';
import {Chart} from 'chart.js';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-stud-predmet',
  templateUrl: './stud-predmet.component.html',
  styleUrls: ['./stud-predmet.component.css']
})
export class StudPredmetComponent implements OnInit {

constructor( public router : Router, public auth: AuthService, public http: HttpClient) { }
  
  user = "";
  predmet = "";
  ocjena=[];
  ispiti=[];
  rokovi=[];
  prijave=[];

  ngOnInit() {
	  this.user = localStorage.getItem('user');
	  this.predmet = localStorage.getItem('predmet');
	  
	  this.http.get('http://localhost:3000/api/student/predmet/ispitniRokovi' + 
					'?username1=' + localStorage.getItem('user') +
					'&kursId=' + localStorage.getItem('kursId') +
					'&username2=' + localStorage.getItem('user'))
		.subscribe((res : any) => {this.rokovi=res;}, 
				   (err : any) => {});
				   
	  this.http.get('http://localhost:3000/api/student/predmet/prijaveIspita' + 
					'?kursId=' + localStorage.getItem('kursId') +
					'&username=' + localStorage.getItem('user'))
		.subscribe((res : any) => {this.prijave=res;}, 
				   (err : any) => {});
	  
	  this.http.get('http://localhost:3000/api/student/predmet/ispiti' + 
					'?kursId=' + localStorage.getItem('kursId') +
					'&username=' + localStorage.getItem('user'))
		.subscribe((res : any) => {this.ispiti=res;}, 
				   (err : any) => {});
	  
	  this.http.get('http://localhost:3000/api/student/predmet/ocjena' + 
					'?kursId=' + localStorage.getItem('kursId') +
					'&username=' + localStorage.getItem('user'))
		.subscribe((res : any) => {this.ocjena=res;}, 
				   (err : any) => {});
				  
  }

}
