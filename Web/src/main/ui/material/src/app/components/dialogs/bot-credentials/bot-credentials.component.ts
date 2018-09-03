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
  accountTypes = ['AWS', 'Self_Hosted'];
  AWSREGIONS = ['us-east-1','us-east-2','us-west-1','us-west-2','ca-central-1','eu-central-1','eu-west-1','eu-west-2','eu-west-3','ap-northeast-1','ap-northeast-2','ap-northeast-3','ap-southeast-1','ap-southeast-2','ap-southeast-1','sa-east-1'];

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
