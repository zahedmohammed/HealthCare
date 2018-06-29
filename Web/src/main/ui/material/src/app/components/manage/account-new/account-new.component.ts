import{Component, OnInit}from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute}from "@angular/router";
import {AccountService}from '../../../services/account.service';
import {OrgService}from '../../../services/org.service';
import {Account} from '../../../models/account.model';
import {Handler}from '../../dialogs/handler/handler';
import {MatSnackBar, MatSnackBarConfig}from '@angular/material';


@Component({
  selector: 'app-account-new',
  templateUrl: './account-new.component.html',
  styleUrls: ['./account-new.component.scss'],
  providers: [AccountService, OrgService]
})
export class AccountNewComponent implements OnInit {

  showSpinner: boolean = false;
  orgs;
  entry: Account = new Account();
  cloudShow: boolean = true;
  config = new MatSnackBarConfig();
  cloudTypes = ['AWS','DIGITAL_OCEAN','GCP','AZURE','PRIVATE_CLOUD','VMWARE','OPENSTACK','OTHER'];
  //accountTypes = [ 'VERSION_CONTROL', 'ISSUE_TRACKER', 'CLOUD', 'NOTIFICATION'];
  accountTypes = [
      '--- Version Control ---', 'Git', 'GitHub', 'BitBucket', 'GitLab', 'Microsoft_TFS_Git', 'Microsoft_VSTS_Git', 'Local',
      '--- Bot Deployment ---', 'AWS', 'Self_Hosted',
      '--- Issue-Trackers ---', 'GitHub', 'Jira',
      '--- Notifications ---', 'Slack' , 'Email'
      ];

  AWSREGIONS = ['us-east-1','us-east-2','us-west-1','us-west-2','ca-central-1','eu-central-1','eu-west-1','eu-west-2','eu-west-3','ap-northeast-1','ap-northeast-2','ap-northeast-3','ap-southeast-1','ap-southeast-2','ap-southeast-1','sa-east-1'];
  constructor(private accountService: AccountService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler, public snackBar: MatSnackBar) { }
  ngOnInit() {
    //this.getOrgs();
  }

  create() {
    this.handler.activateLoader();
    this.accountService.create(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
    this.config.verticalPosition = 'top';
    this.config.horizontalPosition = 'right';
    this.config.duration = 3000;
    this.snackBar.open("Account " + this.entry.name + " Successfully Created", "", this.config);
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

}
