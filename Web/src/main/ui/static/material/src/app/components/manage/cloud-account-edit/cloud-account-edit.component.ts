import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { CloudAccountService } from '../../../services/cloud-account.service';
import { OrgService } from '../../../services/org.service';
import { CloudAccount } from '../../../models/cloud-account.model';


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
  constructor(private cloudAccountService: CloudAccountService, private orgService: OrgService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getById(params['id']);
        this.getOrgs();
      }
    });
  }

  getById(id: string) {
    this.showSpinner = true;
    this.cloudAccountService.getById(id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.entry = results['data'];
      console.log(this.entry);
    }, error => {
      console.log("Unable to fetch cloudAccount");
    });
  }

  update() {
    console.log(this.entry);
    this.cloudAccountService.update(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/cloud-accounts']);
    }, error => {
      console.log("Unable to update cloudAccount");
    });
  }

  delete() {
    console.log(this.entry);
    this.cloudAccountService.delete(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/cloud-accounts']);
    }, error => {
      console.log("Unable to delete entry");
    });
  }

  getOrgs() {
    this.orgService.getByUser().subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.orgs = results['data'];
    }, error => {
      console.log("Unable to fetch orgs");
    });
  }

  visibilities = ['PRIVATE', 'ORG_PUBLIC'];

}
