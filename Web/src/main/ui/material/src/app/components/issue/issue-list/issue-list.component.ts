import { Component, OnInit, ViewChild } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { JobsService } from '../../../services/jobs.service';
import { RunService } from '../../../services/run.service';
import { ProjectService } from '../../../services/project.service';
import { Base } from '../../../models/base.model';
//import { MatSort, MatSortable, MatTableDataSource } from '@angular/material';
import { Handler } from '../../dialogs/handler/handler';
import { VERSION, MatDialog, MatDialogRef, MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { Observable ,  Subscription } from 'rxjs';
import { IssuesService } from './../../../services/issues.service';

@Component({
  selector: 'app-issue-list',
  templateUrl: './issue-list.component.html',
  styleUrls: ['./issue-list.component.scss'],
  providers: [JobsService, RunService, ProjectService, IssuesService]
})
export class IssuelistComponent implements OnInit {

  id; // project id
  issueId: string;
  jobs;
  projectId: string = "";
  project: Base = new Base();
  issues = [];
  showSpinner: boolean = false;
  notificationFlag: boolean;
  autoBugMngmnt: boolean = false;

  displayedColumns: string[] = ['env', 'endPoint', 'method', 'result'];
  dataSource = null;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private IssuesService: IssuesService, private jobsService: JobsService, private runService: RunService, private dialog: MatDialog,
    private projectService: ProjectService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.handler.activateLoader();
    this.route.params.subscribe(params => {
      this.id = params['id'];
      this.loadProject(this.id);
      this.list(this.id);
    });
  }

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
      this.issues = results['data'];
      this.dataSource = new MatTableDataSource(this.issues);
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
