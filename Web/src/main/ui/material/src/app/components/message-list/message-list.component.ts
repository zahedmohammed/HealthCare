import { Component, OnInit } from '@angular/core';
import { MessageService } from '../../services/message.service';
import { Handler } from '../dialogs/handler/handler';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.scss'],
  providers: [MessageService]
})
export class MessageListComponent implements OnInit {
  items;
  showSpinner: boolean = false;
  private _clockSubscription: Subscription;
  constructor(private messageService: MessageService, private handler: Handler) { }

  ngOnInit() {
    this.list();
    let timer = Observable.timer(1, 5000);
    this._clockSubscription = timer.subscribe(t => {
      this.list();
    });
  }

  ngOnDestroy(): void {
    this._clockSubscription.unsubscribe();
  }

  list() {
    this.handler.activateLoader();
    this.messageService.get(this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.items = results['data'];
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