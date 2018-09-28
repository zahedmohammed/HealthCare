import { Component, OnInit } from '@angular/core';
import { MatTabChangeEvent } from '@angular/material';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { RegionsService } from '../../../services/regions.service';
import { OrgService } from '../../../services/org.service';
import { AccountService } from '../../../services/account.service';
import { DashboardService } from '../../../services/dashboard.service';
import { SystemSettingService } from '../../../services/system-setting.service';
import { Region } from '../../../models/regions.model';
import { Saving } from '../../../models/system-setting.model';
import { Handler } from '../../dialogs/handler/handler';
import { DeleteDialogComponent } from '../../dialogs/delete-dialog/delete-dialog.component';
import { VERSION, MatDialog, MatDialogRef } from '@angular/material';
import { SnackbarService } from '../../../services/snackbar.service';
import { BotSavingDialogComponent } from '../../dialogs/bot-saving-dialog/bot-saving-dialog.component';
@Component({
  selector: 'app-regions-edit',
  templateUrl: './region-edit.component.html',
  styleUrls: ['./region-edit.component.scss'],
  providers: [RegionsService, OrgService, SystemSettingService, DashboardService, SnackbarService]
})
export class RegionEditComponent implements OnInit {


  GCP_REGIONS = ['NORTHAMERICA-NORTHEAST1', 'US-CENTRAL', 'US-EAST1', 'US-EAST4', 'SOUTHAMERICA-EAST1', 'EUROPE-WEST', 'EUROPE-WEST2', 'EUROPE-WEST3', 'ASIA-NORTHEAST1', 'ASIA-SOUTH1', 'AUSTRALIA-SOUTHEAST1'];
  //AWS_REGIONS = ['US-EAST-1','US-EAST-2','US-WEST-1','US-WEST-2','CA-CENTRAL-1','EU-CENTRAL-1','EU-WEST-1','EU-WEST-2','EU-WEST-3','AP-NORTHEAST-1','AP-NORTHEAST-2','AP-NORTHEAST-3','AP-SOUTHEAST-1','AP-SOUTHEAST-2','AP-SOUTH-1','SA-EAST-1'];
  AWS_REGIONS = ['US-WEST-1'];
  AZURE_REGIONS = [];
  regions = [];
  showSpinner: boolean = false;
  accounts;
  entry: Region = new Region();
  orgs;
  id;
  saving: Saving;
  constructor(private regionsService: RegionsService, private accountService: AccountService, private orgService: OrgService, private dashboardService: DashboardService,
    private route: ActivatedRoute, private router: Router, private handler: Handler,
    public dialog: MatDialog, private snackbarService: SnackbarService, private systemSettingService: SystemSettingService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.id = params['id'];
        this.getById(params['id']);
        //this.getOrgs();
        //this.getAccountForExecutionBotPage();
      }
    });
    this.getSavings(this.id);

  }

  tabChanged = (tabChangeEvent: MatTabChangeEvent): void => {
    console.log('index => ', tabChangeEvent.index);
    if (tabChangeEvent.index === 0) {
      this.getById(this.id);
    } else if (tabChangeEvent.index === 1) {
      this.getSavings(this.id);
    }
  }

  getById(id: string) {
    this.handler.activateLoader();
    this.regionsService.getById(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.entry = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  update() {
    this.handler.activateLoader();
    this.snackbarService.openSnackBar(this.entry.name + " saving...", "");
    this.regionsService.update(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.snackbarService.openSnackBar(this.entry.name + " saved successfully", "");
      this.router.navigate(['/app/regions']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  delete() {
      var r = confirm("Are you sure you want to delete this test bot?");
      if (r == true) {
          this.snackbarService.openSnackBar(this.entry.name + " deleting...", "");
          this.handler.activateLoader();
          this.regionsService.delete(this.entry).subscribe(results => {
          this.handler.hideLoader();
          if (this.handler.handle(results)) {
            return;
          }
          this.snackbarService.openSnackBar(this.entry.name + " deleted successfully", "");
          this.router.navigate(['/app/regions']);
        }, error => {
          this.handler.hideLoader();
          this.handler.error(error);
        });
      }
  }

  ping() {
    this.handler.activateLoader();
    this.regionsService.ping(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      alert(results['data']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getRegions() {
    if (this.entry.account.cloudType === 'GCP') {
      this.regions = this.GCP_REGIONS;
    } else
      if (this.entry.account.cloudType === 'AWS') {
        this.regions = this.AWS_REGIONS;
      } else
        if (this.entry.account.cloudType === 'AZURE') {
          this.regions = this.AZURE_REGIONS;
        }
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

  getSavings(id: string) {
    this.handler.activateLoader();
    this.dashboardService.botSavings(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.saving = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
  openDialog() {
    const dialogRef = this.dialog.open(BotSavingDialogComponent, {
      width: '800px',
      data: this.saving
    });
    dialogRef.afterClosed().subscribe(result => {
    });
  }

  visibilities = ['PRIVATE', 'PUBLIC'];

}



