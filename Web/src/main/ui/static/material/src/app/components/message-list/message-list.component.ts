import { Component, OnInit } from '@angular/core';
import { MessageService } from '../../services/message.service';

@Component({
  selector: 'app-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.scss'],
  providers: [MessageService]
})
export class MessageListComponent implements OnInit {
  items;
  showSpinner: boolean = false;
  constructor(private messageService: MessageService) { }

  ngOnInit() {
    this.showSpinner = true;
    this.messageService.get().subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.items = results['data'];
    }, error => {
      console.log("Unable to fetch projects");
      alert(error);
    });
  }

}