import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { JobsService } from '../../services/jobs.service';
import { RunService } from '../../services/run.service';
import { ProjectService } from '../../services/project.service';
import { Base } from '../../models/base.model';
import { Run } from '../../models/run.model';
import {VERSION, MatDialog, MatDialogRef} from '@angular/material';
import { MsgDialogComponent } from '../dialogs/msg-dialog/msg-dialog.component';
import { Handler } from '../dialogs/handler/handler';


@Component({
  selector: 'app-run-detail',
  templateUrl: './run-detail.component.html',
  styleUrls: ['./run-detail.component.scss'],
  providers: [JobsService, RunService, ProjectService]
})
export class RunDetailComponent implements OnInit {
  run:Run = new Run();
  list;
  suites;
  id;
  projectId:string = "";
  jobId:string =  "";
  total = 0;
  failed = 0;
  size = 0;
  time = 0;
  success = 0;
  project: Base = new Base();
  job: Base = new Base();
  showSpinner: boolean = false;
  constructor(private jobsService: JobsService, private runService: RunService, private projectService: ProjectService,
    private route: ActivatedRoute, private dialog: MatDialog, private handler: Handler) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
       this.projectId = params['id'];
       this.loadProject(this.projectId);
      }
      if (params['jobId']) {
        this.jobId = params['jobId'];
        this.loadJob(this.jobId);
      }
      if (params['runId']) {
        this.id = params['runId'];
        this.getRunById();
        this.getSummary();
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

  loadJob(id: string) {
    this.jobsService.getById(id).subscribe(results => {
      if (this.handler.handle(results)) {
        return;
      }
      this.job = results['data'];
    });
  }

  calSum() {
    this.total = 0;
    this.failed = 0;
    this.size = 0;
    this.time = 0;
    this.success = 0;

    for(var i = 0; i < this.suites.length; i++){
        this.total += this.suites[i].tests;
        this.failed += this.suites[i].failed;
        this.size += this.suites[i].size;
        this.time += this.suites[i].time;
    }
    this.success = ( (this.total - this.failed) * 100 ) / this.total;
    this.success = Math.floor(this.success);
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

  getTestSuiteResponsesByRunId() {
    this.handler.activateLoader();
    this.runService.getTestSuiteResponses(this.id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.list = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getSummary() {
    this.handler.activateLoader();
    this.runService.getSummary(this.id, this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.suites = results['data'];
      this.length = results['totalElements'];
      this.calSum();
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getTestSuiteResponseByName(name: string) {
    this.handler.activateLoader();
    this.runService.getTestSuiteResponseByName(this.id, name).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.list = results['data'];
      var arrayLength = this.list.length;
      var msg = '';
      for (var i = 0; i < arrayLength; i++) {
        //alert(this.list[i]);
        msg += this.list[i].logs;
        //Do something
      }
      this.showDialog(msg);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  showDialog(msg) {
    this.dialog.open(MsgDialogComponent, {
        width:'100%',
        height:'90%',
        data: msg
    });
  }

  length = 0;
  page = 0;
  pageSize = 100;
  change(evt) {
    this.page = evt['pageIndex'];
    this.getSummary();
  }


}
