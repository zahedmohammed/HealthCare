import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { CloudAccountService } from '../../../services/cloud-account.service';
import { OrgService } from '../../../services/org.service';
import { CloudAccount } from '../../../models/cloud-account.model';
import { Handler } from '../../dialogs/handler/handler';


@Component({
  selector: 'app-cloud-account-new',
  templateUrl: './cloud-account-new.component.html',
  styleUrls: ['./cloud-account-new.component.scss'],
  providers: [CloudAccountService, OrgService]
})
export class CloudAccountNewComponent implements OnInit {

  showSpinner: boolean = false;
  orgs;
  entry: CloudAccount = new CloudAccount();
  cloudTypes = ['AWS','DIGITAL_OCEAN','GCP','AZURE','PRIVATE_CLOUD','VMWARE','OPENSTACK','OTHER'];
  //accountTypes = [ 'VERSION_CONTROL', 'ISSUE_TRACKER', 'CLOUD', 'NOTIFICATION'];
  accountTypes = [ 'AWS', 'Git', 'GitHub', 'BitBucket', 'GitLab', 'Microsoft_TFS_Git', 'Microsoft_VSTS_Git' , 'SLACK' , 'EMAIL' , 'Local' ];
  constructor(private cloudAccountService: CloudAccountService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.getOrgs();
  }

  create() {
    this.handler.activateLoader();
    this.cloudAccountService.create(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.router.navigate(['/app/cloud-accounts']);
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
