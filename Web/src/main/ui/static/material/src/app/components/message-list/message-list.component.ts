import { Component, OnInit } from '@angular/core';
import { MessageService } from '../../services/message.service';
import { Handler } from '../dialogs/handler/handler';

@Component({
  selector: 'app-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.scss'],
  providers: [MessageService]
})
export class MessageListComponent implements OnInit {
  items;
  showSpinner: boolean = false;
  constructor(private messageService: MessageService, private handler: Handler) { }

  ngOnInit() {
    this.handler.activateLoader();
    this.messageService.get().subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.items = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

}