import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { CreateProfileComponent } from '../create-profile/create-profile.component';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { ProjectService } from '../../../services/project.service';
import { OrgService } from '../../../services/org.service';
// import { JobsService } from '../../../services/jobs.service';
import { AccountService } from '../../../services/account.service';
import { Account } from '../../../models/account.model';
import { Project } from '../../../models/project.model';
// import { Jobs } from '../../../models/project-job.model';
import { OrgUser } from '../../../models/org.model';
import { Handler } from '../../dialogs/handler/handler';
import { APPCONFIG } from '../../../config';
import { MatSnackBar, MatSnackBarConfig }from '@angular/material';
import {SnackbarService}from '../../../services/snackbar.service';
import { AutoCodeConfig } from '../../../models/project-autocode-config.model';

@Component({
  selector: 'step-two-component',
  templateUrl: './step-two.component.html',
  styleUrls: ['./step-two.component.scss'],
  providers: [ProjectService, OrgService, SnackbarService]
})
export class StepTwoComponent implements OnInit {
  stepTwoForm: FormGroup;
  show = false;
  showSpinner: boolean = false;
  project: Project = new Project();
  // job: Jobs = new Jobs();
  orgs;
  accounts;
  /* Expansion Panel - Accordian Variables */
  panelOpenState = false;
  severityGroup: string;
  severity: string[] = ['Critical', 'Major', 'Minor'];
  //autocodeConfig = new Array();
  autoCodeConfig: AutoCodeConfig = new AutoCodeConfig();
  /*config;*/
  //radioSelected:string;
  public AppConfig: any;
  constructor(private formBuilder: FormBuilder, 
    private projectService: ProjectService, 
    private accountService: AccountService,
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
    this.listAutoCode();
  //   this.frmStepTwo = this.formBuilder.group({
  //     address: ['', Validators.required]
  // });

  // this.stepTwoForm = new FormGroup({
  //   autocode: new FormControl(),
  //   spec: new FormControl(),
  //   severity: new FormControl(),

  // });

  }

  // create() {
  //   this.handler.activateLoader();
  //     this.snackbarService.openSnackBar( this.project.name + " creating...", "");
  //     this.projectService.create(this.project).subscribe(results => {
  //       this.handler.hideLoader();
  //       if (this.handler.handle(results)) {
  //           return;
  //       }
  //      /* this.config.verticalPosition = 'top';
  //       this.config.horizontalPosition = 'right';
  //       this.config.duration = 3000;*/
  //       this.snackbarService.openSnackBar(this.project.name + " created successfully", "");
  //       this.router.navigate(['/app/auto-code']);
  //   }, error => {
  //       this.handler.hideLoader();
  //       this.handler.error(error);
  //   });
  // }

  // createJob() {
  //   this.handler.activateLoader();
  //     this.snackbarService.openSnackBar( this.job.name + " creating...", "");
  //     this.jobsService.createJob(this.job).subscribe(results => {
  //       this.handler.hideLoader();
  //       if (this.handler.handle(results)) {
  //           return;
  //       }
  //      /* this.config.verticalPosition = 'top';
  //       this.config.horizontalPosition = 'right';
  //       this.config.duration = 3000;*/
  //       this.snackbarService.openSnackBar(this.project.name + " created successfully", "");
  //       this.router.navigate(['/app/projects']);
  //   }, error => {
  //       this.handler.hideLoader();
  //       this.handler.error(error);
  //   });
  // }


  //For Autocode
  listAutoCode() {
    this.handler.activateLoader();
    this.projectService.getAutoCodeConfig().subscribe(results => {
      //console.log(results);
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.autoCodeConfig = results['generators'];
      //this.length = results['totalElements'];
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
