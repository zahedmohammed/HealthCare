import { ChartPieComponent } from './../../../charts/pie/pie.component';
import { CHARTCONFIG } from './../../../charts/charts.config';
import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { TestSuiteService } from '../../../services/test-suite.service';
import { Handler } from '../../dialogs/handler/handler';
import { Coverage } from '../../../models/coverage.model';
import { Project } from '../../../models/project.model';

import { ProjectService } from '../../../services/project.service';
import { Base } from '../../../models/base.model';
import { Subscription } from 'rxjs/Subscription';
import { Chart } from 'chart.js';
import { count } from 'rxjs/operator/count';
@Component({
  selector: 'app-api-coverage',
  templateUrl: './api-coverage.component.html',
  styleUrls: ['./api-coverage.component.scss'],
  providers: [ProjectService, TestSuiteService]
})
export class ApiCoverageComponent implements OnInit {

    id; // project id
    coverage: Coverage = new Coverage();
    project: Project = new Project();
    config = CHARTCONFIG;
    categoryDonutChart;
    byCatagory = []; // This will hold our chart info
    byMethod = []; // This will hold our chart info
    bySeverity = []; // This will hold our chart info
    //pie1:Object


  constructor(private testSuiteService: TestSuiteService, private projectService: ProjectService,
        private route: ActivatedRoute, private router: Router, private handler: Handler) { }

        ngOnInit() {
    
    this.handler.activateLoader();
    this.route.params.subscribe(params => {
      this.id = params['id'];
      //console.log(params);
      this.loadProject(this.id);
        this.getCoverage();
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

getCoverage(){
    this.handler.activateLoader();
    this.testSuiteService.getApiCoverage(this.id).subscribe(results => {
      //console.log('results - ',results);
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.coverage = results['data'];
      
      //Get data for ByCategory - Start
      let countbycat = results['data'].countByCategory;
      let groupby:any = [];
      let count1:any = [];
      for ( let i of countbycat){
      groupby.push(i['groupBy']);
      count1.push(i['count']);
      }
      //End

      //Get data for ByMethod - Start
      let countbyMethod = results['data'].countByMethod;
      let groupByM: any = [];
      let countByM: any = [];
      for(let i of countbyMethod){
        groupByM.push(i['groupBy']);
        countByM.push(i['count']);
      }
      //End

      //Get data for BySeverity - Start
      let countbySeverity = results['data'].countBySeverity;
      let groupByS: any = [];
      let countByS: any = [];
      for(let i of countbySeverity){
        groupByS.push(i['groupBy']);
        countByS.push(i['count']);
      }
      //End

      //Graph 1 Start
      this.byCatagory = new Chart('canvas1',{
        type:'doughnut',
        data:{
          labels:groupby,
          datasets:[
            {
              data:count1,
              backgroundColor:[
                'rgba(255, 99, 132,2)',
                'rgba(54, 206, 86, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 192, 192, 1)',
                'rgba(201, 196, 98, 1)',
                'rgba(153, 201, 98, 1)',
                'rgba(98, 201, 102, 1)',
                'rgba(98, 201, 133, 1)',
                'rgba(98, 185, 201, 1)',
                'rgba(98, 136, 201, 1)',
                'rgba(105, 98, 201, 1)',
                'rgba(153, 98, 201, 1)',
                'rgba(201, 98, 198, 1)',
                'rgba(201, 98, 135, 1)'
              ],
            }
          ]
        },
        options:{
          legend:{
            display:true
          },
          title:{
            text:'Count By Method',
            display:false
          },
          animation: {
            duration: 1000,
            easing: 'linear'
          }

        }
      });

      //Graph 2 Start
      this.byMethod = new Chart('canvas2',{
        type:'pie',
        data:{
          labels:groupByM,
          datasets:[
            {
              data:countByM,
              backgroundColor:[
                'rgba(255, 99, 132,2)',
                'rgba(54, 206, 86, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 192, 192, 1)',
                'rgba(201, 196, 98, 1)',
                'rgba(153, 201, 98, 1)',
                'rgba(98, 201, 102, 1)',
                'rgba(98, 201, 133, 1)',
                'rgba(98, 185, 201, 1)',
                'rgba(98, 136, 201, 1)',
                'rgba(105, 98, 201, 1)',
                'rgba(153, 98, 201, 1)',
                'rgba(201, 98, 198, 1)',
                'rgba(201, 98, 135, 1)'
              ],
            }
          ]
        },
        options:{
          legend:{
            display:true
          },
          title:{
            text:'Count By Category',
            display:false
          },
          animation: {
            duration: 1000,
            easing: 'linear'
          }

        }
      });

      //Graph 3 Start
      this.bySeverity = new Chart('canvas3',{
        type:'pie',
        data:{
          labels:groupByS,
          datasets:[
            {
              data:countByS,
              backgroundColor:[
                'rgba(255, 99, 132,2)',
                'rgba(54, 206, 86, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 192, 192, 1)',
                'rgba(201, 196, 98, 1)',
                'rgba(153, 201, 98, 1)',
                'rgba(98, 201, 102, 1)',
                'rgba(98, 201, 133, 1)',
                'rgba(98, 185, 201, 1)',
                'rgba(98, 136, 201, 1)',
                'rgba(105, 98, 201, 1)',
                'rgba(153, 98, 201, 1)',
                'rgba(201, 98, 198, 1)',
                'rgba(201, 98, 135, 1)'
              ],
            }
          ]
        },
        options:{
          legend:{
            display:true
          },
          title:{
            text:'Count By Severity',
            display:false
          },
          animation: {
            duration: 1000,
            easing: 'linear'
          }

        }
      });
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
}

  loadProject(id: string) {
    this.handler.activateLoader();
    this.projectService.getById(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.project = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
}
