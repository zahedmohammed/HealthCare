import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { OrgService } from '../../../services/org.service';
import { Org, OrgUser, Member } from '../../../models/org.model';
import { Handler } from '../../dialogs/handler/handler';


@Component({
  selector: 'app-user-new',
  templateUrl: './user-new.component.html',
  styleUrls: ['./user-new.component.scss'],
  providers: [OrgService]
})
export class UserNewComponent implements OnInit {

  entry: Member = new Member();
  org: Org = new Org();

  constructor(private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['orgId']) {
        this.getOrgById(params['orgId']);
      }
    });
  }

  getOrgById(id: string) {
    this.handler.activateLoader();
    this.orgService.getById(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.org = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  create() {
    this.handler.activateLoader();
    this.entry.orgId = this.org.id;
    this.orgService.addMember(this.entry.orgId, this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.router.navigate(['/app/orgs', this.org.id, 'users']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  roles = ['ADMIN', 'USER'];
}
