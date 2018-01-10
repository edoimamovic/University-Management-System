import { Component, OnInit } from '@angular/core';
import {Chart} from 'chart.js';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-stud-predmeti',
  templateUrl: './stud-predmeti.component.html',
  styleUrls: ['./stud-predmeti.component.css']
})
export class StudPredmetiComponent implements OnInit {

constructor( public router : Router, public auth: AuthService, public http: HttpClient) { }
  
  user = "";
  kursevi=[];

  ngOnInit() {
	  this.user = localStorage.getItem('user');
	  
	  this.http.get('http://localhost:3000/api/student/predmeti?username=' + localStorage.getItem('user'))
		.subscribe((res : any) => {this.kursevi=res;}, 
				   (err : any) => {});
  }

}
