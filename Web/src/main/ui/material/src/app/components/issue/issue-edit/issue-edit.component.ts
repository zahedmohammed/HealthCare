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

@Component({
  selector: 'app-issue-edit',
  templateUrl: './issue-edit.component.html',
  styleUrls: ['./issue-edit.component.scss'],
  providers: [ProjectService, SnackbarService, JobsService, AccountService, RegionsService]
})
export class IssueEditComponent implements OnInit {

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

  accountTypes = ['FX_Issues', 'GitHub', 'Jira'];

  context: string = "New";
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  categories: string[] = [];
  category: string[];
  selectedCategories: string[] = [];

  constructor(private projectService: ProjectService, private jobsService: JobsService, private accountService: AccountService,
    private route: ActivatedRoute, private regionService: RegionsService, private router: Router, private handler: Handler,
    public snackBar: MatSnackBar, private snackbarService: SnackbarService, private _formBuilder: FormBuilder, public dialog: MatDialog) { }

  ngOnInit() {
    this.job.notifications[0] = new Noti();
    this.job.notifications[1] = new Noti();

    this.route.params.subscribe(params => {
      this.id = params['id'];
      this.jobId = params['jobId'];
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
    this.categories = ['SimpleGET', 'Functional', 'SLA', 'Negative', 'UnSecured', 'InvalidAuth', 'InvalidAuthSQL', 'InvalidAuthEmpty', 'DDOS', 'XSS_Injection', 'SQL_Injection', 'Log_Forging', 'RBAC'];

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
}
