import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { OrgService } from '../../../services/org.service';
import { Org, OrgUser, Member } from '../../../models/org.model';


@Component({
  selector: 'app-user-new',
  templateUrl: './user-new.component.html',
  styleUrls: ['./user-new.component.scss'],
  providers: [OrgService]
})
export class UserNewComponent implements OnInit {

  showSpinner: boolean = false;
  entry: Member = new Member();
  org: Org = new Org();

  constructor(private orgService: OrgService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['orgId']) {
        this.getOrgById(params['orgId']);
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

  create() {
    console.log(this.entry);
    this.entry.orgId = this.org.id;
    this.orgService.addMember(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/orgs', this.org.id, 'users']);
    }, error => {
      console.log("Unable to add member");
    });
  }

  roles = ['ADMIN', 'USER'];
}
