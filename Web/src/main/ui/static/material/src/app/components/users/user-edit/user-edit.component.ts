import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { OrgService } from '../../../services/org.service';
import { Org, OrgUser } from '../../../models/org.model';


@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss'],
  providers: [OrgService]
})
export class UserEditComponent implements OnInit {

  showSpinner: boolean = false;
  entry: OrgUser = new OrgUser();
  org: Org = new Org();
  orgs;
  constructor(private orgService: OrgService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['orgId']) {
        this.getOrgById(params['orgId']);
      }
      if (params['id']) {
        this.getById(params['id']);
      }
    });
  }

  getOrgById(id: string) {
    this.showSpinner = true;
    this.orgService.getById(id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.org = results['data'];
      console.log(this.entry);
    }, error => {
      console.log("Unable to fetch org");
    });
  }

  getById(id: string) {
    this.showSpinner = true;
    this.orgService.getOrgUsers(id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.entry = results['data'];
      console.log(this.entry);
    }, error => {
      console.log("Unable to fetch OrgUser");
    });
  }

  update() {
    console.log(this.entry);
    this.orgService.updateOrgUser(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/orgs']);
    }, error => {
      console.log("Unable to update OrgUser");
    });
  }

  roles = ['ADMIN', 'USER'];
  statuses = ['ACTIVE', 'INACTIVE'];

}
