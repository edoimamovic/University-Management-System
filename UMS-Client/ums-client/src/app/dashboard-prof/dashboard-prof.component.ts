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
    chart1 = null;
    chart2 = null;
    chart1Tip = "bar";
    chart2Tip = "horizontalBar";
    chart1Options = {
    responsive:false,
    maintainAspectRatio: false,
    /*scales: {
            yAxes: [{
                display: true,
                ticks: {
                    suggestedMin: 0,    // minimum will be 0, unless there is a lower value.
                    // OR //
                    beginAtZero: true   // minimum value will be 0.
                }
            }]
        }*/
    };

    chart2Options = {
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

    drawStudentiPoGodinama(){
        let chart1Data = {
            labels: this.statistika.brojstudenata.map(x => x.predmet),
            datasets: [{
                label: "Broj studenata po godinama",
                data: this.statistika.brojstudenata.map(x => x.broj),
                backgroundColor: ["#0074D9", "#FF4136", "#2ECC40", "#FF851B", "#7FDBFF", "#B10DC9", "#FFDC00", "#001f3f", "#39CCCC", "#01FF70", "#85144b", "#F012BE", "#3D9970", "#111111", "#AAAAAA"]               
            }]
        };

        let canvasPc : any =document.getElementById("godine-pc");
        let ctxPc : any = canvasPc.getContext('2d');
        if (this.chart1){
            this.chart1.destroy();
        }
        this.chart1 = new Chart(ctxPc, {
            // The type of chart we want to create
            type: this.chart1Tip,
            // The data for our dataset
            data: chart1Data,
            options: this.chart1Options
         });  
    }

    drawProsjeciPoPredmetima(){
        let chart2Data = {
            labels: this.statistika.prosjeci.map(x => x.predmet),
            datasets: [{
                label: "Prosječne ocjene na Vašim predmetima",
                data: this.statistika.prosjeci.map(x => x.prosjek),
                backgroundColor: ["#0074D9", "#FF4136", "#2ECC40", "#FF851B", "#7FDBFF", "#B10DC9", "#FFDC00", "#001f3f", "#39CCCC", "#01FF70", "#85144b", "#F012BE", "#3D9970", "#111111", "#AAAAAA"]               
            }]
        };

        let canvasLc : any =document.getElementById("godine-lc");
        let ctxLc : any = canvasLc.getContext('2d');
        if (this.chart2){
            this.chart2.destroy();
        }
        this.chart2 = new Chart(ctxLc, {
            // The type of chart we want to create
            type: this.chart2Tip,
            // The data for our dataset
            data: chart2Data,
            options: this.chart2Options });  
    }

  draw() {

    this.drawStudentiPoGodinama();
    this.drawProsjeciPoPredmetima();

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
    this.http.get('http://localhost:3000/api/stats/profesor?username=' + this.auth.getUsername())
    .subscribe((res : any) => {that.statistika = res; that.draw(); },
              (err : any) => {});

  }
}
