import { Component, OnInit } from '@angular/core';
import {TestSuiteService} from "../../../services/test-suite.service";
import {ProjectService} from "../../../services/project.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Handler} from "../../dialogs/handler/handler";
import {Project} from "../../../models/project.model";
import {MatTableDataSource} from "@angular/material";
import {JobsService} from "../../../services/jobs.service";

@Component({
  selector: 'app-job-analytics',
  templateUrl: './job-analytics.component.html',
  styleUrls: ['./job-analytics.component.scss'],
  providers: [ProjectService, JobsService,TestSuiteService]

})
export class JobAnalyticsComponent implements OnInit {
    id; // project id
    jobs;
    project: Project = new Project();
    constructor(private testSuiteService: TestSuiteService, private projectService: ProjectService,private jobsService: JobsService,
                private route: ActivatedRoute, private router: Router, private handler: Handler) { }


    ngOnInit() {
        this.handler.activateLoader();
        this.route.params.subscribe(params => {
            this.id = params['id'];
            //console.log(params);
            this.loadProject(this.id);
            this.list(this.id);
        }, error => {
            this.handler.hideLoader();
            this.handler.error(error);
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
    list(id: string) {
        this.handler.activateLoader();
        this.jobsService.getJobs(id, 0, 10).subscribe(results => {
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
}
