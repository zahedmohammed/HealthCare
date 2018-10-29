import { Component, OnInit, ViewChild } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { JobsService } from '../../../services/jobs.service';
import { RunService } from '../../../services/run.service';
import { ProjectService } from '../../../services/project.service';
import { Base } from '../../../models/base.model';
//import { MatSort, MatSortable, MatTableDataSource } from '@angular/material';
import { Handler } from '../../dialogs/handler/handler';
import { VERSION, MatDialog, MatDialogRef, MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { AdvRunComponent } from '../../dialogs/adv-run/adv-run.component';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import { Subscription } from 'rxjs/Subscription';
import { MavenIntegrationComponent } from '../../dialogs/maven-integration/maven-integration.component';
import { GradleIntegrationComponent } from '../../dialogs/gradle-integration/gradle-integration.component';
import { JenkinsIntegrationComponent } from '../../dialogs/jenkins-integration/jenkins-integration.component';
import { Issue } from '../../../models/issue.model';
import { IssuesService } from './../../../services/issues.service';

@Component({
  selector: 'app-issue-list',
  templateUrl: './issue-list.component.html',
  styleUrls: ['./issue-list.component.scss'],
  providers: [JobsService, RunService, ProjectService, IssuesService]
})
export class IssuelistComponent implements OnInit {

  id; // project id
  jobs;
  projectId: string = "";
  project: Base = new Base();
  issue = [];
  showSpinner: boolean = false;
  //private _clockSubscription: Subscription;
  notificationFlag: boolean;
  autoBugMngmnt: boolean = false;

  displayedColumns: string[] = ['name', 'endPoint', 'method'];
  dataSource = null;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private IssuesService: IssuesService, private jobsService: JobsService, private runService: RunService, private dialog: MatDialog,
    private projectService: ProjectService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.handler.activateLoader();
    this.route.params.subscribe(params => {
      this.id = params['id'];
      console.log("this.id",this.id)
      console.log(params);
      this.loadProject(this.id);
      console.log(this.list(this.id));
      this.list(this.id);
      // this.notificationFlag = false;
    });
  }

  // ngAfterViewInit(){
  //   this.list(this.id);
  // }

  ngOnDestroy(): void {
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
    this.IssuesService.getIssue(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.issue = results['data'];
      console.log('checking data', this.issue);
      this.dataSource = new MatTableDataSource(this.issue);
      this.dataSource.sort = this.sort
      this.length = results['totalElements'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  length = 0;
  page = 0;
  pageSize = 10;
  change(evt) {
    this.page = evt['pageIndex'];
    this.list(this.id);
  }
}
