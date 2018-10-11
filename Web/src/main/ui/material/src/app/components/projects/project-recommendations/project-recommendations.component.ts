import { Component, OnInit } from '@angular/core';
import {TestSuiteService} from "../../../services/test-suite.service";
import {Project} from "../../../models/project.model";
import {ProjectService} from "../../../services/project.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Handler} from "../../dialogs/handler/handler";
import { MatPaginator, MatSort, MatTableDataSource } from "@angular/material";

@Component({
  selector: 'app-project-recommendations',
  templateUrl: './project-recommendations.component.html',
  styleUrls: ['./project-recommendations.component.scss'],
  providers: [ProjectService, TestSuiteService]

})
export class ProjectRecommendationsComponent implements OnInit {
    id; // project id
    project: Project = new Project();

    autoSuggestDS = null;
    autoSuggest: any;

    constructor(private testSuiteService: TestSuiteService, private projectService: ProjectService,
                private route: ActivatedRoute, private router: Router, private handler: Handler) { }


  ngOnInit() {
      this.handler.activateLoader();
      this.route.params.subscribe(params => {
          this.id = params['id'];
          //console.log(params);
          this.loadProject(this.id);
          this.loadSuggestions(this.id);

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

loadSuggestions(id: string) {
    this.projectService.getAutoSuggestions(id).subscribe(results => {
      if (this.handler.handle(results)) {
        return;
      }
      this.autoSuggestDS = new MatTableDataSource(results['data']);
      this.autoSuggest = results['data'];
    });
  }

  openSkipDialog(obj){
    var skip = confirm("Are you sure you want to skip this endpoint from '"+obj.category+"' testing?");
    if (skip == true) {
        // invoke skip service
        this.projectService.skipAutoSuggestion(this.id, obj.testSuiteName,obj.testCaseNumber).subscribe(results => {
          if (this.handler.handle(results)) {
            return;
          }
          this.loadSuggestions(this.id);
        });

    }
  }

}
