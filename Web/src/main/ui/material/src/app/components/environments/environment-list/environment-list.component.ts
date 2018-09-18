import { Component, OnInit, ViewChild } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { JobsService } from '../../../services/jobs.service';
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
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-environment-list',
  templateUrl: './environment-list.component.html',
  styleUrls: ['./environment-list.component.scss'],
  providers: [JobsService, RunService, ProjectService]
})
export class EnvironmentListComponent implements OnInit {

  id; // project id
  envs;
  projectId: string = "";
  project: Base = new Base();
  showSpinner: boolean = false;
  //private _clockSubscription: Subscription;
  displayedColumns: string[] = ['name', 'baseUrl', 'authCount', 'createdDate'];
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
      this.list(this.id);
    });
    /*let timer = Observable.timer(1, 10000);
    this._clockSubscription = timer.subscribe(t => {
      this.list();
    });*/
  }

  ngOnDestroy(): void {
    //this._clockSubscription.unsubscribe();
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
    this.projectService.getEnvsByProjectId(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.envs = results['data'];
      this.length = results['totalElements'];
      this.dataSource = new MatTableDataSource(this.envs);
      this.dataSource.sort = this.sort;
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
