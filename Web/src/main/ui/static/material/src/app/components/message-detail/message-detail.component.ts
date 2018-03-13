import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { MessageService } from '../../services/message.service';
import { Message } from '../../models/message.model';

@Component({
  selector: 'app-message-detail',
  templateUrl: './message-detail.component.html',
  styleUrls: ['./message-detail.component.scss'],
  providers: [MessageService]
})
export class MessageDetailComponent implements OnInit {
  item: Message = new Message('','','','','','');
  msg;
  showSpinner: boolean = false;
  constructor(private messageService: MessageService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getById(params['id']);
      }
    });
  }

  getById(id: string) {
    this.showSpinner = true;
    this.messageService.getById(id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.item = results['data'];
      this.msg = this.item.message.replace(new RegExp('\n', 'g'), "<br />");
      console.log(this.item);
    }, error => {
      console.log("Unable to fetch regions");
    });
  }

  filterNl(txt: string) {
    if(txt) {
      return txt.replace(/(?:\r\n|\r|\n)/g, '<br />');
    }
    return txt;
  }


}







