import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { OrgService } from '../../../services/org.service';
import { Org, OrgUser } from '../../../models/org.model';
import { User } from '../../../models/users.model';


@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
  providers: [OrgService]
})
export class UserListComponent implements OnInit {

  showSpinner: boolean = false;
  org: Org = new Org();
  orgUsers;
  constructor(private orgService: OrgService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getById(params['id']);
        this.getOrgUsersById(params['id']);
      }
    });
  }

  getById(id: string) {
    this.showSpinner = true;
    this.orgService.getById(id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.org = results['data'];
      console.log(this.org);
    }, error => {
      console.log("Unable to fetch org");
    });
  }

  getOrgUsersById(id: string) {
    this.showSpinner = true;
    this.orgService.getOrgUsersById(id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.orgUsers = results['data'];
      console.log(this.orgUsers);
    }, error => {
      console.log("Unable to fetch orgUsers");
    });
  }



}
