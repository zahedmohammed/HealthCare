import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { NotificationService } from '../../../services/notification.service';
import { OrgService } from '../../../services/org.service';
import { NotificationAccount } from '../../../models/notification-account.model';

@Component({
  selector: 'app-notification-new',
  templateUrl: './notification-new.component.html',
  styleUrls: ['./notification-new.component.scss'],
  providers: [NotificationService, OrgService]
})
export class NotificationNewComponent implements OnInit {

  showSpinner: boolean = false;
  orgs;
  entry: NotificationAccount = new NotificationAccount();
  types = ['SLACK','EMAIL'];
  constructor(private notificationService: NotificationService, private orgService: OrgService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.getOrgs();
  }

  create() {
    console.log(this.entry);
    this.showSpinner = true;
    this.notificationService.create(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/notification-accounts']);
    }, error => {
      console.log("Unable to save notification-account entry");
      alert(error);
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
      alert(error);
    });
  }
  visibilities = ['PRIVATE', 'ORG_PUBLIC'];


}

