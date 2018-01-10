import { Component, OnInit } from '@angular/core';
import {Chart} from 'chart.js';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-stud-ocjene',
  templateUrl: './stud-ocjene.component.html',
  styleUrls: ['./stud-ocjene.component.css']
})
export class StudOcjeneComponent implements OnInit {

constructor( public router : Router, public auth: AuthService, public http: HttpClient) { }
  
  user = "";
  ocjene=[];

  ngOnInit() {
	  this.user = localStorage.getItem('user');
	  
	  this.http.get('http://localhost:3000/api/student/ocjene?username=' + localStorage.getItem('user'))
		.subscribe((res : any) => {this.ocjene=res;}, 
				   (err : any) => {});
  }

}
