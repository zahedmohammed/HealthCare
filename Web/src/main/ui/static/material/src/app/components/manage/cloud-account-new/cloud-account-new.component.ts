import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { CloudAccountService } from '../../../services/cloud-account.service';
import { OrgService } from '../../../services/org.service';
import { CloudAccount } from '../../../models/cloud-account.model';


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
  constructor(private cloudAccountService: CloudAccountService, private orgService: OrgService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.getOrgs();
  }

  create() {
    console.log(this.entry);
    this.showSpinner = true;
    this.cloudAccountService.create(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/cloud-accounts']);
    }, error => {
      console.log("Unable to save cloud-account entry");
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
