import { Component, OnInit, ViewChild } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { JobsService } from '../../services/jobs.service';
import { RunService } from '../../services/run.service';
import { ProjectService } from '../../services/project.service';
import { Base } from '../../models/base.model';
//import { MatSort, MatSortable, MatTableDataSource } from '@angular/material';
import { Handler } from '../dialogs/handler/handler';


@Component({
  selector: 'app-jobs-list',
  templateUrl: './jobs-list.component.html',
  styleUrls: ['./jobs-list.component.scss'],
  providers: [JobsService, RunService, ProjectService]
})
export class JobslistComponent implements OnInit {

  jobs;
  projectId: string = "";
  project: Base = new Base();
  showSpinner: boolean = false;
  constructor(private jobsService: JobsService, private runService: RunService, private projectService: ProjectService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {   
    this.handler.activateLoader();
    this.route.params.subscribe(params => {
      console.log(params);
      //if (params['id']) {
        //this.projectId = params['id'];
        this.list();
        //this.loadProject(this.projectId);
      //}
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

  list() {
    this.handler.activateLoader();
    this.jobsService.get().subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.jobs = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  runJob(id: string) {
    this.handler.activateLoader();
    this.runService.run(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      //this.jobs = results['data'];
      this.router.navigate(['/app/jobs/' , id, 'runs']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
}
