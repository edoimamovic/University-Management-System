import { Component, OnInit } from '@angular/core';
import {Chart} from 'chart.js';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-dashboard-prof',
  templateUrl: './dashboard-prof.component.html',
  styleUrls: ['./dashboard-prof.component.css']
})
export class DashboardProfComponent implements OnInit {

  constructor( public router : Router, public auth: AuthService, public http: HttpClient) { }
  
  statistika = null;

  draw() {
    let canvasPc : any =document.getElementById("godine-pc");
    let canvasLc : any =document.getElementById("godine-lc");
    let canvasRad : any =document.getElementById("godine-rad");
    
     let ctxPc : any = canvasPc.getContext('2d');
     let ctxLc : any = canvasLc.getContext('2d');
     let ctxRad : any = canvasRad.getContext('2d');

     let chart1 = new Chart(ctxPc, {
       // The type of chart we want to create
       type: 'bar',
   
       // The data for our dataset
       data: {
           labels: this.statistika.brojstudenata.map(x => x.predmet),
           datasets: [{
               label: "Broj studenata po godinama",
               data: this.statistika.brojstudenata.map(x => x.broj),
               backgroundColor: ["#0074D9", "#FF4136", "#2ECC40", "#FF851B", "#7FDBFF", "#B10DC9", "#FFDC00", "#001f3f", "#39CCCC", "#01FF70", "#85144b", "#F012BE", "#3D9970", "#111111", "#AAAAAA"]               
           }]
       },
   
       options: {
        responsive:false,
        maintainAspectRatio: false,
        scales: {
          yAxes: [{
              display: true,
              ticks: {
                  suggestedMin: 0,    // minimum will be 0, unless there is a lower value.
                  // OR //
                  beginAtZero: true   // minimum value will be 0.
              }
          }]
        }
      }   });

      let chart2 = new Chart(ctxLc, {
        // The type of chart we want to create
        type: 'horizontalBar',
    
        // The data for our dataset
        data: {
            labels: this.statistika.prosjeci.map(x => x.predmet),
            datasets: [{
                label: "Prosječne ocjene na Vašim predmetima",
                data: this.statistika.prosjeci.map(x => x.prosjek),
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
    this.http.get('http://localhost:3000/api/stats/profesor?username=' + this.auth.getUsername())
    .subscribe((res : any) => {that.statistika = res; that.draw(); },
              (err : any) => {});

  }
}
