import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { TestSuiteService } from '../../../services/test-suite.service';
import { Handler } from '../../dialogs/handler/handler';
import { Coverage } from '../../../models/coverage.model';
import { Project } from '../../../models/project.model';

import { ProjectService } from '../../../services/project.service';
import { Base } from '../../../models/base.model';
import { Subscription } from 'rxjs/Subscription';


@Component({
  selector: 'app-api-coverage',
  templateUrl: './api-coverage.component.html',
  styleUrls: ['./api-coverage.component.scss'],
  providers: [ProjectService, TestSuiteService]
})
export class ApiCoverageComponent implements OnInit {

    id; // project id
    coverage: Coverage = new Coverage();
    project: Project = new Project();

  constructor(private testSuiteService: TestSuiteService, private projectService: ProjectService,
        private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.handler.activateLoader();
    this.route.params.subscribe(params => {
      this.id = params['id'];
      console.log(params);
      this.loadProject(this.id);
        this.getCoverage();
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

getCoverage(){
    this.handler.activateLoader();
    this.testSuiteService.getApiCoverage(this.id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.coverage = results['data'];
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
