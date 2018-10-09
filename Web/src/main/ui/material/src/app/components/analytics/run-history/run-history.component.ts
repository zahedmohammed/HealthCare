import { CHARTCONFIG } from './../../../charts/charts.config';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { JobsService } from '../../../services/jobs.service';
import { RunService } from '../../../services/run.service';
import { ProjectService } from '../../../services/project.service';
import { Base } from '../../../models/base.model';
import { Run } from '../../../models/run.model';
import { VERSION, MatDialog, MatDialogRef } from '@angular/material';
import { MsgDialogComponent } from '../../dialogs/msg-dialog/msg-dialog.component';
import { Handler } from '../../dialogs/handler/handler';
import { Chart } from 'chart.js';
import 'chartjs-plugin-labels';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { DatePipe } from '@angular/common';
@Component({
  selector: 'app-run-history',
  templateUrl: './run-history.component.html',
  styleUrls: ['./run-history.component.scss'],
  providers: [JobsService, RunService, ProjectService]
})
export class RunHistoryComponent implements OnInit {

  run: Run = new Run();
  list;
  suites;
  id;
  suiteName = "";
  projectId: string = "";
  jobId: string = "";
  project: Base = new Base();
  job: Base = new Base();
  showSpinner: boolean = false;
  config = CHARTCONFIG;
  // chart = []; // This will hold our chart info
  // chart2 = []; // This will hold our chart info
  // chart3 = [];

    chart:Chart = []; // This will hold our chart info
    chart2:Chart = []; // This will hold our chart info
    chart3:Chart = []; // This will hold our chart info

  displayedColumns: string[] = ['no', 'status', 'region', 'success', 'date/time', 'timeTaken', 'data'];
  dataSource = null;
  

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(private jobsService: JobsService, private runService: RunService, private projectService: ProjectService,
    private route: ActivatedRoute, private dialog: MatDialog, private handler: Handler, private datePipe: DatePipe) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      //console.log(params);
      if (params['jobId']) {
        this.jobId = params['jobId'];
        this.loadJob(this.jobId);
      }
      if (params['suiteId']) {
        this.suiteName = params['suiteId'];
        this.getTestSuiteResponseHistoryByName();
      }
      if (params['projectId']) {
        this.projectId = params['projectId'];
        this.loadProject(this.projectId);
      }
      if (params['id']) {
        this.id = params['id'];
        this.getRunById();

      }
    });
  }
  loadProject(id: string) {
    this.projectService.getById(id).subscribe(results => {
      if (this.handler.handle(results)) {
        return;
      }
      this.project = results['data'];
    });
  }

 
  getRunById() {
    this.handler.activateLoader();
    this.runService.getDetails(this.id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.run = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  loadJob(id: string) {
    this.jobsService.getById(id).subscribe(results => {
      if (this.handler.handle(results)) {
        return;
      }
      this.job = results['data'];
    });
  }

  getTestSuiteResponseHistoryByName() {
    this.handler.activateLoader();
    this.runService.getTestSuiteResponseHistoryByName(this.jobId, this.suiteName, this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.suites = results['data'];
      this.length = results['totalElements'];
      this.dataSource = new MatTableDataSource(this.suites);
      this.dataSource.sort = this.sort;
     
      //Get data - Start
      let totalData = results['data'];
      let tp: any = [];
      let tf: any = [];
      let tb: any = [];
      let rt: any = [];
      let runno: any = [];
      let success: any = [];
      let crDate: any = [];
      let crDateConvert: any = [];
      let temp: any = [];
      for (let i = totalData.length - 1; i >= 0; i--) {
        tp[i] = totalData[i]['totalPassed'];
        tf[i] = totalData[i]['totalFailed'];
        tb[i] = (totalData[i]['totalBytes'] / 1024);
        rt[i] = (totalData[i]['requestTime'] / 1000);
        crDate[i] = totalData[i]['createdDate'];
        let dt = new Date(crDate[i]);
        crDateConvert[i] = this.datePipe.transform(dt,"MMM dd");
        success.push(100 * (tp[i] / (tp[i] + tf[i])));
      }
      //End
      tb.reverse();
      rt.reverse();
      crDateConvert.reverse();

      for (let i = totalData.length - 1; i >= 0; i--) {
        runno.push(totalData[i].runNo);
      }
      // return runno;

      // Options Start for Graphs
      let graph1Options = {
        responsive: true,
        legend: {
          display: true
        },
        title: {
          display: false,
          text: 'Success Statistics'
        },
        scales: {
          xAxes: [{
            display: true,
            scaleLabel: {
              display: true,
              labelString: 'Date'
            }
          }],
          yAxes: [{
            display: true,
            scaleLabel: {
              display: true,
              labelString: 'Success'
            }
          }]
        },
        plugins: {
          datalabels: {
            backgroundColor: function(context) {
              return context.dataset.backgroundColor;
            },
            borderRadius: 5,
            color: 'white',
            datalabels: {
              align: 'end',
              anchor: 'end'
            }
          }
        }
      };

      let graph2Options = {
        legend: {
          display: true
        },
        title: {
          display: false,
          text: 'Data (in Bytes)'
        },
        scales: {
          xAxes: [{
            display: true,
            scaleLabel: {
              display: true,
              labelString: 'Runs'
            }
          }],
          yAxes: [{
            display: true,
            scaleLabel: {
              display: true,
              labelString: 'Data'
            }
          }]
        },
        plugins: {
          datalabels: {
            backgroundColor: function(context) {
              return context.dataset.backgroundColor;
            },
            borderRadius: 5,
            color: 'white',
            datalabels: {
              align: 'end',
              anchor: 'end'
            }
          }
        }
      };

      let graph3Options = {
        legend: {
          display: true
        },
        title: {
          display: false,
          text: 'Time Taken (in Seconds)'
        },
        scales: {
          xAxes: [{
            display: true,
            scaleLabel: {
              display: true,
              labelString: 'Runs'
            }
          }],
          yAxes: [{
            display: true,
            scaleLabel: {
              display: true,
              labelString: 'Time'
            }
          }]
        },
        plugins: {
          datalabels: {
            backgroundColor: function(context) {
              return context.dataset.backgroundColor;
            },
            borderRadius: 5,
            color: 'white',
            datalabels: {
              align: 'end',
              anchor: 'end'
            }
          }
        }
      };
      // Options End

      //Graph 1 Data
      let graph1Data = {
        labels: crDateConvert,
        datasets: [
          {
            data: success,
            label: 'Success (%)',
            borderColor: this.config.success,
            backgroundColor: this.config.success,
            fill: false,
            pointRadius: 4,
            pointHoverRadius: 5,
            pointBorderColor: "rgba(38, 185, 154, 0.7)",
            pointBackgroundColor: "#fff",
            pointHoverBackgroundColor: "#fff",
            pointHoverBorderColor: "rgba(220,220,220,1)",
            pointBorderWidth: 1
          }
        ]
      };

      //Graph 2 Data
      let graph2Data = {
        labels: runno,
        datasets: [
          {
            data: tb,
            label: 'Data',
            borderColor: this.config.primary,
            backgroundColor: this.config.primary,
            fill: true,
            pointRadius: 4,
            pointHoverRadius: 5,
            pointBorderColor: "rgba(38, 185, 154, 0.7)",
            pointBackgroundColor: "#fff",
            pointHoverBackgroundColor: "#fff",
            pointHoverBorderColor: "rgba(220,220,220,1)",
            pointBorderWidth: 1
          }
        ]
      };

      //Graph 3 Data
      let graph3Data = {
        labels: runno,
        datasets: [
          {
            data: rt,
            label: 'Time Taken',
            borderColor: this.config.warning,
            backgroundColor: this.config.warning,
            fill: true,
            pointRadius: 4,
            pointHoverRadius: 5,
            pointBorderColor: "rgba(38, 185, 154, 0.7)",
            pointBackgroundColor: "#fff",
            pointHoverBackgroundColor: "#fff",
            pointHoverBorderColor: "rgba(220,220,220,1)",
            pointBorderWidth: 1
          }
        ]
      };

      //Graph 1 Start
      this.chart = new Chart('canvas1', {
        type: 'line',
        data: graph1Data,
        options: graph1Options
      });
      //Graph 2 Start
      this.chart2 = new Chart('canvas2', {
        type: 'line',
        data: graph2Data,
        options: graph2Options
      });
      //Graph 3 Start
      this.chart3 = new Chart('canvas3', {
        type: 'line',
        data: graph3Data,
        options: graph3Options
      });
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  length = 0;
  page = 0;
  pageSize = 10;
  pageSizeOptions: number[] = [20, 50, 100];

  change(evt) {
      if(this.chart){
          this.chart.destroy()
      }
      if(this.chart2){
          this.chart2.destroy()
      }
      if(this.chart3){
          this.chart3.destroy()
      }
 
      this.page = evt['pageIndex'];
      this.pageSize = evt.pageSize;
      this.getTestSuiteResponseHistoryByName();
  }
}
