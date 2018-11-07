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
import { Job } from '../../../models/project-job.model';
import { RegionsService } from '../../../services/regions.service';
import { IssuesService } from '../../../services/issues.service';
import { Issue } from '../../../models/issue.model';

@Component({
  selector: 'app-issue-new',
  templateUrl: './issue-new.component.html',
  styleUrls: ['./issue-new.component.scss'],
  providers: [ProjectService, SnackbarService, JobsService, AccountService, RegionsService, IssuesService]
})
export class IssueNewComponent implements OnInit {
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
  status = ['OPEN', 'CLOSED'];

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
    this.route.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.loadProject(this.id);
      }
    });

    this.firstFormGroup = this._formBuilder.group({
      Ctrl1: ['', Validators.required],
      Ctrl2: ['', Validators.required],
      Ctrl3: ['', Validators.required],
      Ctrl4: ['', Validators.required],
      Ctrl5: ['', Validators.required],
      Ctrl6: ['', Validators.required],
      Ctrl7: ['', Validators.required],
      Ctrl8: ['', Validators.required],
      Ctrl9: ['', Validators.required],
      Ctrl10: ['', Validators.required],
      Ctrl11: ['', Validators.required],
      Ctrl12: ['', Validators.required],
      Ctrl13: ['', Validators.required]
    });

    this.secondFormGroup = this._formBuilder.group({
    });

    this.thirdFormGroup = this._formBuilder.group({
    });
  }

  createIssue() {
    this.snackbarService.openSnackBar(this.issue.issueName + " Creating ...", "");
    this.handler.activateLoader();
    this.issue.project.id = this.project.id;
    this.issue.headers = this.issue.headersText.split(",");
    this.issuesService.create(this.issue).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return
      }
      this.issue = results['data'];
      this.snackbarService.openSnackBar(this.issue.issueName + "  Created successfully", "");
      this.router.navigate(['/app/projects', this.project.id, 'issue']);
      error => {
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

}
