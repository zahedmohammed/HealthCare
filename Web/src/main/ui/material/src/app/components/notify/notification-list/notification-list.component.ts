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
    this.list();
  }

  list() {
    this.handler.activateLoader();
    this.notificationService.get(this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.accounts = results['data'];
      this.length = results['totalElements'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  length = 0;
  page = 0;
  pageSize = 20;
  change(evt) {
    this.page = evt['pageIndex'];
    this.list();
  }

}
