import{RegisterComponent}from'./../../dialogs/register/register.component';
import { Component, OnInit, Inject}from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute}from "@angular/router";
import {ProjectService}from '../../../services/project.service';
import {OrgService }from '../../../services/org.service';
import {JobsService}from '../../../services/jobs.service';
import {AccountService}from '../../../services/account.service';
import {Account} from '../../../models/account.model';
import {Project}from '../../../models/project.model';
import {AutoCodeConfig}from '../../../models/project-autocode-config.model';
import {Env, Auth }from '../../../models/project-env.model';
import {Job}from '../../../models/project-job.model';
import {OrgUser}from '../../../models/org.model';
import {Handler} from '../../dialogs/handler/handler';
import {APPCONFIG} from '../../../config';
import {MatSnackBar, MatSnackBarConfig, MatDialog, MatDialogRef, MAT_DIALOG_DATA}from '@angular/material';
import {SnackbarService} from '../../../services/snackbar.service';
import {FormBuilder, FormGroup, Validators , FormArray}from '@angular/forms';

import {MatStepper}from '@angular/material';
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
  env1:any;
  orgs;
  accounts;
  matStepper;// MatStepper;

  context: string = "New";

  isLinear = false;
  thirdFormGroup: FormGroup;

  authTypes = ['Basic', 'Token', 'OAuth_2_0', 'Auth0', 'No_Authentication'];
  authTypesgrantTypes = ['password', 'client_credentials', 'authorization_code', 'implicit'];
  auth0GrantTypes = ['client_credentials', 'password'];
  schemeTypes = ['form', 'header', 'none', 'query'];
  scopeTypes= ['read', 'write'];

orderForm: FormGroup;
items: FormArray;
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
    this.thirdFormGroup = this._formBuilder.group({
      nameCtrl: ['', [Validators.required]],
      urlCtrl:  ['', Validators.required],
     // authTypeCtrl:  ['', Validators.required],
      items: this._formBuilder.array([  ])
    });
    this.route.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.loadProject(this.id);
      }

      if(localStorage.getItem('envClone') === null){
        this.addItem();
      }
       
    });

    if(localStorage.getItem('envClone') != null){
      var envClone = localStorage.getItem("envClone");
      this.env.name = JSON.parse(envClone)['name'] + "_copy" ;
      this.env.baseUrl = JSON.parse(envClone)['baseUrl'];
      this.env.auths = (JSON.parse(envClone)["auths"]);
     // this.addItem1(JSON.parse(envClone)["auths"]);
      let k:Auth = new Auth();
     if(this.env.auths.length==0) this.env.auths=[k];
          for(var i=0;i<this.env.auths.length;i++) this.addItem1(this.env.auths[i]);
    }
    localStorage.removeItem('envClone');



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
createItem1(obj): FormGroup {
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

removeItem(i: number) {
    // remove address from the list
    const control = <FormArray>this.thirdFormGroup.controls['items'];
    control.removeAt(i);
}


  saveEnv(obj) {
    this.env.projectId = this.project.id;
    this.snackbarService.openSnackBar("'Project '" + this.project.name + "' Environment saving...", "");
    this.env.auths=this.items.value;

    this.projectService.saveEnv(this.env, this.project.id).subscribe(results => {
      this.handler.hideLoader();
        if (this.handler.handle(results)) {
        return;
      }
      this.env = results['data'];
      this.snackbarService.openSnackBar("'Project '" + this.project.name + "' Environment saved successfully", "");
      this.router.navigate(['/app/projects' , this.project.id , 'environments']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

}