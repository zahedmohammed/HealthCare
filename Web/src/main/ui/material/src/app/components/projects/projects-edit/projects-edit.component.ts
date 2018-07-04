import { Component, OnInit, Inject } from '@angular/core';
import {MatTabChangeEvent}from '@angular/material';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { ProjectService } from '../../../services/project.service';
import { OrgService } from '../../../services/org.service';
import { AccountService } from '../../../services/account.service';
import {DashboardService}from '../../../services/dashboard.service';
import {TestSuiteService}from '../../../services/test-suite.service';
import { Project } from '../../../models/project.model';
import { Handler } from '../../dialogs/handler/handler';
import { APPCONFIG } from '../../../config';
import {VERSION, MatDialog, MatDialogRef, MatSnackBar, MatSnackBarConfig } from '@angular/material';
import {DeleteDialogComponent}from '../../dialogs/delete-dialog/delete-dialog.component';
import { Http, Response} from '@angular/http';

@Component({
  selector: 'app-projects-edit',
  templateUrl: './projects-edit.component.html',
  styleUrls: ['./projects-edit.component.scss'],
  providers: [ProjectService, OrgService, DashboardService, TestSuiteService]
})
export class ProjectsEditComponent implements OnInit {

  showSpinner: boolean = false;
  orgs;
  project: Project = new Project();
  accounts;
  config;
  TestSuite;
  id;
  public AppConfig: any;
  constructor(private projectService: ProjectService, private accountService: AccountService,
  private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler,
  public dialog: MatDialog, public snackBar: MatSnackBar, private dashboardService: DashboardService) { }

  ngOnInit() {
    this.AppConfig = APPCONFIG;
    this.route.params.subscribe(params => {
      console.log(params);
      this.id = params['id'];
      if (params['id']) {
        this.getById(params['id']);
        //this.getOrgs();
        this.getAccountsForProjectPage();
        this.config = new MatSnackBarConfig();
      }
    });
  }

    tabChanged = (tabChangeEvent: MatTabChangeEvent): void => {
      //console.log('tabChangeEvent => ', tabChangeEvent);
      console.log('index => ', tabChangeEvent.index);
      if (tabChangeEvent.index === 0) {
        this.getById(this.id);
      } else if (tabChangeEvent.index === 1) {
        this.getTestSuite(this.id);
      }
  }

  getById(id: string) {
    this.showSpinner = true;
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

  update() {
    console.log(this.project);
    this.projectService.update(this.project).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.config.verticalPosition = 'top';
      this.config.horizontalPosition = 'right';
      this.config.duration = 3000;
      this.snackBar.open("Project " + this.project.name + " Successfully Updated", "", this.config);
      this.router.navigate(['/app/projects']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

delete() {
    let dialogRef = this.dialog.open(DeleteDialogComponent, {
        data: {
            project: this.project
        }
    });

    dialogRef.afterClosed().subscribe(result => {
        if (result != null) {
            this.handler.activateLoader();
            this.projectService.delete(this.project).subscribe(results => {
                this.handler.hideLoader();
                if (this.handler.handle(results)) {
                    return;
                }
                this.config.verticalPosition = 'top';
                this.config.horizontalPosition = 'right';
                this.config.duration = 3000;
                this.snackBar.open("Project " + this.project.name + " Successfully Deleted", "", this.config);
                this.router.navigate(['/app/projects']);
            }, error => {
                this.handler.hideLoader();
                this.handler.error(error);
            });
        }
    });
}

  getOrgs() {
    this.orgService.getByUser().subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.orgs = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getAccountsForProjectPage() {
    this.handler.activateLoader();
    this.accountService.getAccountByAccountType('PROJECT').subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.accounts = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
   getTestSuite(id: string) {
    this.handler.activateLoader();
    this.dashboardService.projectSavings(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.TestSuite = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  setAccount(account){
     this.project.account.accountType =  account.accountType;
  }

  projectTypes = ['Git', 'GitHub', 'BitBucket', 'GitLab', 'Microsoft_TFS_Git', 'Microsoft_VSTS_Git', 'Local'];
  visibilities = ['PRIVATE', 'ORG_PUBLIC'];
  genPolicies = ['None', 'Create'];

}
