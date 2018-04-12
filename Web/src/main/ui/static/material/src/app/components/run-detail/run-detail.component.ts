import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { JobsService } from '../../services/jobs.service';
import { RunService } from '../../services/run.service';
import { ProjectService } from '../../services/project.service';
import { Base } from '../../models/base.model';
import { Run } from '../../models/run.model';
import {VERSION, MatDialog, MatDialogRef} from '@angular/material';
import { MsgDialogComponent } from '../dialogs/msg-dialog/msg-dialog.component';


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
    private route: ActivatedRoute, private dialog: MatDialog) { }

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
        if (!results)
            return;
        this.project = results['data'];
    });
  }

  loadJob(id: string) {
    this.jobsService.getById(id).subscribe(results => {
        if (!results)
            return;
        this.job = results['data'];
    });
  }

  calSum() {
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
    this.showSpinner = true;
    this.runService.getDetails(this.id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.run = results['data'];
    }, error => {
      console.log("Unable to fetch regions");
    });
  }

  getTestSuiteResponsesByRunId() {
    this.showSpinner = true;
    this.runService.getTestSuiteResponses(this.id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.list = results['data'];
    }, error => {
      console.log("Unable to fetch regions");
    });
  }

  getSummary() {
    this.showSpinner = true;
    this.runService.getSummary(this.id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.suites = results['data'];
      this.calSum();
    }, error => {
      console.log("Unable to fetch regions");
    });
  }

  getTestSuiteResponseByName(name: string) {
    this.showSpinner = true;
    this.runService.getTestSuiteResponseByName(this.id, name).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
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
      console.log("Unable to fetch regions");
    });
  }

  showDialog(msg) {
    this.dialog.open(MsgDialogComponent, {
        width:'100%',
        height:'90%',
        data: msg
    });
  }


}
