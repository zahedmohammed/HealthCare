import { Component, OnInit, ViewChild } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { TestSuiteService } from '../../../services/test-suite.service';
import { RunService } from '../../../services/run.service';
import { ProjectService } from '../../../services/project.service';
import { Base } from '../../../models/base.model';
//import { MatSort, MatSortable, MatTableDataSource } from '@angular/material';
import { Handler } from '../../dialogs/handler/handler';
import { VERSION, MatDialog, MatDialogRef } from '@angular/material';
import { AdvRunComponent } from '../../dialogs/adv-run/adv-run.component';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import { Subscription } from 'rxjs/Subscription';

@Component({
selector: 'app-test-suite-list',
  templateUrl: './test-suite-list.component.html',
  styleUrls: ['./test-suite-list.component.scss'],
  providers: [TestSuiteService, RunService, ProjectService]
})
export class TestSuiteListComponent implements OnInit {

  id; // project id
  testsuites;
  projectId: string = "";
  project: Base = new Base();
  showSpinner: boolean = false;
  //private _clockSubscription: Subscription;

  constructor(private testSuiteService: TestSuiteService, private runService: RunService, private dialog: MatDialog,
    private projectService: ProjectService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.handler.activateLoader();
    this.route.params.subscribe(params => {
      this.id = params['id'];
      console.log(params);
      this.loadProject(this.id);
      this.list(this.id);
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

    this.testSuiteService.getById(id, this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.testsuites = results['data'];
      this.length = results['totalElements'];
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
    this.list(this.id);
  }

}