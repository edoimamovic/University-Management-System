import { Component, OnInit } from '@angular/core';
import {Chart} from 'chart.js';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-stud-potvrde',
  templateUrl: './stud-potvrde.component.html',
  styleUrls: ['./stud-potvrde.component.css']
})
export class StudPotvrdeComponent implements OnInit {

constructor( public router : Router, public auth: AuthService, public http: HttpClient) { }
  
  user = "";
  potvrde=[];
  tipoviPotvrda=[];

  ngOnInit() {
	  this.user = localStorage.getItem('user');
	  
	  this.http.get('http://localhost:3000/api/student/potvrde?username=' + localStorage.getItem('user'))
		.subscribe((res : any) => {this.potvrde=res;}, 
				   (err : any) => {});
				   
	  this.http.get('http://localhost:3000/api/tipoviPotvrda')
		.subscribe((res : any) => {this.tipoviPotvrda=res;}, 
				   (err : any) => {});
  }
  
  public podnesiZahtjev(){
	  var el=document.getElementById("tipoviPotvrda") as HTMLSelectElement;
	  let tipPotvrde=parseInt(el.options[el.selectedIndex].value);
	  
	  let studentId=0;
	  this.http.get('http://localhost:3000/api/username/student?username=' + localStorage.getItem('user'))
		.subscribe((res : any) => {
			this.http.post('http://localhost:3000/api/student/zahtjev', {tipPotvrdeId:tipPotvrde, studentId:res[0].id})
				.subscribe((res : any) => {}, 
						   (err : any) => {});
		}, 
				   (err : any) => {});
	  
	  this.router.navigate(['./stud-potvrde']);
  }
  
  public otkaziZahtjev(zahtjevId){
	  this.http.post('http://localhost:3000/api/student/ukloniZahtjev', {zahtjevId:zahtjevId})
				.subscribe((res : any) => {}, 
						   (err : any) => {});
  }

}
