import { Component, OnInit, ViewChild } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { JobsService } from '../../services/jobs.service';
import { RunService } from '../../services/run.service';
import { ProjectService } from '../../services/project.service';
import { Base } from '../../models/base.model';


@Component({
  selector: 'app-run-list',
  templateUrl: './run-list.component.html',
  styleUrls: ['./run-list.component.scss'],
  providers: [JobsService, RunService, ProjectService]
})
export class RunListComponent implements OnInit {
  list;
  projectId:string = "";
  jobId:string =  "";
  title:string = "";
  project: Base = new Base();
  job: Base = new Base();
  showSpinner: boolean = false;
  constructor(private jobsService: JobsService, private runService: RunService, private projectService: ProjectService, private route: ActivatedRoute) {

  }

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
        this.getRunByJob(this.jobId);
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

  getRunByJob(id: string) {
    this.showSpinner = true;
    this.runService.get(id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.list = results['data'];
    }, error => {
      console.log("Unable to fetch regions");
      alert(error);
    });
  }

}