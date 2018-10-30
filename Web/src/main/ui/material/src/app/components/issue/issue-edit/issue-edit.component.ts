import { Headers } from '@angular/http';
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
import { SnackbarService } from '../../../services/snackbar.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material';
import { RegionsService } from '../../../services/regions.service';
import { IssuesService } from './../../../services/issues.service';
import { Issue } from './../../../models/issue.model';

@Component({
  selector: 'app-issue-edit',
  templateUrl: './issue-edit.component.html',
  styleUrls: ['./issue-edit.component.scss'],
  providers: [ProjectService, SnackbarService, JobsService, AccountService, RegionsService, IssuesService]
})
export class IssueEditComponent implements OnInit {
  httpMethod = ['GET', 'POST', 'PUT', 'DELETE'];
  issue: Issue = new Issue;
  issueId: string;
  id: string;
  jobId: string;
  project: Project = new Project();
  job: Jobs = new Jobs();
  // job:any = [];
  envs: Env;
  issues: any;
  itAccounts: Array<Account> = [];
  list;
  page = 0;
  pageSize = 100;
  notifyAccounts: Account[];
  crons: Cron[] = [
  ];
  entry: Account = new Account();
  regions: string[] = ["FXLabs/US_WEST_1", "FXLabs/US_WEST_2", "FXLabs/US_EAST_1", "FXLabs/US_EAST_2", "FXLabs/EU_WEST_1", "FXLabs/EU_CENTRAL_1", "FXLabs/SA_EAST_1"]
  accountTypes = ['FX_Issues', 'GitHub', 'Jira'];
  context: string = "New";
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  categories: string[] = [];
  category: string[];
  headers = "";
  selectedCategories: string[] = [];

  constructor(private IssuesService: IssuesService, private projectService: ProjectService, private jobsService: JobsService, private accountService: AccountService,
    private route: ActivatedRoute, private regionService: RegionsService, private router: Router, private handler: Handler,
    public snackBar: MatSnackBar, private snackbarService: SnackbarService, private _formBuilder: FormBuilder, public dialog: MatDialog) { }

  ngOnInit() {
    // this.loadIssues(this.id);
    this.job.notifications[0] = new Noti();
    this.job.notifications[1] = new Noti();

    this.route.params.subscribe(params => {
      this.id = params['id'];
      this.loadProject();
      this.issueId = params['issueId'];
    });

    this.loadIssues();
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
      nameCtrl11: ['', Validators.required]
    });
  }

  loadProject() {
    this.handler.activateLoader();
    this.projectService.getById(this.id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.project = results['data'];
      // this.job['project'] = this.project;
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
  loadIssues() {
    this.handler.activateLoader();
    this.IssuesService.getById(this.issueId).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.issues = results['data'];
      this.issue = results['data'];
      this.headers = this.issues.headers[0];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
  save() {
    this.snackbarService.openSnackBar(this.issue.env + "  saving...", "");
    this.handler.activateLoader();
    this.IssuesService.update(this.issue).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.issues = results['data'];
      this.snackbarService.openSnackBar(this.issue.env + "  saved successfully", "");
      this.router.navigate(['/app/projects', this.project.id, 'issue']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
  delete() {
    var r = confirm("Are you sure you want to delete this issue project ?");
      if (r == true) {
    this.snackbarService.openSnackBar(this.issue.env + " deleting...", "");
    this.handler.activateLoader();
    this.IssuesService.delete(this.issues.project.id, this.issueId).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.issues = results['data'];
      this.snackbarService.openSnackBar(this.issue.env + "  deleted successfully", "");
      this.router.navigate(['/app/projects', this.project.id, 'issue']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
  }
}
