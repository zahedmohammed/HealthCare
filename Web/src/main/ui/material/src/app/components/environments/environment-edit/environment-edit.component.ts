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
import { FormBuilder, FormGroup, Validators , FormArray} from '@angular/forms';

import { MatStepper } from '@angular/material';
@Component({
  selector: 'app-environment-edit',
  templateUrl: './environment-edit.component.html',
  styleUrls: ['./environment-edit.component.scss'],
  providers: [ProjectService, OrgService, SnackbarService, JobsService]
})
export class EnvironmentEditComponent implements OnInit {

  showSpinner: boolean = false;
  id: string;
  envId:string;
  project: Project = new Project();
  autoCodeConfig: AutoCodeConfig = new AutoCodeConfig();
  env: Env = new Env();
  env1:any;
  orgs;
  accounts;
  matStepper;// MatStepper;

  context: string = "Edit";

  isLinear = false;
  thirdFormGroup: FormGroup;

  authTypes = ['Basic', 'OAuth_2_0' , 'Token'];
  authTypesgrantTypes = ['password', 'client_credentials', 'authorization_code', 'implicit'];
  schemeTypes = ['form', 'header', 'none', 'query'];
  scopeTypes= ['read', 'write'];

orderForm: FormGroup;
items: any[] = [];
  public AppConfig: any;
  constructor(private projectService: ProjectService, private accountService: AccountService, private jobsService: JobsService,
            private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler,
            public snackBar: MatSnackBar, private snackbarService: SnackbarService, private _formBuilder: FormBuilder, public dialog: MatDialog) {

  }
  addAuthentication(){
    //this.env.auths.push({});
  }
  ngOnInit() {
    this.AppConfig = APPCONFIG;
    this.getAccountsForProjectPage();
    this.route.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.loadProject(this.id);
      }
      
      this.envId = params['envId'];
      if (this.envId) {
        this.loadEnv(this.id);
      }
    });
   
    this.thirdFormGroup = this._formBuilder.group({
      nameCtrl: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(25)]],
      urlCtrl:  ['', Validators.required],
     // authTypeCtrl:  ['', Validators.required],
items: this._formBuilder.array([  ])
    });

    /*this.fourthFormGroup = this._formBuilder.group({
      usernameCtrl:  ['', Validators.required],
      passwordCtrl:  ['', Validators.required],
    });

    this.fifthFormGroup = this._formBuilder.group({
      header1Ctrl:  ['', Validators.required],
    });

    grantTypeCtrl:  ['', Validators.required],
      clientIdCtrl:  ['', Validators.required],
      clientSecretCtrl:  ['', Validators.required],
      accessTokenUriCtrl:  ['', Validators.required],
      clientAuthenticationSchemeCtrl:  ['', Validators.required],
      authorizationSchemeCtrl:  ['', Validators.required],
      scopeCtrl:  ['', Validators.required]*/

  }
addItem(): void {
  this.items = this.thirdFormGroup.get('items') as FormArray;
  this.items.push(this.createItem());
}
addItem1(obj): void {
  this.items = this.thirdFormGroup.get('items') as FormArray;
  this.items.push(this.createItem1(obj));
}
geInfo(obj){
console.log("sss");
}
createItem(): FormGroup {
  return this._formBuilder.group({
  accessTokenUri:null,
authType: [null, Validators.required],
authorizationScheme:null,
clientAuthenticationScheme:null,
clientId:null,
clientSecret:null,
grantType:null,
header_1:null,
header_2:null,
header_3:null,
id:null,
name:null,
password:null,
preEstablishedRedirectUri:null,
scope:null,
tokenName:null,
useCurrentUri:null,
userAuthorizationUri:null,
username:null
  });
}
createItem1(obj:Auth): FormGroup {
  var k= this._formBuilder.group({
  accessTokenUri:'',
authType: [obj.authType, Validators.required],
authorizationScheme:obj.authorizationScheme,
clientAuthenticationScheme:obj.clientAuthenticationScheme,
clientId:obj.clientId,
clientSecret:obj.clientSecret,
grantType:obj.grantType,
header_1:obj.header_1,
header_2:obj.header_2,
header_3:obj.header_3,
id:obj.id,
name:obj.name,
password:obj.password,
preEstablishedRedirectUri:obj.preEstablishedRedirectUri,
scope:obj.scope,
tokenName:obj.tokenName,
useCurrentUri:obj.useCurrentUri,
userAuthorizationUri:obj.userAuthorizationUri,
username:obj.username
  });
return k;
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
  loadEnv(id: string) {
    this.handler.activateLoader();
    this.projectService.getEnvListByProjectId(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      var envs = results['data'];
      for(var i=0;i<envs.length;i++)
      {
       let a:any;
        a=envs[i];
        if(a!=null)
        if(this.envId=a.id) this.env=a;
      }
     // this.context = this.project.name + " > Edit";
     let k:Auth = new Auth();
     if(this.env.auths.length==0) this.env.auths=[k];
          for(var i=0;i<this.env.auths.length;i++) this.addItem1(this.env.auths[i]);

    }, error => {this.env
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
  save(matStepper) {
    this.matStepper = matStepper;
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
        this.matStepper.next();
        this.getAutoCode();
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
      this.matStepper.next();
      this.getAutoCode();
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
removeItem(i: number) {
    // remove address from the list
    const control = <FormArray>this.thirdFormGroup.controls['items'];
    control.removeAt(i);
}
  saveEnv(obj) {
    console.log(this.env);
    this.snackbarService.openSnackBar("'Project '" + this.project.name + "' Environment saving...", "");
this.env.auths=this.items.value;
    this.projectService.updateEnv(this.env, this.project.id).subscribe(results => {
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

  openDialog() {
    const dialogRef = this.dialog.open(RegisterComponent, {
      width:'50%',
        height:'65%'
    });
    dialogRef.afterClosed().subscribe(result => {
      this.getAccountsForProjectPage();
    });
  }

  projectTypes = ['Git', 'GitHub', 'BitBucket', 'GitLab', 'Microsoft_TFS_Git', 'Microsoft_VSTS_Git', 'Local'];
  visibilities = ['PRIVATE', 'ORG_PUBLIC'];
  genPolicies = ['None', 'Create'];
  dbs = ['MySQL', 'Oracle', 'Postgres', 'SQLServer', 'MongoDB'];


}
