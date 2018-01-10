import { Component, OnInit } from '@angular/core';
import {Chart} from 'chart.js';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-stud-prijave',
  templateUrl: './stud-prijave.component.html',
  styleUrls: ['./stud-prijave.component.css']
})
export class StudPrijaveComponent implements OnInit {

constructor( public router : Router, public auth: AuthService, public http: HttpClient) { }
  
  user = "";
  rokovi=[];
  prijave=[];

  ngOnInit() {
	  this.user = localStorage.getItem('user');
	  
	  this.http.get('http://localhost:3000/api/student/ispitniRokovi?username1=' + localStorage.getItem('user')
																 + '&username2=' + localStorage.getItem('user'))
		.subscribe((res : any) => {this.rokovi=res;}, 
				   (err : any) => {});
				   
	  this.http.get('http://localhost:3000/api/student/prijaveIspita?username=' + localStorage.getItem('user'))
		.subscribe((res : any) => {this.prijave=res;}, 
				   (err : any) => {});
  }

  public otkaziPrijavu(prijavaId){
	  this.http.post('http://localhost:3000/api/student/ukloniPrijavu', {prijavaId:prijavaId})
				.subscribe((res : any) => {}, 
						   (err : any) => {});
  }
  
  public podnesiPrijavu(kursId, ispitniRokId){
	  this.http.get('http://localhost:3000/api/student/nastavaId?kursId=' + kursId + '&username=' + localStorage.getItem('user'))
		.subscribe((res : any) => {
			this.http.post('http://localhost:3000/api/student/prijava', {nastavaId:res[0].id, ispitniRokId:ispitniRokId})
				.subscribe((res : any) => {}, 
						   (err : any) => {});
		}, 
				   (err : any) => {});
	  
	  this.router.navigate(['./stud-prijave']);
  }
  
}
