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
import { VERSION, MatDialog, MatDialogRef, MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { DeleteDialogComponent}from '../../dialogs/delete-dialog/delete-dialog.component';
import { SnackbarService}from '../../../services/snackbar.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material';



@Component({
  selector: 'app-jobs-new',
  templateUrl: './jobs-new.component.html',
  styleUrls: ['./jobs-new.component.scss'],
  providers: [ProjectService, SnackbarService, JobsService, AccountService]
})
export class JobsNewComponent implements OnInit {

  id: string;
  project: Project = new Project();
  job: Jobs = new Jobs();
  envs: Env;
  itAccounts: Account[];
  notifyAccounts: Account[];
  crons: Cron[] = [
  ];

  regions: string[] = ["FXLabs/US_WEST_1", "FXLabs/US_WEST_2", "FXLabs/US_EAST_1", "FXLabs/US_EAST_2", "FXLabs/EU_WEST_1", "FXLabs/EU_CENTRAL_1", "FXLabs/SA_EAST_1"]


  context: string = "New";
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;

  constructor(private projectService: ProjectService, private jobsService: JobsService, private accountService: AccountService,
            private route: ActivatedRoute, private router: Router, private handler: Handler,
            public snackBar: MatSnackBar, private snackbarService: SnackbarService, private _formBuilder: FormBuilder) { }

  ngOnInit() {
    this.job.notifications[0] = new Noti();
    this.job.notifications[1] = new Noti();

    this.route.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.loadProject(this.id);
        this.getEnvs();
        this.getITAccounts();
        this.getNotifyAccounts();
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

  getITAccounts() {
    this.handler.activateLoader();
    this.accountService.getAccountByAccountType('ISSUE_TRACKER').subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.itAccounts = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
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
    this.jobsService.create(this.job).subscribe(results => {
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
}
