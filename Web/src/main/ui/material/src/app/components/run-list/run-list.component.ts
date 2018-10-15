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
  displayedColumns: string[] = ['region', 'date', 'data','totaltime','passfail', 'bugs', 'bugs2','success','status', 'no'];
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
    var skip = confirm("Are you sure you want to skip this endpoint from '"+obj.category+"' testing?");
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
  //
  // dynamicColors(){
  //   let r = Math.floor(Math.random() * 255);
  //   let g = Math.floor(Math.random() * 255);
  //   let b = Math.floor(Math.random() * 255);
  //   return "rgba(" + r + "," + g + "," + b + ", 0.5)";
  // }
  //
  // poolColors(a) {
  //   var pool = [];
  //   for(let i = 0; i < a; i++){
  //       pool.push(this.dynamicColors());
  //     }
  //   return pool;
  // }

 
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
      this.page = evt['pageIndex'];
      this.getRunByJob(this.jobId);
  }
}