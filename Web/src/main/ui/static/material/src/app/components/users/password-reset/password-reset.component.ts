import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { OrgService } from '../../../services/org.service';
import { Org, OrgUser, Member } from '../../../models/org.model';
import { Handler } from '../../dialogs/handler/handler';

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.scss'],
  providers: [OrgService]
})
export class PasswordResetComponent implements OnInit {

  entry: OrgUser = new OrgUser();
  org: Org = new Org();
  member: Member = new Member();
  constructor(private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

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

  getById(id: string) {
    this.handler.activateLoader();
    this.orgService.getOrgUsers(id).subscribe(results => {
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
    this.orgService.resetPassword(this.org.id, this.entry.users.id, this.member).subscribe(results => {
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


}
