import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { RegionsService } from '../../../services/regions.service';
import { AccountService } from '../../../services/account.service';
import { Account } from '../../../models/account.model';
import { OrgService } from '../../../services/org.service';
import { Region } from '../../../models/regions.model';
import { Handler } from '../../dialogs/handler/handler';
import { MatSnackBar, MatSnackBarConfig, MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { SnackbarService } from '../../../services/snackbar.service';
import { BotCredentialsComponent } from '../../dialogs/bot-credentials/bot-credentials.component';
import { BotDialogComponent } from '../../dialogs/bot-dialog/bot-dialog.component';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-region-new',
  templateUrl: './region-new.component.html',
  styleUrls: ['./region-new.component.scss'],
  providers: [RegionsService, OrgService, SnackbarService]
})
export class RegionNewComponent implements OnInit {

  GCP_REGIONS = ['NORTHAMERICA-NORTHEAST1', 'US-CENTRAL', 'US-EAST1', 'US-EAST4', 'SOUTHAMERICA-EAST1', 'EUROPE-WEST', 'EUROPE-WEST2', 'EUROPE-WEST3', 'ASIA-NORTHEAST1', 'ASIA-SOUTH1', 'AUSTRALIA-SOUTHEAST1'];
  AWS_REGIONS = ['us-east-1', 'us-east-2', 'us-west-1', 'us-west-2', 'ca-central-1', 'eu-central-1', 'eu-west-1', 'eu-west-2', 'eu-west-3', 'ap-northeast-1', 'ap-northeast-2', 'ap-northeast-3', 'ap-southeast-1', 'ap-southeast-2', 'sa-east-1'];
  AZURE_REGIONS = ['West US', 'West US 2', 'Central US', 'East US', 'East US 2', 'North Central US', 'South Central US', 'West Central US', 'Canada Central', 'Canada East', 'Brazil South', 'North Europe', 'West Europe', 'UK South', 'UK West', 'East Asia', 'South East Asia', 'Japan East', 'Japan West', 'Australia East',
    'Australia Southeast', 'Central India', 'South India', 'West India', 'Korea Central', 'Korea South', 'China North', 'China East', 'Germany Central', 'Germany Northeast'];

  regions = [];
  regions1 = [];
  showSpinner: boolean = false;
  accounts;
  cloudAccounts = [];
  cloudAccounts1 = [];
  orgs;
  entry: Region = new Region();
  isLinear = true;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  selectedRegionValue = '';
  clusterValue = [1, 2, 5, 10];
  showRegion = false;
  showCluster = false;
  i: number = 0;
  r: number = 0;
  nextAccountFlag: boolean = false;
  prevAccountFlag: boolean = true;
  nextRegionFlag: boolean = false;
  prevRegionFlag: boolean = true;
  newAccount: boolean = false;

  constructor(private regionsService: RegionsService, private accountService: AccountService, private orgService: OrgService,
    private route: ActivatedRoute, private router: Router, private handler: Handler, private snackbarService: SnackbarService,
    public dialog: MatDialog, private _formBuilder: FormBuilder) {
    dialog.afterAllClosed.subscribe(() => {
      this.getAccountForExecutionBotPage();
    })
  }

  ngOnInit() {
    this.getAccountForExecutionBotPage();
    //this.getOrgs();
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      accountCtrl: ['', Validators.required],
      regionCtrl: ['', Validators.required]
    });
  }

  create() {
    this.handler.activateLoader();
    this.snackbarService.openSnackBar(this.entry.name + " creating...", "");
    this.regionsService.create(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.entry = results['data'];
      console.log('id', this.entry.id);
      var scriptlog = this.entry.manualScript;
      if (scriptlog != null) {
        const dialogRef = this.dialog.open(BotDialogComponent, {
          width: '800px',
          data: scriptlog
        });
        dialogRef.afterClosed().subscribe(result => {
        });
        this.snackbarService.openSnackBar(this.entry.name + " created successfully", "");
        this.router.navigate(['/app/regions', this.entry.id]);
      }
      else {
        this.snackbarService.openSnackBar(this.entry.name + " created successfully", "");
        this.router.navigate(['/app/regions']);
      }
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  manualDeploy() {
    this.handler.activateLoader();
    let account = this.getSelHostedAccountForExecutionBotPage();
    this.entry.account = account;
    this.snackbarService.openSnackBar(this.entry.name + " creating...", "");
    this.regionsService.create(this.entry).subscribe(results => {
      this.handler.hideLoader();
      this.entry = results['data'];
      console.log('id', this.entry.id);
      var scriptlog = this.entry.manualScript;
      if (scriptlog != null) {
        const dialogRef = this.dialog.open(BotDialogComponent, {
          width: '800px',
          data: scriptlog
        });
        dialogRef.afterClosed().subscribe(result => {
        });
        this.snackbarService.openSnackBar(this.entry.name + " created successfully", "");
        this.router.navigate(['/app/regions', this.entry.id]);
      }
      else {
        this.snackbarService.openSnackBar(this.entry.name + " created successfully", "");
        this.router.navigate(['/app/regions']);
      }
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getRegions(event, accountSelected) {
    this.showRegion = true;
    this.entry.account = accountSelected
    if (accountSelected.accountType === 'GCP') {
      this.regions = this.GCP_REGIONS;
    } else
      if (accountSelected.accountType === 'AWS') {
        if (accountSelected.allowedRegions.length > 0) {
          this.regions = accountSelected.allowedRegions;
        } else {
          this.regions = this.AWS_REGIONS;
        }
      } else
        if (accountSelected.accountType === 'AZURE') {
          if (accountSelected.allowedRegions.length > 0) {
            this.regions = accountSelected.allowedRegions;
          }
          else {
            this.regions = this.AZURE_REGIONS;
          }
        }

    for (this.r = 0; this.r < 4 && this.r < this.regions.length; this.r++) {
      this.regions1[this.r] = this.regions[this.r]
    }
    if (this.regions.length <= 4)
      this.nextRegionFlag = true;
  }

  getCloudAccounts() {
    this.handler.activateLoader();
    this.accountService.get(0, 100).subscribe(results => {
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

  getAccountForExecutionBotPage() {
    this.handler.activateLoader();
    this.accountService.getAccountByAccountType('BOT_HUB').subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.accounts = results['data'];
      if (this.accounts != null) {
        this.cloudAccounts = [];
        this.cloudAccounts1 = [];
        this.getCloudAccountForExecutionBotPage();
      }
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getSelHostedAccountForExecutionBotPage() {
    for (let entry of this.accounts) {
      if (entry.accountType == 'Self_Hosted') {
        return entry;
      }
    }
  }

  getCloudAccountForExecutionBotPage() {
    for (let entry of this.accounts) {
      if (entry.accountType != 'Self_Hosted') {
        this.cloudAccounts.push(entry);
      }
    }
    this.cloudAccounts.push('newButton');

    for (this.i = 0; this.i < 4 && this.i < this.cloudAccounts.length; this.i++) {
      this.cloudAccounts1[this.i] = this.cloudAccounts[this.i]
    }
    if (this.cloudAccounts.length <= 4)
      this.nextAccountFlag = true;
  }

  setAccount(account_) {
    this.entry.account.accountType = account_.accountType;
  }

  openDialog() {
    const dialogRef = this.dialog.open(BotCredentialsComponent, {
      width: '800px'
    });
    dialogRef.afterClosed().subscribe(result => {
      this.getAccountForExecutionBotPage();
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
  visibilities = ['PRIVATE', 'PUBLIC'];

  enable() {
    this.isLinear = false;
  }
  selectedRegion(event, selectedRegionValue) {
    this.showCluster = true;
    this.selectedRegionValue = selectedRegionValue
    this.entry.region = selectedRegionValue
  }
  selectedCluster(event, value) {
    this.entry.min = value
  }

  nextAccount() {
    this.cloudAccounts1 = [];
    if (this.i < 0) {
      for (this.i = 0; this.i < 4 && this.i < this.cloudAccounts.length;) {
        this.i++
      }
    }
    if (this.cloudAccounts.length > this.i) {
      for (var k = 0; k < 4 && k < this.cloudAccounts.length && this.i < this.cloudAccounts.length; k++) {
        this.cloudAccounts1[k] = this.cloudAccounts[this.i]
        this.i++
      }
    }
    if (this.cloudAccounts.length == this.i) {
      this.nextAccountFlag = true;

    }
    if (this.i > 4)
      this.prevAccountFlag = false;
  }

  previousAccount() {
    this.i = this.i - this.cloudAccounts1.length
    this.i--;
    this.cloudAccounts1 = [];
    for (var k = 3; k >= 0 && k < this.cloudAccounts.length && this.i >= 0; k--) {
      this.cloudAccounts1[k] = this.cloudAccounts[this.i]
      this.i--
    }
    if (this.cloudAccounts.length == this.i - 1)
      this.prevAccountFlag = true;
    if (this.i <= 0)
      this.prevAccountFlag = true;
    if (this.cloudAccounts.length > 4)
      this.nextAccountFlag = false;
    this.i = this.i + 5
  }

  nextRegion() {
    this.regions1 = [];
    if (this.r < 0) {
      for (this.r = 0; this.r < 4 && this.r < this.regions.length;) {
        this.r++
      }
    }
    if (this.regions.length > this.r) {
      for (var k = 0; k < 4 && k < this.regions.length && this.r < this.regions.length; k++) {
        this.regions1[k] = this.regions[this.r]
        this.r++
      }
    }
    if (this.regions.length == this.r) {
      this.nextRegionFlag = true;
    }
    if (this.r > 4)
      this.prevRegionFlag = false;
  }

  previousRegion() {
    this.r = this.r - this.regions1.length
    this.r--;
    this.regions1 = [];
    for (var k = 3; k >= 0 && k < this.cloudAccounts.length && this.r >= 0; k--) {
      this.regions1[k] = this.regions[this.r]
      this.r--
    }
    if (this.regions.length == this.r - 1)
      this.prevRegionFlag = true;
    if (this.r <= 0)
      this.prevRegionFlag = true;
    if (this.regions.length > 4)
      this.nextRegionFlag = false;
    this.r = this.r + 5
  }

}

