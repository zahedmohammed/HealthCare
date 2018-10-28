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

@Component({
  selector: 'app-jobs-list',
  templateUrl: './jobs-list.component.html',
  styleUrls: ['./jobs-list.component.scss'],
  providers: [JobsService, RunService, ProjectService]
})
export class JobslistComponent implements OnInit {

  id; // project id
  jobs;
  projectId: string = "";
  project: Base = new Base();
  showSpinner: boolean = false;
  //private _clockSubscription: Subscription;
  notificationFlag: boolean;
  autoBugMngmnt: boolean = false;

  displayedColumns: string[] = ['name', 'env', 'bot-region', 'bug-management', 'notifications', 'next-fire', 'jobsNoHeader'];
  dataSource = null;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private jobsService: JobsService, private runService: RunService, private dialog: MatDialog,
    private projectService: ProjectService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.handler.activateLoader();
    this.route.params.subscribe(params => {
      this.id = params['id'];
      console.log(params);
      this.loadProject(this.id);
    //  this.list(this.id);
      // this.notificationFlag = false;
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
    this.jobsService.getJobs(id, this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.jobs = results['data'];
      this.dataSource = new MatTableDataSource(this.jobs);
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

  runJob(id: string) {
    this.handler.activateLoader();
    this.runService.run(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.router.navigate(['/app/jobs/', id, 'runs']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  advRun(job) {
    const dialogRef = this.dialog.open(AdvRunComponent, {
      width: '800px',
      data: job
    });
    dialogRef.afterClosed().subscribe(result => {
    });
  }
  openJenkins() {
    const dialogRef = this.dialog.open(JenkinsIntegrationComponent, {
      width: '800px',
      data: this.id
    });
    dialogRef.afterClosed().subscribe(result => {
    });
  }
  openMaven() {
    const dialogRef = this.dialog.open(MavenIntegrationComponent, {
      width: '800px',
      data: this.id
    });
    dialogRef.afterClosed().subscribe(result => {
    });
  }
  openGradle() {
    const dialogRef = this.dialog.open(GradleIntegrationComponent, {
      width: '800px',
      data: this.id
    });
    dialogRef.afterClosed().subscribe(result => {
    });
  }
}
