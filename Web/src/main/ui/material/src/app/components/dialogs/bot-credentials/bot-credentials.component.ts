import {Component, OnInit, Inject} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {Routes, RouterModule, Router, ActivatedRoute}from "@angular/router";
import {AccountService}from '../../../services/account.service';
import {OrgService}from '../../../services/org.service';
import {SnackbarService}from '../../../services/snackbar.service';
import {Account} from '../../../models/account.model';
import {Handler}from '../../dialogs/handler/handler';

@Component({
  selector: 'app-bot-credentials',
  templateUrl: './bot-credentials.component.html',
  styleUrls: ['./bot-credentials.component.scss'],
  providers: [AccountService, OrgService, SnackbarService]

})
export class BotCredentialsComponent implements OnInit {

  entry: Account = new Account();
  accountTypes = ['AWS', 'AZURE', 'GCP','Self_Hosted'];
  AWSREGIONS = ['us-east-1','us-east-2','us-west-1','us-west-2','ca-central-1','eu-central-1','eu-west-1','eu-west-2','eu-west-3','ap-northeast-1','ap-northeast-2','ap-northeast-3','ap-southeast-1','ap-southeast-2','ap-southeast-1','sa-east-1'];
 
  AZUREREGIONS = ['West US', 'West US 2', 'Central US', 'East US', 'East US 2', 'North Central US', 'South Central US','West Central US',
  'Canada Central', 'Canada East', 'Brazil South', 'North Europe', 'West Europe', 'UK South', 'UK West','East Asia', 'South East Asia', 'Japan East', 'Japan West', 'Australia East', 'Australia Southeast', 'Central India', 'South India',
  'West India', 'Korea Central', 'Korea South','China North', 'China East','Germany Central', 'Germany Northeast'];     
 
  GCPREGIONS = ['asia-east1-a', 'asia-east1-b', 'asia-east1-c', 'asia-northeast1-a', 'asia-northeast1-b', 'asia-northeast1-c', 'asia-south1-a', 'asia-south1-b', 'asia-south1-c', 'asia-southeast1-a', 'asia-southeast1-b', 'asia-southeast1-c', 'australia-southeast1-a', 'australia-southeast1-b', 'australia-southeast1-c',
  'europe-north1-a', 'europe-north1-b', 'europe-north1-c', 'europe-west1-b', 'europe-west1-c', 'europe-west1-d', 'europe-west2-a', 'europe-west2-b', 'europe-west2-c', 'europe-west3-a', 'europe-west3-c', 'europe-west3-c', 'europe-west4-a', 'europe-west4-c', 'europe-west4-c', 
  'northamerica-northeast1-a', 'northamerica-northeast1-b', 'northamerica-northeast1-c', 'southamerica-east1-a', 'southamerica-east1-b', 'southamerica-east1-c', 'us-central1-a', 'us-central1-b', 'us-central1-c',
  'us-central1-f', 'us-east1-b', 'us-east1-c', 'us-east1-d', 'us-east4-a', 'us-east4-b', 'us-east4-c', 'us-west1-a', 'us-west1-b', 'us-west1-c', 'us-west2-a', 'us-west2-b', 'us-west2-c'];

  isValid: boolean = true;
  cloudShow: boolean = true;

  constructor(private accountService: AccountService,
    private orgService: OrgService,
    private handler: Handler, 
    private snackbarService: SnackbarService,
    private route: ActivatedRoute, 
    private router: Router,
    public dialogRef: MatDialogRef<BotCredentialsComponent>,
    private dialog: MatDialog) {}

  ngOnInit() {
  }

  create() {
    this.handler.activateLoader();
    this.snackbarService.openSnackBar(this.entry.name + " registering...", "");
    this.accountService.create(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
    this.snackbarService.openSnackBar(this.entry.name + " registered successfully", "");
    this.onClose();
    // this.router.navigateByUrl('/app/projects', {skipLocationChange: true}).then(()=>
    //   this.router.navigate(['/app/projects/new']));
    
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  toggleCloud() {
    this.cloudShow = !this.cloudShow;
  }

  onClose(){
    this.dialogRef.close();
  }

  changeValue(valid: boolean) {
      this.isValid = valid;
  }
}
