import { Component, OnInit } from '@angular/core';
import {Chart} from 'chart.js';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-stud-ispiti',
  templateUrl: './stud-ispiti.component.html',
  styleUrls: ['./stud-ispiti.component.css']
})
export class StudIspitiComponent implements OnInit {

constructor( public router : Router, public auth: AuthService, public http: HttpClient) { }
  
  user = "";
  ispiti=[];

  ngOnInit() {
	  this.user = localStorage.getItem('user');
	  
	  this.http.get('http://localhost:3000/api/student/ispiti?username=' + localStorage.getItem('user'))
		.subscribe((res : any) => {this.ispiti=res;}, 
				   (err : any) => {});
  }

}
