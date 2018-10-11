import { Component, OnInit } from '@angular/core';
import {TestSuiteService} from "../../../services/test-suite.service";
import {ProjectService} from "../../../services/project.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Handler} from "../../dialogs/handler/handler";
import {Project} from "../../../models/project.model";

@Component({
  selector: 'app-job-analytics',
  templateUrl: './job-analytics.component.html',
  styleUrls: ['./job-analytics.component.scss'],
  providers: [ProjectService, TestSuiteService]

})
export class JobAnalyticsComponent implements OnInit {
    id; // project id
    project: Project = new Project();
    constructor(private testSuiteService: TestSuiteService, private projectService: ProjectService,
                private route: ActivatedRoute, private router: Router, private handler: Handler) { }


    ngOnInit() {
        this.handler.activateLoader();
        this.route.params.subscribe(params => {
            this.id = params['id'];
            //console.log(params);
            this.loadProject(this.id);
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
}
