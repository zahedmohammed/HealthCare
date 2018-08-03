import { RegisterComponent } from './../../dialogs/register/register.component';
import { Component, OnInit, Inject } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { ProjectService } from '../../../services/project.service';
import { OrgService } from '../../../services/org.service';
import { JobsService } from '../../../services/jobs.service';
import { AccountService } from '../../../services/account.service';
import { Account } from '../../../models/account.model';
import { Project } from '../../../models/project.model';
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
  selector: 'app-environment-new',
  templateUrl: './environment-new.component.html',
  styleUrls: ['./environment-new.component.scss'],
  providers: [ProjectService, OrgService, SnackbarService, JobsService]
})

export class EnvironmentNewComponent implements OnInit {

  showSpinner: boolean = false;
  id: string;
  envId:string;
  project: Project = new Project();
  autoCodeConfig: AutoCodeConfig = new AutoCodeConfig();
  env: Env = new Env();
  orgs;
  accounts;
  matStepper;// MatStepper;

  context: string = "New";

  isLinear = false;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;

  authTypes = ['Basic', 'OAuth_2_0' , 'Token'];
  authTypesgrantTypes = ['password', 'client_credentials', 'authorization_code', 'implicit'];
  schemeTypes = ['form', 'header', 'none', 'query'];
  scopeTypes= ['read', 'write'];


  public AppConfig: any;
  constructor(private projectService: ProjectService, private accountService: AccountService, private jobsService: JobsService,
            private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler,
            public snackBar: MatSnackBar, private snackbarService: SnackbarService, private _formBuilder: FormBuilder, public dialog: MatDialog) {

  }

  ngOnInit() {
    this.AppConfig = APPCONFIG;
    //this.getAccountsForProjectPage();
    let k:Auth =new Auth();
    this.env.auths=[k];
    this.route.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.loadProject(this.id);
      }
      
     
    });
   
    this.thirdFormGroup = this._formBuilder.group({
      nameCtrl: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(25)]],
      urlCtrl:  ['', Validators.required],
      authTypeCtrl:  ['', Validators.required],
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
      this.context = this.project.name + " > Edit";
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  saveEnv() {
    console.log(this.env);
    this.snackbarService.openSnackBar("'Project '" + this.project.name + "' Environment saving...", "");

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

  projectTypes = ['Git', 'GitHub', 'BitBucket', 'GitLab', 'Microsoft_TFS_Git', 'Microsoft_VSTS_Git', 'Local'];
  visibilities = ['PRIVATE', 'ORG_PUBLIC'];
  genPolicies = ['None', 'Create'];
  dbs = ['MySQL', 'Oracle', 'Postgres', 'SQLServer', 'MongoDB'];


}
