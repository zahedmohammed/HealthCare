import { Component, OnInit } from '@angular/core';
import { NotificationService } from '../../../services/notification.service';
import { Handler } from '../../dialogs/handler/handler';

@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.scss'],
  providers: [NotificationService]
})
export class NotificationListComponent implements OnInit {

  accounts;
  showSpinner: boolean = false;
  constructor(private notificationService: NotificationService, private handler: Handler) { }

  ngOnInit() {
    this.handler.activateLoader();
    this.notificationService.get().subscribe(results => {
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

}
