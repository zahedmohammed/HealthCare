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
import 'chartjs-plugin-labels';
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
              backgroundColor: this.config.backgroundColor
            }
          ]
        },
        options:{
          legend:{
            display:true,
            position:'left'
          },
          title:{
            text:'Test Suite Categories',
            display:true
          },
          animation: {
            duration: 1000,
            animateScale:true,
            easing: 'linear'
          },
          plugins: {
            labels:[
              {
              fontSize: 11,
              render: 'value',
              fontColor: '#000',
              fontStyle: 'bold',
              textShadow: true,
              overlap: true,
              arc: true
            }
          ]
        }
        }
      });

      //Graph 2 Start
      this.byMethod = new Chart('canvas2',{
        type:'doughnut',
        data:{
          labels:groupByM,
          datasets:[
            {
              data:countByM,
              backgroundColor: this.config.backgroundColor
            }
          ]
        },
        options:{
          legend:{
            display:true,
            position:'left'
          },
          title:{
            text:'HTTP Methods',
            display:true
          },
          animation: {
            duration: 1000,
            easing: 'linear'
          },
          plugins: {
            labels:[
              {
              fontSize: 11,
              render: 'value',
              fontColor: '#000',
              fontStyle: 'bold',
              textShadow: true,
              overlap: true,
              arc: true
            }
          ]
        }
        }
      });

      //Graph 3 Start
      this.bySeverity = new Chart('canvas3',{
        type:'doughnut',
        data:{
          labels:groupByS,
          datasets:[
            {
              data:countByS,
              backgroundColor: this.config.backgroundColor
            }
          ]
        },
        options:{
          legend:{
            display:true,
            position:'left'
          },
          title:{
            text:'Test Suite Severities',
            display:true
          },
          animation: {
            duration: 1000,
            easing: 'linear'
          },
          plugins: {
            labels:[
              {
              fontSize: 11,
              render: 'value',
              fontColor: '#000',
              fontStyle: 'bold',
              textShadow: true,
              overlap: true,
              arc: true
            }
          ]
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
