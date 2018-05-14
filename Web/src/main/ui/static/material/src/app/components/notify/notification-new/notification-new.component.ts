import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { NotificationService } from '../../../services/notification.service';
import { AccountService } from '../../../services/account.service';
import { OrgService } from '../../../services/org.service';
import { Notification } from '../../../models/notification.model';
import { Account } from '../../../models/account.model';
import { Handler } from '../../dialogs/handler/handler';

@Component({
  selector: 'app-notification-new',
  templateUrl: './notification-new.component.html',
  styleUrls: ['./notification-new.component.scss'],
  providers: [NotificationService, AccountService, OrgService]
})
export class NotificationNewComponent implements OnInit {

  showSpinner: boolean = false;
  orgs;
  accounts;
  entry: Notification = new Notification();
  types = ['SLACK','EMAIL'];
  constructor(private notificationService: NotificationService, private accountService: AccountService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.getOrgs();
    this.getAccountyForNotificationHubType();
  }

  create() {
    this.handler.activateLoader();
    this.showSpinner = true;
    this.notificationService.create(this.entry).subscribe(results => {
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
    this.handler.activateLoader();
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
    this.accountService.getAccountByAccountType('NOTIFICATION_HUB').subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.accounts = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

 setAccount(account){
     this.entry.account.accountType =  account.accountType;
 }
  visibilities = ['PRIVATE', 'ORG_PUBLIC'];


}

