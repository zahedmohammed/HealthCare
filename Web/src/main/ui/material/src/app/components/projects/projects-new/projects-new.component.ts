import { Component, OnInit, Inject } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { ProjectService } from '../../../services/project.service';
import { OrgService } from '../../../services/org.service';
import { JobsService } from '../../../services/jobs.service';
import { AccountService } from '../../../services/account.service';
import { Account } from '../../../models/account.model';
import { Project } from '../../../models/issues-project.model';
import { AutoCodeConfig } from '../../../models/project-autocode-config.model';
import { Env, Auth } from '../../../models/project-env.model';
import { Job } from '../../../models/project-job.model';
import { OrgUser } from '../../../models/org.model';
import { Handler } from '../../dialogs/handler/handler';
import { APPCONFIG } from '../../../config';
import { MatSnackBar, MatSnackBarConfig, MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { SnackbarService } from '../../../services/snackbar.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material';

@Component({
  selector: 'app-projects-new',
  templateUrl: './projects-new.component.html',
  styleUrls: ['./projects-new.component.scss'],
  providers: [ProjectService, OrgService, SnackbarService, JobsService, AccountService]
})
export class ProjectsNewComponent implements OnInit {

  showSpinner: boolean = false;
  id: string;
  project: Project = new Project();
  autoCodeConfig: AutoCodeConfig = new AutoCodeConfig();
  job: Job = new Job();
  env: Env = new Env();
  orgs;
  accounts;
  matStepper;// MatStepper;
  flag = false;

  context: string = "New";

  isLinear = false;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;

  authTypes = ['Basic', 'Token', 'OAuth_2_0', 'Auth0', 'No_Authentication'];
  grantTypes = ['password', 'client_credentials', 'authorization_code', 'implicit'];
  auth0GrantTypes = ['client_credentials', 'password'];
  schemeTypes = ['form', 'header', 'none', 'query'];
  scopeTypes = ['read', 'write'];



  public AppConfig: any;
  constructor(private projectService: ProjectService, private accountService: AccountService, private jobsService: JobsService,
    private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler,
    public snackBar: MatSnackBar, private snackbarService: SnackbarService, private _formBuilder: FormBuilder, public dialog: MatDialog) {
  }

  ngOnInit() {
    this.AppConfig = APPCONFIG;
    this.route.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.loadProject(this.id);
      }
    });

    this.env.auths[0] = new Auth();
    this.env.auths[0].name = "Default";

    this.firstFormGroup = this._formBuilder.group({
      nameCtrl: ['', Validators.required],
    });
    this.secondFormGroup = this._formBuilder.group({
      openAPISpec: ['', Validators.required]
    });

    this.thirdFormGroup = this._formBuilder.group({
      nameCtrl: ['', [Validators.required]],
      urlCtrl: ['', Validators.required],
      authTypeCtrl: ['', Validators.required],
      usernameCtrl: [''],
      pswCtrl: ['']
    });
  }


  setAuthType(obj) {
    this.env.auths[0].authType = obj;
    if (obj == 'No_Authentication' || obj == 'OAuth_2_0' || obj == 'Token') {
      this.thirdFormGroup.controls["pswCtrl"].setValidators([]);
      this.thirdFormGroup.controls["pswCtrl"].updateValueAndValidity();
      this.thirdFormGroup.controls["usernameCtrl"].setValidators([]);
      this.thirdFormGroup.controls["usernameCtrl"].updateValueAndValidity();
    } else {
      /*this.thirdFormGroup.controls["pswCtrl"].setValidators([Validators.required]);
      this.thirdFormGroup.controls["pswCtrl"].updateValueAndValidity();
      this.thirdFormGroup.controls["usernameCtrl"].setValidators([Validators.required]);
      this.thirdFormGroup.controls["usernameCtrl"].updateValueAndValidity();*/
    }

    if (obj == 'Auth0') {
      this.env.auths[0].grantType = 'client_credentials';
    }

  }


  loadProject(id: string) {
    this.handler.activateLoader();
    this.projectService.getById(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.project = results['data'];
      this.context = this.project.name + " > Edit";
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  save() {
    if (this.project.id) {
      this.update();
    } else {
      this.create();
    }
  }

  create() {
    this.handler.activateLoader();
    this.snackbarService.openSnackBar("'Project '" + this.project.name + "' creating...", "");
    this.projectService.create(this.project).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
        
      }
      this.snackbarService.openSnackBar("'Project '" + this.project.name + "' created successfully", "");
      this.project = results['data'];
      this.router.navigate(['/app/projects']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  update() {
    console.log(this.project);
    this.snackbarService.openSnackBar("'Project '" + this.project.name + "' saving...", "");
    this.projectService.update(this.project).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.snackbarService.openSnackBar("'Project '" + this.project.name + "' saved successfully", "");
      this.project = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getAutoCode() {
    this.projectService.getAutoCodeConfig(this.project.id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.autoCodeConfig = results['data'];
      console.log(this.autoCodeConfig);
    });
  }

  saveAutoCode() {
    console.log(this.autoCodeConfig);
    this.snackbarService.openSnackBar("'Project '" + this.project.name + "' AutoCode saving...", "");
    this.projectService.saveAutoCodeConfig(this.autoCodeConfig, this.project.id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.autoCodeConfig = results['data'];
      this.snackbarService.openSnackBar("'Project '" + this.project.name + "' AutoCode saved successfully", "");
      this.matStepper.next();
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  saveNewProjectAutoCode() {
    console.log(this.autoCodeConfig);
    this.snackbarService.openSnackBar("'Project '" + this.project.name + "' AutoCode saving...", "");

    this.projectService.saveNewProjectAutoCodeConfig(this.autoCodeConfig, this.project.id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.autoCodeConfig = results['data'];
      this.snackbarService.openSnackBar("'Project '" + this.project.name + "' AutoCode saved successfully", "");
      this.matStepper.next();
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  saveEnv() {
    console.log(this.env);
    this.snackbarService.openSnackBar("'Project '" + this.project.name + "' Environment saving...", "");
    this.env.projectId = this.project.id;
    this.projectService.saveEnv(this.env, this.project.id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.env = results['data'];
      this.snackbarService.openSnackBar("'Project '" + this.project.name + "' Environment saved successfully", "");
      this.router.navigate(['/app/projects']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getEnvByProjectId(id: string) {
    this.handler.activateLoader();
    this.projectService.getEnvsByProjectId(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      if (results['data'] != null) {
        this.env = results['data'][0];
      }
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
  projectTypes = ['Git', 'GitHub', 'BitBucket', 'GitLab', 'Microsoft_TFS_Git', 'Microsoft_VSTS_Git', 'Local'];
  visibilities = ['PRIVATE', 'ORG_PUBLIC'];
  genPolicies = ['None', 'Create'];
  dbs = ['MySQL', 'Oracle', 'Postgres', 'SQLServer', 'MongoDB'];

}

