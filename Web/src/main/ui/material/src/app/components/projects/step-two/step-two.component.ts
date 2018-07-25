import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { CreateProfileComponent } from '../create-profile/create-profile.component';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { ProjectService } from '../../../services/project.service';
import { OrgService } from '../../../services/org.service';
import { JobsService } from '../../../services/jobs.service';
import { AccountService } from '../../../services/account.service';
import { Account } from '../../../models/account.model';
import { Project } from '../../../models/project.model';
import { Job } from '../../../models/project-job.model';
import { OrgUser } from '../../../models/org.model';
import { Handler } from '../../dialogs/handler/handler';
import { APPCONFIG } from '../../../config';
import { MatSnackBar, MatSnackBarConfig }from '@angular/material';
import {SnackbarService}from '../../../services/snackbar.service';

@Component({
  selector: 'step-two-component',
  templateUrl: './step-two.component.html',
  styleUrls: ['./step-two.component.scss'],
  providers: [ProjectService, OrgService, SnackbarService, JobsService]
})
export class StepTwoComponent implements OnInit {
  frmStepTwo: FormGroup;
  show = true;
  showSpinner: boolean = false;
  project: Project = new Project();
  job: Job = new Job();
  orgs;
  accounts;
  /* Expansion Panel - Accordian Variables */
  panelOpenState = false;
  severityGroup: string;
  severity: string[] = ['Critical', 'Major', 'Minor', 'Trivial '];

  /*config;*/
  public AppConfig: any;
  constructor(private formBuilder: FormBuilder, 
    private projectService: ProjectService, 
    private accountService: AccountService, 
    private jobsService: JobsService,
    private orgService: OrgService, 
    private route: ActivatedRoute, 
    private router: Router, 
    private handler: Handler, 
    public snackBar: MatSnackBar, 
    private snackbarService: SnackbarService) { }

  ngOnInit() {
    this.AppConfig = APPCONFIG;
    //this.getOrgs();
    //this.getAccountsForProjectPage();
    this.frmStepTwo = this.formBuilder.group({
      address: ['', Validators.required]
  });

  }

  create() {
    this.handler.activateLoader();
      this.snackbarService.openSnackBar( this.project.name + " creating...", "");
      this.projectService.create(this.project).subscribe(results => {
        this.handler.hideLoader();
        if (this.handler.handle(results)) {
            return;
        }
       /* this.config.verticalPosition = 'top';
        this.config.horizontalPosition = 'right';
        this.config.duration = 3000;*/
        this.snackbarService.openSnackBar(this.project.name + " created successfully", "");
        this.router.navigate(['/app/auto-code']);
    }, error => {
        this.handler.hideLoader();
        this.handler.error(error);
    });
  }

  createJob() {
    this.handler.activateLoader();
      this.snackbarService.openSnackBar( this.job.name + " creating...", "");
      this.jobsService.createJob(this.job).subscribe(results => {
        this.handler.hideLoader();
        if (this.handler.handle(results)) {
            return;
        }
       /* this.config.verticalPosition = 'top';
        this.config.horizontalPosition = 'right';
        this.config.duration = 3000;*/
        this.snackbarService.openSnackBar(this.project.name + " created successfully", "");
        this.router.navigate(['/app/projects']);
    }, error => {
        this.handler.hideLoader();
        this.handler.error(error);
    });
  }

  getOrgs() {
    this.handler.activateLoader();
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

  setAccount(account){
     this.project.account.accountType =  account.accountType;
  }

  projectTypes = ['Git', 'GitHub', 'BitBucket', 'GitLab', 'Microsoft_TFS_Git', 'Microsoft_VSTS_Git', 'Local'];
  visibilities = ['PRIVATE', 'ORG_PUBLIC'];
  genPolicies = ['None', 'Create'];

}
