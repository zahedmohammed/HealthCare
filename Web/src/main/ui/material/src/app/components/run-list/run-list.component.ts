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
import 'chartjs-plugin-datalabels';
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
  autoSuggest: any;

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
      this.autoSuggest = results['data'];
    });
  }

  openSkipDialog(obj){
    var skip = confirm("Are you sure you want to skip this suggestion for all tests?");
    if (skip == true) {
        // invoke skip service
        this.jobsService.skipAutoSuggestion(this.jobId, obj.testSuiteName,obj.testCaseNumber).subscribe(results => {
          if (this.handler.handle(results)) {
            return;
          }
          this.loadSuggestions(this.jobId);
        });

    }
  }

  dynamicColors(){
    let r = Math.floor(Math.random() * 255);
    let g = Math.floor(Math.random() * 255);
    let b = Math.floor(Math.random() * 255);
    return "rgba(" + r + "," + g + "," + b + ", 0.5)";
  }

  poolColors(a) {
    var pool = [];
    for(let i = 0; i < a; i++){
        pool.push(this.dynamicColors());
      }
    return pool;
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
    let runId: any = [];

    for (let i = 0; i < totalData.length; i++) {
      let rid: any[] = totalData[i].runId;
      runId.push(rid);

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
     
      let bytes = totalData[i].task.totalBytes / 1024;
      bytes = Math.round(bytes);
      totalBytes.push(bytes);      

      let closed: any[] = totalData[i].task.issuesClosed;
      issuesClosed.push(closed);

      let logged: any[] = totalData[i].task.issuesLogged;
      issuesLogged.push(logged);

      let reopen: any[] = totalData[i].task.issuesReopen;
      issuesReopen.push(reopen);

      let totalTimeArr = totalData[i].task.totalTime / 1000;
      totalTimeArr = Math.round(totalTimeArr);
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
    runId.reverse();

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
            labelString: 'Run No'
          }
        }],
        yAxes: [{
          display: true,
          scaleLabel: {
            display: true,
            labelString: 'Pass / Fail'
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
            labelString: 'Run No'
          }
        }],
        yAxes: [{
          display: true,
          scaleLabel: {
            display: true,
            labelString: 'Issues'
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
            labelString: 'Run No'
          }
        }],
        yAxes: [{
          display: true,
          scaleLabel: {
            display: true,
            labelString: 'Data ( In KBs )'
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
            labelString: 'Run No'
          }
        }],
        yAxes: [{
          display: true,
          scaleLabel: {
            display: true,
            labelString: 'Total Time ( In Seconds )'
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

    //Graph 1 Data - Passed Failed
    let graph1Data = {
      labels: runId,
      datasets: [
        {
          data: totalPass,
          label: 'Passed',
          backgroundColor: this.config.success,
          borderColor: this.config.success,
          pointBorderColor: this.config.pointborderColor,
          pointBackgroundColor: this.config.whiteColor,
          pointHoverBackgroundColor: this.config.whiteColor,
          pointHoverBorderColor: this.config.pointhoverBorderColor,
          pointBorderWidth: 1,          
          fill: false,
          pointRadius: 3,
          pointHoverRadius: 4,
          datalabels: {
						align: 'top',
						anchor: 'top'
					}
        },
        {
          data: totalFail,
          label: 'Failed',
          backgroundColor: this.config.danger,
          borderColor: this.config.danger,
          pointBorderColor: this.config.pointborderColor,
          pointBackgroundColor: this.config.whiteColor,
          pointHoverBackgroundColor: this.config.whiteColor,
          pointHoverBorderColor: this.config.pointhoverBorderColor,
          pointBorderWidth: 1,         
          fill: false,
          pointRadius: 3,
          pointHoverRadius: 4,
          datalabels: {
						align: 'bottom',
						anchor: 'bottom'
					}
        }
      ]
    };

    //Graph 2 Data - Issues
    let graph2Data = {
      labels: runId,
      datasets: [
        {
          data: issuesClosed,
          label: 'Closed',
          borderColor: this.config.success,
          backgroundColor: this.config.success,
          pointBorderColor: this.config.pointborderColor,
          pointBackgroundColor: this.config.whiteColor,
          pointHoverBackgroundColor: this.config.whiteColor,
          pointHoverBorderColor: this.config.pointhoverBorderColor,
          pointBorderWidth: 1,
          fill: false,
          pointRadius: 3,
          pointHoverRadius: 4,
          datalabels: {
						align: 'top',
						anchor: 'top'
					}
        },
        {
          data: issuesLogged,
          label: 'New',
          borderColor: this.config.danger,
          backgroundColor: this.config.danger,
          pointBorderColor: this.config.pointborderColor,
          pointBackgroundColor: this.config.whiteColor,
          pointHoverBackgroundColor: this.config.whiteColor,
          pointHoverBorderColor: this.config.pointhoverBorderColor,
          pointBorderWidth: 1,
          fill: false,
          pointRadius: 3,
          pointHoverRadius: 4,
          datalabels: {
						align: 'center',
						anchor: 'center'
					}
        },
        {
          data: totalOpenIssues,
          label: 'Open',
          borderColor: this.config.warning,
          backgroundColor: this.config.warning,
          pointBorderColor: this.config.pointborderColor,
          pointBackgroundColor: this.config.whiteColor,
          pointHoverBackgroundColor: this.config.whiteColor,
          pointHoverBorderColor: this.config.pointhoverBorderColor,
          pointBorderWidth: 1,
          fill: false,
          pointRadius: 3,
          pointHoverRadius: 4,
          datalabels: {
						align: 'bottom',
						anchor: 'bottom'
					}
        }
      ]
    };

    //Graph 3 Data - KiloBytes
    let graph3Data = {
      labels: runId,
      datasets: [
        {
          data: totalBytes,
          label: 'Data (In KBs)',
          borderColor: this.config.info,
          backgroundColor: this.config.info,
          pointBorderColor: this.config.pointborderColor,
          pointBackgroundColor: this.config.whiteColor,
          pointHoverBackgroundColor: this.config.whiteColor,
          pointHoverBorderColor: this.config.pointhoverBorderColor,
          pointBorderWidth: 1,
          fill: false,
          pointRadius: 3,
          pointHoverRadius: 4,
          datalabels: {
						align: 'top',
						anchor: 'top'
					}
        },
      ]
    };

    //Graph 4 Data - Total Time
    let graph4Data = {
      labels: runId,
      datasets: [
        {
          data: totalTime,
          label: 'Time (In Seconds)',
          borderColor: this.config.infoAlt,
          backgroundColor: this.config.infoAlt,
          pointBorderColor: this.config.pointborderColor,
          pointBackgroundColor: this.config.whiteColor,
          pointHoverBackgroundColor: this.config.whiteColor,
          pointHoverBorderColor: this.config.pointhoverBorderColor,
          pointBorderWidth: 1,
          fill: false,
          pointRadius: 3,
          pointHoverRadius: 4,
          datalabels: {
						align: 'top',
						anchor: 'top'
					}
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
    // console.log(this.graph1.length);
    // if(this.graph1 != undefined || this.graph1 != null){
    //   console.log("this.graph1", this.graph1)
    //     this.graph1.destroy();
    // }
    // if(this.graph2 !== undefined || this.graph1 !== null){
    //   console.log("this.graph2", this.graph2)
    //     this.graph2.destroy();
    // }
    // if(this.graph3 !== undefined || this.graph1 !== null){
    //   console.log("this.graph3", this.graph3)
    //     this.graph3.destroy();
    // }
    // if(this.graph4 !== undefined || this.graph1 !== null){
    //   console.log("this.graph4", this.graph4)
    //     this.graph4.destroy();
    // }
      this.page = evt['pageIndex'];
      this.getRunByJob(this.jobId);
  }
}