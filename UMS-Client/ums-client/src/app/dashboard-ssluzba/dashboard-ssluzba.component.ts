import { Component, OnInit } from '@angular/core';
import {Chart} from 'chart.js';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-dashboard-ssluzba',
  templateUrl: './dashboard-ssluzba.component.html',
  styleUrls: ['./dashboard-ssluzba.component.css']
})
export class DashboardSsluzbaComponent implements OnInit {

  constructor( public router : Router, public auth: AuthService, public http: HttpClient) { }
  
  statistika = null;

  draw() {
    let canvasPc : any =document.getElementById("godine-pc");
    let canvasDc : any =document.getElementById("godine-dc");
    
     let ctxPc : any = canvasPc.getContext('2d');
     let ctxDc : any = canvasDc.getContext('2d');
     let chart1 = new Chart(ctxPc, {
       // The type of chart we want to create
       type: 'doughnut',
   
       // The data for our dataset
       data: {
           labels: this.statistika.prviciklus.map(x => x.godinaStudija + ". godina"),
           datasets: [{
               label: "Broj studenata po godinama",
               data: this.statistika.prviciklus.map(x => x.broj),
               backgroundColor: ["#0074D9", "#FF4136", "#2ECC40", "#FF851B", "#7FDBFF", "#B10DC9", "#FFDC00", "#001f3f", "#39CCCC", "#01FF70", "#85144b", "#F012BE", "#3D9970", "#111111", "#AAAAAA"]               
           }]
       },
   
       // Configuration options go here
       options: {
        maintainAspectRatio: true,
        responsive: true
       }
   });

    var chart2 = new Chart(ctxDc, {
      // The type of chart we want to create
      type: 'doughnut',

      // The data for our dataset
      data: {
          labels: this.statistika.prviciklus.map(x => x.godinaStudija + ". godina"),
          datasets: [{
              label: "Broj studenata po godinama",
              data: this.statistika.drugiciklus.map(x => x.broj),
              backgroundColor: ["#0074D9", "#FF4136", "#2ECC40", "#FF851B", "#7FDBFF", "#B10DC9", "#FFDC00", "#001f3f", "#39CCCC", "#01FF70", "#85144b", "#F012BE", "#3D9970", "#111111", "#AAAAAA"]               
          }]
      },

      // Configuration options go here
      options: {
      maintainAspectRatio: true,
      responsive: true
      }
    });

    let canvasLine : any =document.getElementById("godine-lc");
    let ctxLine : any = canvasLine.getContext('2d');
    var chart = new Chart(ctxLine, {
      type: "line",
    
      data: {
        labels: ["1. godina", "2. godina", "3. godina"],
        datasets: [
          {
            label: "Automatika i elektronika",
            //backgroundColor: "rgb(99, 132, 255)",
            borderColor: "rgb(99, 132, 255)",
            data: this.statistika.prosjeci.filter(x => x.odsjek == "Automatika i elektronika").map(x => x.prosjek + 1)
          },
          {
            label: "Računarstvo i informatika",
            //backgroundColor: "rgb(255, 99, 132)",
            borderColor: "rgb(255, 99, 132)",
            data: this.statistika.prosjeci.filter(x => x.odsjek == "Računarstvo i informatika").map(x => x.prosjek + 2)
          },
        ]
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
      }
  });
}

  ngOnInit() {
    var that = this;
    this.http.get('http://localhost:3000/api/stats/studentska')
    .subscribe((res : any) => {that.statistika = res; that.draw(); },
              (err : any) => {});

  }
}
