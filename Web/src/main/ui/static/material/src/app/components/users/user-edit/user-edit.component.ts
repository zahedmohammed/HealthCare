import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { OrgService } from '../../../services/org.service';
import { Org, OrgUser } from '../../../models/org.model';
import { Handler } from '../../dialogs/handler/handler';


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
    this.showSpinner = true;
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
    this.showSpinner = true;
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
    console.log(this.entry);
    this.orgService.updateOrgUser(this.entry).subscribe(results => {
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
  statuses = ['ACTIVE', 'INACTIVE'];

}
