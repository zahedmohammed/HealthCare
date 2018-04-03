import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { NotificationService } from '../../../services/notification.service';
import { OrgService } from '../../../services/org.service';
import { NotificationAccount } from '../../../models/notification-account.model';


@Component({
  selector: 'app-notification-edit',
  templateUrl: './notification-edit.component.html',
  styleUrls: ['./notification-edit.component.scss'],
  providers: [NotificationService, OrgService]
})
export class NotificationEditComponent implements OnInit {

  showSpinner: boolean = false;
  entry: NotificationAccount = new NotificationAccount();
  orgs;
  types = ['SLACK','EMAIL'];
  constructor(private notificationService: NotificationService, private orgService: OrgService, private route: ActivatedRoute, private router: Router) { }

    ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getById(params['id']);
        this.getOrgs();
      }
    });
  }

  getById(id: string) {
    this.showSpinner = true;
    this.notificationService.getById(id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.entry = results['data'];
      console.log(this.entry);
    }, error => {
      console.log("Unable to fetch Notification Account");
    });
  }

  update() {
    console.log(this.entry);
    this.notificationService.update(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/notification-accounts']);
    }, error => {
      console.log("Unable to update notificationAccount");
    });
  }

  delete() {
    console.log(this.entry);
    this.notificationService.delete(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/notification-accounts']);
    }, error => {
      console.log("Unable to delete entry");
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


