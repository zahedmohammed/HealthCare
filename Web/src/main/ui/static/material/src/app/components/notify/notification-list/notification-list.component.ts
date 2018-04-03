import { Component, OnInit } from '@angular/core';
import { NotificationService } from '../../../services/notification.service';

@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.scss'],
  providers: [NotificationService]
})
export class NotificationListComponent implements OnInit {

  accounts;
  showSpinner: boolean = false;
  constructor(private notificationService: NotificationService) { }

  ngOnInit() {
    this.showSpinner = true;
    this.notificationService.get().subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.accounts = results['data'];
    }, error => {
      console.log("Unable to fetch accounts");
    });
  }

}
