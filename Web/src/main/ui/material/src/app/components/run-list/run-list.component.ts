import { Component, OnInit, ViewChild } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { JobsService } from '../../services/jobs.service';
import { RunService } from '../../services/run.service';
import { ProjectService } from '../../services/project.service';
import { Base } from '../../models/base.model';
import { Handler } from '../dialogs/handler/handler';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import { Subscription } from 'rxjs/Subscription';


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
  jobId:string =  "";
  title:string = "";
  project: Base = new Base();
  job: Base = new Base();
  showSpinner: boolean = false;
  private _clockSubscription: Subscription;
  constructor(private jobsService: JobsService, private runService: RunService, private projectService: ProjectService, private route: ActivatedRoute, private handler: Handler) {

  }

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

  getRunByJob(id: string) {
    this.handler.activateLoader();
    this.runService.get(id, this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.list = results['data'];
      this.length = results['totalElements'];
       this.times = 0;
       for (var  i = 0; i < this.list.length; i++){
           this.times += this.list[i].task.timeSaved;
        }
       this.totalTimeSaved = this.times;
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
    this.getRunByJob(this.jobId);
  }

}