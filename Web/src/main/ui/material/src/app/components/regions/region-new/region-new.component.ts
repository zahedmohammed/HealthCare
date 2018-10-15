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
  //AWS_REGIONS = ['US-WEST-1'];
  AZURE_REGIONS = ['eastus', 'eastus2', 'westus', 'westus2', 'canadacentral', 'canadaeast', 'northeurope', 'westeurope', 'uksouth', 'ukwest', 'eastasia', 'southeastasia', 'japanwest', 'australiaeast', 'australiasoutheast', 'centralindia', 'southindia', 'westindia', 'koreacentral'];

  regions = [];
  showSpinner: boolean = false;
  accounts;
  cloudAccounts = [];
  orgs;
  entry: Region = new Region();
  isLinear = true;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  selectedRegionValue = '';

  constructor(private regionsService: RegionsService, private accountService: AccountService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler, private snackbarService: SnackbarService, public dialog: MatDialog, private _formBuilder: FormBuilder) { }

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

  selectedRegion(event, selectedRegionValue) {
    this.selectedRegionValue = selectedRegionValue
    this.entry.region = selectedRegionValue

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
    accountSelected.active = !accountSelected.active;
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
    this.cloudAccounts = [];
    for (let entry of this.accounts) {
      if (entry.accountType != 'Self_Hosted') {
        this.cloudAccounts.push(entry);
      }

    }

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
}

