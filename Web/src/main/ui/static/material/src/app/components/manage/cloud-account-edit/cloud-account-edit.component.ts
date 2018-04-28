import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { CloudAccountService } from '../../../services/cloud-account.service';
import { OrgService } from '../../../services/org.service';
import { CloudAccount } from '../../../models/cloud-account.model';
import { Handler } from '../../dialogs/handler/handler';


@Component({
  selector: 'app-cloud-account-edit',
  templateUrl: './cloud-account-edit.component.html',
  styleUrls: ['./cloud-account-edit.component.scss'],
  providers: [CloudAccountService, OrgService]
})
export class CloudAccountEditComponent implements OnInit {

  showSpinner: boolean = false;
  entry: CloudAccount = new CloudAccount();
  orgs;
  cloudTypes = ['AWS','DIGITAL_OCEAN','GCP','AZURE','PRIVATE_CLOUD','VMWARE','OPENSTACK','OTHER'];
  accountTypes = [ 'AWS', 'BITBUCKET' , 'GITHUB', 'GIT', 'MERCURIAL' , 'SVN' , 'SLACK'];
  constructor(private cloudAccountService: CloudAccountService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.getById(params['id']);
        this.getOrgs();
      }
    });
  }

  getById(id: string) {
    this.handler.activateLoader();
    this.cloudAccountService.getById(id).subscribe(results => {
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
    this.cloudAccountService.update(this.entry).subscribe(results => {
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

  delete() {
    this.handler.activateLoader();
    this.cloudAccountService.delete(this.entry).subscribe(results => {
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
