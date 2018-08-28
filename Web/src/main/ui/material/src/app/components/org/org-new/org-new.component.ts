import { AccountService } from './../../../services/account.service';
import { Account } from './../../../models/account.model';
import { Component, OnInit, Inject } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { OrgService } from '../../../services/org.service';
import { Org, OrgUser, Member } from '../../../models/org.model';
import { Handler } from '../../dialogs/handler/handler';
import {VERSION, MatSnackBar, MatDialog, MatSnackBarConfig, MatDialogRef, MAT_DIALOG_DATA }from '@angular/material';
import {SnackbarService}from '../../../services/snackbar.service';
import { MatStepper } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {APPCONFIG} from '../../../config';

@Component({
  selector: 'app-org-new',
  templateUrl: './org-new.component.html',
  styleUrls: ['./org-new.component.scss'],
  providers: [OrgService, SnackbarService, OrgService]
})
export class OrgNewComponent implements OnInit {

  showSpinner: boolean = false;
  orgs;
  entry: Org = new Org();
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  account: Member = new Member();
  matStepper: MatStepper// MatStepper;@Input()
  isLinear = false;

  // authTypes = ['Basic', 'OAuth_2_0' , 'Token'];
  // grantTypes = ['password', 'client_credentials', 'authorization_code', 'implicit'];
  // schemeTypes = ['form', 'header', 'none', 'query'];
  // scopeTypes= ['read', 'write'];
  cloudShow: boolean = true;
  isValid: boolean = true;

  //cloudTypes = ['AWS','DIGITAL_OCEAN','GCP','AZURE','PRIVATE_CLOUD','VMWARE','OPENSTACK','OTHER'];
  //accountTypes = [ 'VERSION_CONTROL', 'ISSUE_TRACKER', 'CLOUD', 'NOTIFICATION'];
  // accountTypes = [
  //     '--- Version Control ---', 'Git', 'GitHub', 'BitBucket', 'GitLab', 'Microsoft_TFS_Git', 'Microsoft_VSTS_Git', 'Local',
  //     '--- Bot Deployment ---', 'AWS', 'Self_Hosted',
  //     '--- Issue-Trackers ---', 'GitHub', 'Jira',
  //     '--- Notifications ---', 'Slack' , 'Email'
  //     ];

  // AWSREGIONS = ['us-east-1','us-east-2','us-west-1','us-west-2','ca-central-1','eu-central-1','eu-west-1','eu-west-2','eu-west-3','ap-northeast-1','ap-northeast-2','ap-northeast-3','ap-southeast-1','ap-southeast-2','ap-southeast-1','sa-east-1'];

  constructor(private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler, private snackbarService: SnackbarService, private accountService: AccountService, private _formBuilder: FormBuilder) { }

  ngOnInit() {
    this.firstFormGroup = this._formBuilder.group({
      nameCtrl: ['', Validators.required],
      companyCtrl: ['', Validators.required],
      emailCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
    });
  }

  create() {
    this.handler.activateLoader();
    this.snackbarService.openSnackBar(this.entry.name + " creating...", "");
    this.orgService.create(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.snackbarService.openSnackBar(this.entry.name + " created successfully...", "");
      this.entry = results['data'];
      // console.log("Before - this.matStepper.next()");
      // this.matStepper.next();
      // console.log("After - this.matStepper.next()");
      //this.router.navigate(['/app/orgs']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  // createAccount() {
  //   this.handler.activateLoader();
  //   this.snackbarService.openSnackBar(this.account.name + " creating...", "");
  //   this.accountService.create(this.account).subscribe(results => {
  //     this.handler.hideLoader();
  //     if (this.handler.handle(results)) {
  //       return;
  //     }
  //   this.snackbarService.openSnackBar(this.account.name + " created successfully", "");
  //   this.router.navigate(['/app/orgs']);
  //   }, error => {
  //     this.handler.hideLoader();
  //     this.handler.error(error);
  //   });
  // }

  createAccount() {
    this.handler.activateLoader();
    this.snackbarService.openSnackBar(this.account.name + " creating...", "");
    this.account.orgId = this.entry.id;
    this.orgService.addMember(this.account.orgId, this.account).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.snackbarService.openSnackBar(this.account.name + " created successfully", "");
      this.router.navigate(['/app/orgs', this.entry.id, 'users']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  roles = ['USER', 'PROJECT_MANAGER', 'ADMIN'];

//   update() {
//     this.handler.activateLoader();
//     this.snackbarService.openSnackBar(this.account.name + " saving...", "");
//     this.accountService.update(this.account).subscribe(results => {
//         this.handler.hideLoader();
//         if (this.handler.handle(results)) {
//             return;
//         }
//         this.snackbarService.openSnackBar(this.account.name + " saved successfully", "");
//         this.account = results['data'];
//       this.matStepper.next();
//     }, error => {
//         this.handler.hideLoader();
//         this.handler.error(error);
//     });
// }
  
// gotoAccount(matStepper) {
//     this.matStepper = matStepper;
//     if(this.account.id){
//       this.update();
//     }else{
//       this.createAccount();
//     }
//   }

  // toggleCloud() {
  //   this.cloudShow = !this.cloudShow;
  // }

  // visibilities = ['PRIVATE', 'ORG_PUBLIC'];

  //   changeValue(valid: boolean) {
  //       this.isValid = valid;
  //   }
}
