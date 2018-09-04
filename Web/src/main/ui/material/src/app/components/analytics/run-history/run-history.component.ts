import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { JobsService } from '../../../services/jobs.service';
import { RunService } from '../../../services/run.service';
import { ProjectService } from '../../../services/project.service';
import { Base } from '../../../models/base.model';
import { Run } from '../../../models/run.model';
import { VERSION, MatDialog, MatDialogRef } from '@angular/material';
import { MsgDialogComponent } from '../../dialogs/msg-dialog/msg-dialog.component';
import { Handler } from '../../dialogs/handler/handler';

@Component({
  selector: 'app-run-history',
  templateUrl: './run-history.component.html',
  styleUrls: ['./run-history.component.scss'],
  providers: [JobsService, RunService, ProjectService]
})
export class RunHistoryComponent implements OnInit {

  run:Run = new Run();
  list;
  suites;
  id;
  suiteName = "";
  projectId:string = "";
  jobId:string =  "";
  project: Base = new Base();
  job: Base = new Base();
  showSpinner: boolean = false;
  constructor(private jobsService: JobsService, private runService: RunService, private projectService: ProjectService,
    private route: ActivatedRoute, private dialog: MatDialog, private handler: Handler) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
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
        this.id=params['id'];
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
    this.runService.getTestSuiteResponseHistoryByName(this.jobId, this.suiteName).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.suites = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }


  length = 0;
  page = 0;
  pageSize = 20;
  change(evt) {
    this.page = evt['pageIndex'];
    this.list();
  }
}
