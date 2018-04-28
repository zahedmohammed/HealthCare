import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { NotificationService } from '../../../services/notification.service';
import { CloudAccountService } from '../../../services/cloud-account.service';
import { OrgService } from '../../../services/org.service';
import { CloudAccount } from '../../../models/cloud-account.model';
import { NotificationAccount } from '../../../models/notification-account.model';
import { Handler } from '../../dialogs/handler/handler';


@Component({
  selector: 'app-notification-edit',
  templateUrl: './notification-edit.component.html',
  styleUrls: ['./notification-edit.component.scss'],
  providers: [NotificationService, CloudAccountService, OrgService]
})
export class NotificationEditComponent implements OnInit {

  showSpinner: boolean = false;
  entry: NotificationAccount = new NotificationAccount();
  orgs;
  cloudAccounts;
  types = ['SLACK','EMAIL'];
  constructor(private notificationService: NotificationService, private cloudAccountService: CloudAccountService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

    ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getById(params['id']);
        this.getOrgs();
        this.getAccountyForNotificationHubType();
      }
    });
  }

  getById(id: string) {
    this.handler.activateLoader();
    this.notificationService.getById(id).subscribe(results => {
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
    this.notificationService.update(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.router.navigate(['/app/notification-accounts']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  delete() {
    this.handler.activateLoader();
    this.notificationService.delete(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.router.navigate(['/app/notification-accounts']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getOrgs() {
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

  getAccountyForNotificationHubType() {
    this.handler.activateLoader();
    this.cloudAccountService.getAccountByAccountType('NOTIFICATION_HUB').subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.cloudAccounts = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

 setCloudAccount(cloudAccount){
     this.entry.cloudAccount.accountType =  cloudAccount.accountType;
 }
  visibilities = ['PRIVATE', 'ORG_PUBLIC'];
}


