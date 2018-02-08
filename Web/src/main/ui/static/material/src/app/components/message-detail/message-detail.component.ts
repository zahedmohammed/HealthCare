import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { MessageService } from '../../services/message.service';

@Component({
  selector: 'app-message-detail',
  templateUrl: './message-detail.component.html',
  styleUrls: ['./message-detail.component.scss'],
  providers: [MessageService]
})
export class MessageDetailComponent implements OnInit {
  item;
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
      console.log(this.item);
    }, error => {
      console.log("Unable to fetch regions");
    });
  }


}






