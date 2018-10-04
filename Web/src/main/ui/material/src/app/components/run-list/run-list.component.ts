import { CHARTCONFIG } from './../../charts/charts.config';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { JobsService } from '../../services/jobs.service';
import { RunService } from '../../services/run.service';
import { ProjectService } from '../../services/project.service';
import { Base } from '../../models/base.model';
import { Handler } from '../dialogs/handler/handler';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import { Subscription } from 'rxjs/Subscription';
import { MatPaginator, MatSort, MatTableDataSource } from "@angular/material";
import { Chart } from 'chart.js';
import 'chartjs-plugin-labels';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-run-list',
  templateUrl: './run-list.component.html',
  styleUrls: ['./run-list.component.scss'],
  providers: [JobsService, RunService, ProjectService]
})
export class RunListComponent implements OnInit {
  id;
  list;
  times;
  totalTimeSaved = 0;
  projectId;
  jobId: string = "";
  title: string = "";
  project: Base = new Base();
  job: Base = new Base();
  showSpinner: boolean = false;
  private _clockSubscription: Subscription;
  displayedColumns: string[] = ['region', 'date', 'passfail', 'success', 'data', 'totaltime', 'bugs', 'bugs2', 'status', 'no'];
  dataSource = null;
  autoSuggestColumns: string[] = ['testSuiteName', 'category', 'endPoint', 'issueDesc', 'suggestion'];
  autoSuggestDS = null;

  config = CHARTCONFIG;
  graph1: Chart = []; // This will hold our chart info
  graph2: Chart = []; // This will hold our chart info
  graph3: Chart = []; // This will hold our chart info
  graph4: Chart = []; // This will hold our chart info
  line1: any;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private jobsService: JobsService, private runService: RunService,
    private projectService: ProjectService, private route: ActivatedRoute,
    private handler: Handler, private datePipe: DatePipe) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['projectId']) {
        this.projectId = params['projectId'];
        this.loadProject(this.projectId);
      }
      if (params['jobId']) {
        this.jobId = params['jobId'];
        this.loadJob(this.jobId);
        this.getRunByJob(this.jobId);
        this.loadSuggestions(this.jobId);
        let timer = Observable.timer(10000, 15000);
        this._clockSubscription = timer.subscribe(t => {
          this.getRunByJob(this.jobId);
        });
      }
    });
  }

  ngOnDestroy(): void {
    this._clockSubscription.unsubscribe();
  }

  loadProject(id: string) {
    this.projectService.getById(id).subscribe(results => {
      if (this.handler.handle(results)) {
        return;
      }
      this.project = results['data'];
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

  loadSuggestions(id: string) {
    this.jobsService.getAutoSuggestions(id).subscribe(results => {
      if (this.handler.handle(results)) {
        return;
      }
      this.autoSuggestDS = new MatTableDataSource(results['data']);
    });
  }

  //Graph Analytics - Start
  getAnalyticsData() {
    let totalData = this.list;
    let totalPass: any = [];
    let totalFail: any = [];
    let totalBytes: any = [];
    let dateTime: any = [];
    let dtDateConvert: any = [];
    let crDate: any = [];
    let totalOpenIssues: any = [];
    let issuesClosed: any = [];
    let issuesLogged: any = [];
    let issuesReopen: any = [];
    let totalTime: any = [];

    for (let i = 0; i < totalData.length; i++) {
      let openIssues: any[] = totalData[i].task.totalOpenIssues;
      totalOpenIssues.push(openIssues);      

      let failed: any[] = totalData[i].task.failedTests;
      totalFail.push(failed);      

      let passed: any[] = totalData[i].task.totalTestCompleted;
      totalPass.push(passed);      

      let dateTimeX: any[] = totalData[i].createdDate;
      dateTime.push(dateTimeX);
      crDate[i] = dateTimeX;
      let dt = new Date(crDate[i]);
      dtDateConvert[i] = this.datePipe.transform(dt, "MMM dd");
     
      let bytes: any[] = totalData[i].task.totalBytes;
      totalBytes.push(bytes);      

      let closed: any[] = totalData[i].task.issuesClosed;
      issuesClosed.push(closed);

      let logged: any[] = totalData[i].task.issuesLogged;
      issuesLogged.push(logged);

      let reopen: any[] = totalData[i].task.issuesReopen;
      issuesReopen.push(reopen);

      let totalTimeArr: any[] = totalData[i].task.totalTime;
      totalTime.push(totalTimeArr);
    }
    totalOpenIssues.reverse();
    totalFail.reverse();
    totalPass.reverse();
    dtDateConvert.reverse();
    totalBytes.reverse();
    issuesClosed.reverse();
    issuesLogged.reverse();
    issuesReopen.reverse();
    totalTime.reverse();

    // Options Start for Graphs
    let graph1Options = {
      responsive: true,
      legend: {
        display: true
      },
      title: {
        display: false,
        text: 'Passed & Failed Analytics'
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
            labelString: 'Pass / Fail'
          }
        }]
      }
    };

    let graph2Options = {
      responsive: true,
      legend: {
        display: true
      },
      title: {
        display: false,
        text: 'Issues Analytics'
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
            labelString: 'Issues'
          }
        }]
      }
    };

    let graph3Options = {
      responsive: true,
      legend: {
        display: true
      },
      title: {
        display: false,
        text: 'Data Analytics'
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
            labelString: 'Data (In Bytes)'
          }
        }]
      }
    };

    let graph4Options = {
      responsive: true,
      legend: {
        display: true
      },
      title: {
        display: false,
        text: 'Time Analytics'
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
            labelString: 'Total Time ( In Milli Seconds )'
          }
        }]
      }
    };

    //Graph 1 Data - Passed Failed
    let graph1Data = {
      labels: dtDateConvert,
      datasets: [
        {
          data: totalPass,
          label: 'Passed',
          borderColor: this.config.success,
          backgroundColor: this.config.success,
          pointBackgroundColor: this.config.lightBlack,
          pointBorderWidth: 1,
          pointBorderColor: "#FFF",
          fill: false,
          pointRadius: 3,
          pointHoverRadius: 4
        },
        {
          data: totalFail,
          label: 'Failed',
          borderColor: this.config.danger,
          backgroundColor: this.config.danger,
          pointBackgroundColor: this.config.lightBlack,
          pointBorderColor: "#FFF",
          pointBorderWidth: 1,
          fill: false,
          pointRadius: 3,
          pointHoverRadius: 4
        }
      ]
    };

    //Graph 2 Data - Issues
    let graph2Data = {
      labels: dtDateConvert,
      datasets: [
        {
          data: issuesClosed,
          label: 'Closed',
          borderColor: this.config.success,
          backgroundColor: this.config.success,
          pointBackgroundColor: this.config.lightBlack,
          pointBorderWidth: 1,
          fill: false,
          pointRadius: 3,
          pointHoverRadius: 4
        },
        {
          data: issuesLogged,
          label: 'Logged',
          borderColor: this.config.danger,
          backgroundColor: this.config.danger,
          pointBackgroundColor: this.config.lightBlack,
          pointBorderWidth: 1,
          fill: false,
          pointRadius: 3,
          pointHoverRadius: 4
        },
        {
          data: totalOpenIssues,
          label: 'Open',
          borderColor: this.config.warning,
          backgroundColor: this.config.warning,
          pointBackgroundColor: this.config.lightBlack,
          pointBorderWidth: 1,
          fill: false,
          pointRadius: 3,
          pointHoverRadius: 4
        }
      ]
    };

    //Graph 3 Data - Bytes
    let graph3Data = {
      labels: dtDateConvert,
      datasets: [
        {
          data: totalBytes,
          label: 'Bytes',
          borderColor: this.config.info,
          backgroundColor: this.config.info,
          pointBackgroundColor: this.config.lightBlack,
          pointBorderWidth: 1,
          fill: false,
          pointRadius: 3,
          pointHoverRadius: 4
        },
      ]
    };

    //Graph 4 Data - Total Time
    let graph4Data = {
      labels: dtDateConvert,
      datasets: [
        {
          data: totalTime,
          label: 'Time',
          borderColor: this.config.infoAlt,
          backgroundColor: this.config.infoAlt,
          pointBackgroundColor: this.config.lightBlack,
          pointBorderWidth: 1,
          fill: false,
          pointRadius: 3,
          pointHoverRadius: 4
        }
      ]
    }
    
    //Graph 1 Start
    this.graph1 = new Chart('canvasGraph1', {
      type: 'line',
      data: graph1Data,
      options: graph1Options
    });

    //Graph 2 Start
    this.graph2 = new Chart('canvasGraph2', {
      type: 'line',
      data: graph2Data,
      options: graph2Options
    });

    //Graph 3 Start
    this.graph3 = new Chart('canvasGraph3', {
      type: 'line',
      data: graph3Data,
      options: graph3Options
    });

    //Graph 4 Start
    this.graph4 = new Chart('canvasGraph4', {
      type: 'line',
      data: graph4Data,
      options: graph4Options
    });
  }
//End Graph

  getRunByJob(id: string) {
    this.handler.activateLoader();
    this.runService.get(id, this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.list = results['data'];
      this.length = results['totalElements'];
      this.dataSource = new MatTableDataSource(this.list);
      this.dataSource.sort = this.sort;
      this.times = 0;
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
  length = 0;
  page = 0;
  pageSize = 10;
  change(evt) {
    // if(this.graph1){
    //     this.graph1.destroy();
    //     //this.graph1.clear();
    // }
    // if(this.graph2){
    //     this.graph2.destroy();
    //     //this.graph2.clear();
    // }
    // if(this.graph3){
    //     this.graph3.destroy();
    //     //this.graph3.clear();
    // }
    // if(this.graph4){
    //     this.graph4.destroy();
    //     //this.graph4.clear();
    // }
      this.page = evt['pageIndex'];
      this.getRunByJob(this.jobId);
  }
}