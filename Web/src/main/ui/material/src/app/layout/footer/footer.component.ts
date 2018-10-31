import { Jobs } from './../../models/jobs.model';
import { Project } from './../../models/project.model';
import { ProjectService } from './../../services/project.service';
import { JobsService } from './../../services/jobs.service';
import { Handler } from './../../components/dialogs/handler/handler';
import { Component, OnInit } from '@angular/core';
import { APPCONFIG } from '../../config';
import { TasksService } from './../../services/tasks.service';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";

@Component({
  selector: 'my-app-footer',
  styleUrls: ['./footer.component.scss'],
  templateUrl: './footer.component.html',
  providers: [TasksService, ProjectService, JobsService]
})

export class AppFooterComponent implements OnInit {
  public AppConfig: any;
  tasksRes;
  id: string;
  jobId: string;
  count: number;
  project: Project = new Project();
  job: Jobs = new Jobs();
  constructor(private tasksService: TasksService,
    private handler: Handler,
    private projectService: ProjectService,
    private jobsService: JobsService,
    private route: ActivatedRoute,
    private router: Router) {
  }
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params['entityId'];
      this.jobId = params['entityId'];
      if (this.id) {
        this.loadProject();
      }
      if (this.jobId) {
        this.loadJob();
      }
    });
  }

  loadProject() {
    this.handler.activateLoader();
    this.projectService.getById(this.id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.project = results['data'];
      this.job['project'] = this.project;
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  loadJob() {
    this.handler.activateLoader();
    this.jobsService.getById(this.jobId).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.job = results['data'];
      this.loadProject();
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  length = 0;
  page = 0;
  pageSize = 10;
}
