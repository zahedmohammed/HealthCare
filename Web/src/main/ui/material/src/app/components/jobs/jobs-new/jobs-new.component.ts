import { Component, OnInit, Inject } from '@angular/core';
import { MatTabChangeEvent } from '@angular/material';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { JobsService } from '../../../services/jobs.service';
import { ProjectService } from '../../../services/project.service';
import { Project } from '../../../models/project.model';
import { Jobs, Noti, Cron } from '../../../models/jobs.model';
import { Env, Auth } from '../../../models/project-env.model';
import { AccountService } from '../../../services/account.service';
import { Account } from '../../../models/account.model';
import { Handler } from '../../dialogs/handler/handler';
import { APPCONFIG } from '../../../config';
import { VERSION, MatDialog, MatDialogRef, MatSnackBar, MatSnackBarConfig, MAT_DIALOG_DATA } from '@angular/material';
import { DeleteDialogComponent } from '../../dialogs/delete-dialog/delete-dialog.component';
import { SnackbarService } from '../../../services/snackbar.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material';
import { IssueTrackerRegisterComponent } from './../../dialogs/issue-tracker-register/issue-tracker-register.component';
import { SlackRegisterComponent } from './../../dialogs/slack-register/slack-register.component';
import { Job } from '../../../models/project-job.model';
import { RegionsService } from '../../../services/regions.service';
import { IssuesService } from './../../../services/issues.service';
import { Issue } from '../../../models/issue.model';

@Component({
  selector: 'app-jobs-new',
  templateUrl: './jobs-new.component.html',
  styleUrls: ['./jobs-new.component.scss'],
  providers: [ProjectService, SnackbarService, JobsService, AccountService, RegionsService, IssuesService]
})
export class JobsNewComponent implements OnInit {
  httpMethod = ['GET', 'POST', 'PUT', 'DELETE'];
  issue: Issue = new Issue;
  id: string;
  project: Project = new Project();
  job: Jobs = new Jobs();
  list;
  page = 0;
  pageSize = 100;
  clone: Object;
  envs: Env;
  //itAccounts: Account[];
  itAccounts: Array<Account> = [];
  notifyAccounts: Account[];
  entry: Account = new Account();

  crons: Cron[] = [
  ];
  categories: string[] = [];
  category: string[];
  selectedCategories: string[] = [];

  regions: string[] = ["FXLabs/US_WEST_1", "FXLabs/US_WEST_2", "FXLabs/US_EAST_1", "FXLabs/US_EAST_2", "FXLabs/EU_WEST_1", "FXLabs/EU_CENTRAL_1", "FXLabs/SA_EAST_1"]

  accountTypes = ['FX_Issues', 'GitHub', 'Jira'];
  context: string = "New";
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;

  constructor(private issuesService: IssuesService, private projectService: ProjectService, private jobsService: JobsService, private accountService: AccountService,
    private route: ActivatedRoute, private regionService: RegionsService, private router: Router, private handler: Handler,
    public snackBar: MatSnackBar, private snackbarService: SnackbarService, private _formBuilder: FormBuilder, public dialog: MatDialog) { }

  ngOnInit() {
    // this.getRegions();
    this.job.notifications[0] = new Noti();
    this.job.notifications[1] = new Noti();

    this.route.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.loadProject(this.id);
        // this.getEnvs();
        // this.getNotifyAccounts();
      }
    });

    this.firstFormGroup = this._formBuilder.group({
      nameCtrl: ['', Validators.required],
      nameCtrl1: ['', Validators.required],
      nameCtrl2: ['', Validators.required],
      nameCtrl3: ['', Validators.required],
      nameCtrl4: ['', Validators.required],
      nameCtrl5: ['', Validators.required],
      nameCtrl6: ['', Validators.required],
      nameCtrl7: ['', Validators.required],
      nameCtrl8: ['', Validators.required],
      nameCtrl9: ['', Validators.required],
      nameCtrl10: ['', Validators.required],
    });

    this.secondFormGroup = this._formBuilder.group({
    });

    this.thirdFormGroup = this._formBuilder.group({
    });

    if (localStorage.getItem('jobClone') != null) {
      var jobClone = localStorage.getItem("jobClone");
      this.job.name = JSON.parse(jobClone)["name"] + "_copy";
      this.job.environment = JSON.parse(jobClone)["environment"];
      this.job.regions = JSON.parse(jobClone)["regions"];
      this.job.categories = JSON.parse(jobClone)["categories"];
      if (this.job.categories) {
        this.category = this.job.categories.split(",")
          .map(function (item) {
            return item.trim();
          });
      }
      this.job.cron = JSON.parse(jobClone)["cron"];
      localStorage.removeItem('jobClone');
    }
    this.crons[0] = new Cron("0 0 12 * * ?", "Fire at 12pm (noon) every day"),
      this.crons[1] = new Cron("0 15 10 * * ?", "Fire at 10:15am every day"),
      this.crons[2] = new Cron("0 15 10 ? * MON-FRI", "Fire at 10:15am every Monday, Tuesday, Wednesday, Thursday and Friday")
    this.categories = ['SimpleGET', 'Functional', 'SLA', 'Negative', 'UnSecured', 'InvalidAuth', 'InvalidAuthSQL', 'InvalidAuthEmpty', 'DDOS', 'XSS_Injection', 'SQL_Injection', 'Log_Forging', 'RBAC'];

  }

  createIssue() {
    this.handler.activateLoader();
    this.issue.project.id = this.project.id
    this.issue.headers = this.issue.headersText.split(",");
    this.issuesService.create(this.issue).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return
      }
      this.issue = results['data'];
      error => {
        console.log('issue 2..',this.issue);
        this.handler.hideLoader();
        this.handler.error(error);
      }
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
      this.job['project'] = this.project;
      this.context = this.project.name + " > Edit";
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

  getAccountsForIssueTracker() {
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
        if (entry.accountType == this.job.issueTracker.accountType) {
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
      width: '800px',
      data: this.accountTypes
    });
    dialogRef.afterClosed().subscribe(result => {
      this.getITAccountsByAccountType();
    });
  }

  openDialogSlackRegister() {
    const dialogRef = this.dialog.open(SlackRegisterComponent, {
      width: '800px'
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
    this.jobsService.create(this.job, this.category).subscribe(results => {
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
