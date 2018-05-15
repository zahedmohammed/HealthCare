import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { AccountService } from '../../../services/account.service';
import { OrgService } from '../../../services/org.service';
import { Account } from '../../../models/account.model';
import { Handler } from '../../dialogs/handler/handler';


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
  cloudTypes = ['AWS','DIGITAL_OCEAN','GCP','AZURE','PRIVATE_CLOUD','VMWARE','OPENSTACK','OTHER'];
  //accountTypes = [ 'VERSION_CONTROL', 'ISSUE_TRACKER', 'CLOUD', 'NOTIFICATION'];
  accountTypes = ['Git', 'GitHub', 'Jira', 'BitBucket', 'GitLab', 'Microsoft_TFS_Git', 'Microsoft_VSTS_Git', 'AWS', 'Slack' , 'Email' , 'Self_Hosted'];
  constructor(private accountService: AccountService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.getOrgs();
  }

  create() {
    this.handler.activateLoader();
    this.accountService.create(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
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
  visibilities = ['PRIVATE', 'ORG_PUBLIC'];


}
