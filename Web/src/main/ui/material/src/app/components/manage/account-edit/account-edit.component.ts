import { Component, OnInit, Inject } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { AccountService } from '../../../services/account.service';
import { OrgService } from '../../../services/org.service';
import { Account } from '../../../models/account.model';
import { Handler } from '../../dialogs/handler/handler';
import { VERSION, MatDialog, MatDialogRef } from '@angular/material';
import { DeleteDialogComponent } from '../../dialogs/delete-dialog/delete-dialog.component';
import { SnackbarService } from '../../../services/snackbar.service';

@Component({
  selector: 'app-account-edit',
  templateUrl: './account-edit.component.html',
  styleUrls: ['./account-edit.component.scss'],
  providers: [AccountService, OrgService, SnackbarService]
})
export class AccountEditComponent implements OnInit {

  showSpinner: boolean = false;
  entry: Account = new Account();
  orgs;
  cloudShow: boolean = true;
  cloudTypes = ['AWS', 'DIGITAL_OCEAN', 'GCP', 'AZURE', 'PRIVATE_CLOUD', 'VMWARE', 'OPENSTACK', 'OTHER'];
  accountTypes = ['Git', 'GitHub', 'Jira', 'FX_Issues', 'BitBucket', 'GitLab', 'Microsoft_TFS_Git', 'Microsoft_VSTS_Git', 'Local', 'AWS', 'Slack', 'Email', 'Self_Hosted'];
  AWSREGIONS = ['us-east-1', 'us-east-2', 'us-west-1', 'us-west-2', 'ca-central-1', 'eu-central-1', 'eu-west-1', 'eu-west-2', 'eu-west-3', 'ap-northeast-1', 'ap-northeast-2', 'ap-northeast-3', 'ap-southeast-1', 'ap-southeast-2', 'ap-southeast-1', 'sa-east-1'];
  constructor(private accountService: AccountService, private orgService: OrgService, private snackbarService: SnackbarService, private route: ActivatedRoute, private router: Router, private handler: Handler, private dialog: MatDialog) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.getById(params['id']);
        //this.getOrgs();
      }
    });
  }

  getById(id: string) {
    this.handler.activateLoader();
    this.accountService.getById(id).subscribe(results => {
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
    this.accountService.update(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.snackbarService.openSnackBar(this.entry.name + " saved successfully", "");
      this.router.navigate(['/app/accounts']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  delete() {
      let dialogRef = this.dialog.open(DeleteDialogComponent, {
          data: this.entry.name + ' credentials'
      });

      dialogRef.afterClosed().subscribe(result => {
          if (result != null) {
              this.handler.activateLoader();
              this.accountService.delete(this.entry).subscribe(results => {
                  this.handler.hideLoader();
                  if (this.handler.handle(results)) {
                      return;
                  }
                  this.snackbarService.openSnackBar(this.entry.name + " deleted successfully", "");
                  this.router.navigate(['/app/accounts']);
              }, error => {
                  this.handler.hideLoader();
                  this.handler.error(error);
              });
          }
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

}
