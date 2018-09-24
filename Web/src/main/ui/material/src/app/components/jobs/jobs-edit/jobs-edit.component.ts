import { Component, OnInit, Inject } from '@angular/core';
import { MatTabChangeEvent}from '@angular/material';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { JobsService } from '../../../services/jobs.service';
import { ProjectService } from '../../../services/project.service';
import { Project } from '../../../models/project.model';
import { Jobs, Noti, Cron } from '../../../models/jobs.model';
import { Env, Auth } from '../../../models/project-env.model';
import { AccountService } from '../../../services/account.service';
import { Account } from '../../../models/account.model';
import { Handler } from '../../dialogs/handler/handler';
import { APPCONFIG } from '../../../config';
import { VERSION, MatDialog, MatDialogRef, MatSnackBar, MatSnackBarConfig , MAT_DIALOG_DATA} from '@angular/material';
import { DeleteDialogComponent}from '../../dialogs/delete-dialog/delete-dialog.component';
import { SnackbarService}from '../../../services/snackbar.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material';
import {RegionsService}from '../../../services/regions.service';
import {IssueTrackerRegisterComponent}from'./../../dialogs/issue-tracker-register/issue-tracker-register.component';
import {SlackRegisterComponent}from'./../../dialogs/slack-register/slack-register.component';

@Component({
  selector: 'app-jobs-edit',
  templateUrl: './jobs-edit.component.html',
  styleUrls: ['./jobs-edit.component.scss'],
  providers: [ProjectService, SnackbarService, JobsService, AccountService, RegionsService]
})
export class JobsEditComponent implements OnInit {

  id: string;
  jobId: string;
  project: Project = new Project();
  job: Jobs = new Jobs();
  // job:any = [];
  envs: Env;
  //itAccounts: Account[];
  itAccounts: Array<Account> = [];
  list;
  page = 0;
  pageSize = 100;
  notifyAccounts: Account[];
  crons: Cron[] = [
  ];
  entry: Account = new Account();

  regions: string[] = ["FXLabs/US_WEST_1", "FXLabs/US_WEST_2", "FXLabs/US_EAST_1", "FXLabs/US_EAST_2", "FXLabs/EU_WEST_1", "FXLabs/EU_CENTRAL_1", "FXLabs/SA_EAST_1"]

  accountTypes = ['GitHub', 'Jira'];

  context: string = "New";
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  categories: string[]=[];
  category: string[];
  selectedCategories: string[]=[];

  constructor(private projectService: ProjectService, private jobsService: JobsService, private accountService: AccountService,
            private route: ActivatedRoute, private regionService: RegionsService, private router: Router, private handler: Handler,
            public snackBar: MatSnackBar, private snackbarService: SnackbarService, private _formBuilder: FormBuilder, public dialog: MatDialog) { }

  ngOnInit() {
    this.getRegions();
    this.job.notifications[0] = new Noti();
    this.job.notifications[1] = new Noti();

    this.route.params.subscribe(params => {
      this.id = params['id'];
      this.jobId = params['jobId'];
      if (this.id) {
        this.loadProject();
        this.getEnvs();
        this.getNotifyAccounts();
      }
      if (this.jobId) {
         this.loadJob();
      }
    });

    this.firstFormGroup = this._formBuilder.group({
      nameCtrl: ['', Validators.required]
    });

    this.secondFormGroup = this._formBuilder.group({
    });

    this.thirdFormGroup = this._formBuilder.group({
    });

    this.crons[0] = new Cron("0 0 12 * * ?", "Fire at 12pm (noon) every day"),
    this.crons[1] = new Cron("0 15 10 * * ?", "Fire at 10:15am every day"),
    this.crons[2] = new Cron("0 15 10 ? * MON-FRI", "Fire at 10:15am every Monday, Tuesday, Wednesday, Thursday and Friday")
    this.categories=['SimpleGET','Functional','SLA','Negative','UnSecured','DDOS','XSS_Injection','SQL_Injection','Log_Forging','RBAC'];

  }

  loadProject() {
    this.handler.activateLoader();
    this.projectService.getById(this.id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.project = results['data'];
      this.job['project'] = this.project;
      this.context = this.project.name + " > Edit";
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  loadJob() {
    this.handler.activateLoader();
    this.jobsService.getById(this.jobId).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.job = results['data'];
      if ( this.job.categories ){
          this.selectedCategories = this.job.categories.split(",")
          .map(function(item) {
            return item.trim();
          });
      }
        this.getITAccountsByAccountType();

      this.loadProject();
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getEnvs() {
    this.projectService.getEnvsByProjectId(this.id).subscribe(results => {
       this.handler.hideLoader();
        if (this.handler.handle(results)) {
        return;
      }
      this.envs = results['data'];
      if (!this.envs) {
      }
      console.log(this.envs);
    });
  }

    getAccountsForIssueTracker(){
        this.handler.activateLoader();
        this.accountService.getAccountByAccountType('ISSUE_TRACKER').subscribe(results => {
          this.handler.hideLoader();
          if (this.handler.handle(results)) {
            return;
          }
          //this.itAccounts = results['data'];
          this.itAccounts = new Array();
        }, error => {
          this.handler.hideLoader();
          this.handler.error(error);
        });
    }

  getITAccountsByAccountType() {
    this.handler.activateLoader();
    this.accountService.getAccountByAccountType('ISSUE_TRACKER').subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      //this.itAccounts = results['data'];
      this.itAccounts = new Array();
        for (let entry of results['data']) {
            if(entry.accountType == this.job.issueTracker.accountType){
                this.itAccounts.push(entry);
            }
        }

    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  openDialogITCredentials() {
    const dialogRef = this.dialog.open(IssueTrackerRegisterComponent, {
      width:'800px',
      data: this.accountTypes
    });
    dialogRef.afterClosed().subscribe(result => {
      this.getITAccountsByAccountType();
    });
  }

  openDialogSlackRegister() {
    const dialogRef = this.dialog.open(SlackRegisterComponent, {
      width:'800px'
    });
    dialogRef.afterClosed().subscribe(result => {
      this.getNotifyAccounts();
    });
  }

  getNotifyAccounts() {
    this.handler.activateLoader();
    this.accountService.getAccountByAccountType('NOTIFICATION_HUB').subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.notifyAccounts = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  saveJob() {
    this.handler.activateLoader();
    this.snackbarService.openSnackBar("'Job '" + this.job.name + "' adding.", "");
    this.jobsService.create(this.job,this.category).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.snackbarService.openSnackBar("'Job '" + this.job.name + "' added.", "");
      this.router.navigate(['/app/projects', this.id, 'jobs']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  delete() {

      let dialogRef = this.dialog.open(DeleteDialogComponent, {
          data: this.job.name + ' project'
      });

      dialogRef.afterClosed().subscribe(result => {
          if (result != null) {
              this.snackbarService.openSnackBar(this.job.name + " deleting...", "");
              this.handler.activateLoader();

              this.jobsService.delete(this.job).subscribe(results => {
                  if (this.handler.handle(results)) {
                      return;
                  }
                  this.snackbarService.openSnackBar("'Job '" + this.job.name + "' deleted", "");
                  this.router.navigate(['/app/projects', this.id, 'jobs']);
              }, error => {
                  this.handler.hideLoader();
                  this.handler.error(error);
              });
          }
      });
  }

  cloneJob() {
    localStorage.setItem('jobClone', JSON.stringify(this.job));
    this.router.navigate(['/app/projects', this.id, 'jobs', 'new']);
  }

  getRegions() {
    this.handler.activateLoader();
    this.regionService.getEntitled(this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.list = results['data'];
      //this.length = results['totalElements'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
}
