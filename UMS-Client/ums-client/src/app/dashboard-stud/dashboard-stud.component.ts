import { Component, OnInit } from '@angular/core';
import {Chart} from 'chart.js';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-dashboard-stud',
  templateUrl: './dashboard-stud.component.html',
  styleUrls: ['./dashboard-stud.component.css']
})
export class DashboardStudComponent implements OnInit {

  constructor( public router : Router, public auth: AuthService, public http: HttpClient) { }
  
  statistika = null;

  draw() {
    let canvasRad : any =document.getElementById("godine-rad");
    let ctxRad : any = canvasRad.getContext('2d');

      let chart3 = new Chart(ctxRad, {
        // The type of chart we want to create
        type: 'radar',
    
        // The data for our dataset
        data: {
            labels: ["redovni 1. parcijalni", "redovni 2. parcijalni", "završni", "popravni 1. parcijalni", "popravni 2. parcijalni"],
            datasets: [{
                label: "Distribucija bodova po ispitu",
                data: this.statistika.bodovi.map(x => x.prosjek),
                backgroundColor: ["#0074D9", "#FF4136", "#2ECC40", "#FF851B", "#7FDBFF", "#B10DC9", "#FFDC00", "#001f3f", "#39CCCC", "#01FF70", "#85144b", "#F012BE", "#3D9970", "#111111", "#AAAAAA"]               
            }]
        },
    
        options: {
         responsive:false,
         maintainAspectRatio: false,
         scales: {
           xAxes: [{
               display: true,
               ticks: {
                   suggestedMin: 6,    // minimum will be 0, unless there is a lower value.
               }
           }]
         }
       }
      });
 

}

  ngOnInit() {
    var that = this;
    this.http.get('http://localhost:3000/api/stats/student?username=' + this.auth.getUsername())
    .subscribe((res : any) => {that.statistika = res; that.draw(); },
              (err : any) => {});

  }
}
