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
  chart1 = null;
  chart2 = null;
  chart1Tip = "doughnut";
  chart2Tip = "doughnut";
  chart1Options = {
    maintainAspectRatio: true,
    responsive: true
  };
  chart2Options = {
    maintainAspectRatio: true,
    responsive: true
  };

  drawStudentiPoGodinama(){
    let chart1Data = {
      labels: this.statistika.prviciklus.map(x => x.godinaStudija + ". godina"),
      datasets: [{
          label: "Broj studenata po godinama",
          data: this.statistika.prviciklus.map(x => x.broj),
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

  drawStudentiPoGodinamaDc(){
    let chart2Data = {
      labels: this.statistika.drugiciklus.map(x => x.godinaStudija + ". godina"),
      datasets: [{
          label: "Broj studenata po godinama",
          data: this.statistika.drugiciklus.map(x => x.broj),
          backgroundColor: ["#0074D9", "#FF4136", "#2ECC40", "#FF851B", "#7FDBFF", "#B10DC9", "#FFDC00", "#001f3f", "#39CCCC", "#01FF70", "#85144b", "#F012BE", "#3D9970", "#111111", "#AAAAAA"]               
      }]
  }

    let canvasDc : any =document.getElementById("godine-dc");
    let ctxDc : any = canvasDc.getContext('2d');
    if (this.chart2){
        this.chart2.destroy();
    }
    this.chart2 = new Chart(ctxDc, {
        // The type of chart we want to create
        type: this.chart2Tip,
        // The data for our dataset
        data: chart2Data,
        options: this.chart1Options
    });  
  }  


  draw() {

    this.drawStudentiPoGodinama();
    this.drawStudentiPoGodinamaDc();

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
            data: this.statistika.prosjeci.filter(x => x.odsjek == "Automatika i elektronika").map(x => x.prosjek)
          },
          {
            label: "Računarstvo i informatika",
            //backgroundColor: "rgb(255, 99, 132)",
            borderColor: "rgb(255, 99, 132)",
            data: this.statistika.prosjeci.filter(x => x.odsjek == "Računarstvo i informatika").map(x => x.prosjek)
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
                  suggestedMin: 6,    // minimum will be 0, unless there is a lower value.
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
