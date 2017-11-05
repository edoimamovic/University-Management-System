import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  constructor( public router : Router) { }
    
  user = "";

 public signOut() {
     localStorage.removeItem('token');
     localStorage.removeItem('user');
     this.router.navigate(['']);
 }

 ngOnInit() {
   this.user = localStorage.getItem('user');
 }
}
