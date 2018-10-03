import { Component, OnInit, ViewChild } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { TestSuiteService } from '../../../services/test-suite.service';
import { RunService } from '../../../services/run.service';
import { ProjectService } from '../../../services/project.service';
import { Base } from '../../../models/base.model';
//import { MatSort, MatSortable, MatTableDataSource } from '@angular/material';
import { Handler } from '../../dialogs/handler/handler';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { AutoSyncComponent } from '../../dialogs/auto-sync/auto-sync.component';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import { Subscription } from 'rxjs/Subscription';
import { ProjectSync } from '../../../models/project-sync.model';
import { SnackbarService}from '../../../services/snackbar.service';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';

@Component({
selector: 'app-test-suite-list',
  templateUrl: './test-suite-list.component.html',
  styleUrls: ['./test-suite-list.component.scss'],
  providers: [TestSuiteService, RunService, ProjectService, SnackbarService]
})
export class TestSuiteListComponent implements OnInit {

  id; // project id
  testsuites;
  projectId: string = "";
  project: Base = new Base();
  showSpinner: boolean = false;
  keyword: string = '';
  category: string = 'All';
  suites;
  projectSync: ProjectSync = new ProjectSync();
  //private _clockSubscription: Subscription;
  selectedCategories:string;
  categories=['All','SimpleGET','Functional','SLA','Negative','UnSecured','DDOS','XSS_Injection','SQL_Injection','Log_Forging','RBAC'];

  displayedColumns: string[] = ['name', 'category', 'type', 'auto-created', 'modified-date'];
  dataSource = null;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private testSuiteService: TestSuiteService, private runService: RunService, private dialog: MatDialog,
    private projectService: ProjectService, private route: ActivatedRoute, private router: Router, private handler: Handler, private snackbarService: SnackbarService) { }

  ngOnInit() {
    this.handler.activateLoader();
    this.route.params.subscribe(params => {
      this.id = params['id'];
      console.log(params);
      this.loadProject(this.id);
      //this.list(this.id);
      this.searchByCategory();
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
      this.dataSource = new MatTableDataSource(this.testsuites);
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
    this.searchByCategory();
  }

  search() {
     if (this.keyword == '' && this.category == '') {
       return this.list(this.id);
    }
    var category_ = '';
    if (this.category == 'All') {
      category_ = '';
    } else {
      category_ = this.category;
    }
    if(this.keyword.length>1 || this.keyword.length==0){
      this.handler.activateLoader();

    this.testSuiteService.searchTestSuite(this.id, category_, this.keyword, this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.testsuites = results['data'];
      this.length = results['totalElements'];
      this.dataSource = new MatTableDataSource(this.testsuites);
      this.dataSource.sort = this.sort;
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
   }
  }



  searchByCategory() {
    this.handler.activateLoader();
     if (this.keyword == '' && this.category == '') {
       return this.list(this.id);
    }
    var category_ = '';
    if (this.category == 'All') {
      category_ = '';
    } else {
      category_ = this.category;
    }
    this.testSuiteService.searchTestSuite(this.id, category_, this.keyword, this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.testsuites = results['data'];
      this.length = results['totalElements'];
      this.dataSource = new MatTableDataSource(this.testsuites);
      this.dataSource.sort = this.sort;
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getSummary() {
    this.handler.activateLoader();
    this.runService.getSummary(this.id, this.page, this.pageSize).subscribe(results => {
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

  // open() {
  //   const dialogRef = this.dialog.open(AutoSyncComponent, {
  //     //width:'450px',
  //     data: this.project
  // });
  //  dialogRef.afterClosed().subscribe(result => {
  // });
  // }

}
