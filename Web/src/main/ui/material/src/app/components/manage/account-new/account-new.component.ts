import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { AccountService } from '../../../services/account.service';
import { OrgService } from '../../../services/org.service';
import { Account } from '../../../models/account.model';
import { Handler } from '../../dialogs/handler/handler';
import { VERSION, MatDialog, MatDialogRef } from '@angular/material';
import { SnackbarService } from '../../../services/snackbar.service';

export interface AccountType{
value: string;
viewValue: string;
}

export interface AccountTypeGroup{
disabled?: boolean;
name: string;
accountType: AccountType[];
}

@Component({
  selector: 'app-account-new',
  templateUrl: './account-new.component.html',
  styleUrls: ['./account-new.component.scss'],
  providers: [AccountService, OrgService, SnackbarService]
})
export class AccountNewComponent implements OnInit {

  showSpinner: boolean = false;
  orgs;
  entry: Account = new Account();
  cloudShow: boolean = true;
  isValid: boolean = true;
  cloudTypes = ['AWS', 'DIGITAL_OCEAN', 'GCP', 'AZURE', 'PRIVATE_CLOUD', 'VMWARE', 'OPENSTACK', 'OTHER'];
  //accountTypes = [ 'VERSION_CONTROL', 'ISSUE_TRACKER', 'CLOUD', 'NOTIFICATION'];

  accountTypeGroup: AccountTypeGroup[]= [
  {
      name: 'Version Control',
      accountType: [
        {value: 'Git', viewValue: 'Git'},
        {value: 'GitHub', viewValue: 'GitHub'},
        {value: 'BitBucket', viewValue: 'BitBucket'},
        {value: 'GitLab', viewValue: 'GitLab'},
        {value: 'Microsoft_TFS_Git', viewValue: 'Microsoft_TFS_Git'},
        {value: 'Microsoft_VSTS_Git', viewValue: 'Microsoft_VSTS_Git'},
        {value: 'Local', viewValue: 'Local'}
      ]
    },
    {
      name: 'Bot Deployment',
      accountType: [
        {value: 'AWS', viewValue: 'AWS'},
        {value: 'AZURE', viewValue: 'AZURE'},
        {value: 'Self_Hosted', viewValue: 'Self_Hosted'}
      ]
    },
    {
      name: 'Issue Trackers',
      accountType: [
        {value: 'FX_Issues', viewValue: 'FX_Issues'},
        {value: 'GitHub', viewValue: 'GitHub'},
        {value: 'Jira', viewValue: 'Jira'}
      ]
    },
    {
      name: 'Notifications',
      accountType: [
        {value: 'Slack', viewValue: 'Slack'},
        {value: 'Email', viewValue: 'Email'},
      ]
    }
  ];

  AWSREGIONS = ['us-east-1', 'us-east-2', 'us-west-1', 'us-west-2', 'ca-central-1', 'eu-central-1', 'eu-west-1', 'eu-west-2', 'eu-west-3', 'ap-northeast-1', 'ap-northeast-2', 'ap-northeast-3', 'ap-southeast-1', 'ap-southeast-2', 'ap-southeast-1', 'sa-east-1'];
  AZUREREGIONS = ['West US', 'West US 2', 'Central US', 'East US', 'East US 2', 'North Central US', 'South Central US','West Central US',
    'Canada Central', 'Canada East', 'Brazil South', 'North Europe', 'West Europe', 'UK South', 'UK West','East Asia', 'South East Asia', 'Japan East', 'Japan West', 'Australia East', 'Australia Southeast', 'Central India', 'South India',
    'West India', 'Korea Central', 'Korea South','China North', 'China East','Germany Central', 'Germany Northeast'];
  constructor(private accountService: AccountService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler, private snackbarService: SnackbarService) { }
  ngOnInit() {
    //this.getOrgs();
  }

  create() {
    this.handler.activateLoader();
    this.snackbarService.openSnackBar(this.entry.name + " creating...", "");
    this.accountService.create(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.snackbarService.openSnackBar(this.entry.name + " created successfully", "");
      this.router.navigate(['/app/accounts']);
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

  toggleCloud() {
    this.cloudShow = !this.cloudShow;
  }

  visibilities = ['PRIVATE', 'ORG_PUBLIC'];

  changeValue(valid: boolean) {
    this.isValid = valid;
  }

}
