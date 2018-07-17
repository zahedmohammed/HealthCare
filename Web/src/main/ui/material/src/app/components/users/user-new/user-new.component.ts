import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { OrgService } from '../../../services/org.service';
import { Org, OrgUser, Member } from '../../../models/org.model';
import { Handler } from '../../dialogs/handler/handler';
import {SnackbarService}from '../../../services/snackbar.service';
import {VERSION, MatDialog, MatDialogRef }from '@angular/material';

@Component({
  selector: 'app-user-new',
  templateUrl: './user-new.component.html',
  styleUrls: ['./user-new.component.scss'],
  providers: [OrgService, SnackbarService]
})
export class UserNewComponent implements OnInit {

  entry: Member = new Member();
  org: Org = new Org();

  constructor(private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler, private snackbarService: SnackbarService) { }

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
    this.snackbarService.openSnackBar(this.entry.name + " creating...", "");
    this.entry.orgId = this.org.id;
    this.orgService.addMember(this.entry.orgId, this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.snackbarService.openSnackBar(this.entry.name + " created successfully", "");
      this.router.navigate(['/app/orgs', this.org.id, 'users']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  roles = ['USER', 'PROJECT_MANAGER', 'ADMIN'];
}
