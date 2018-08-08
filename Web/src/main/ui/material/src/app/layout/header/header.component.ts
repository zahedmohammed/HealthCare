import { Component, OnInit } from '@angular/core';
import { APPCONFIG } from '../../config';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { OrgService } from '../../services/org.service';
import { Org, OrgUser } from '../../models/org.model';
import { Handler } from '../../components/dialogs/handler/handler';
import {Observable} from "rxjs";

@Component({
  selector: 'my-app-header',
  styles: [],
  templateUrl: './header.component.html',
  providers: [OrgService]
})

export class AppHeaderComponent implements OnInit {
  public AppConfig: any;
  entry: OrgUser = new OrgUser();

  constructor(private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.AppConfig = APPCONFIG;
    this.getLoggedInUser();
  //  this.connect();
  }

  getLoggedInUser() {
    this.handler.activateLoader();
    this.orgService.getLoggedInUser().subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.entry = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

 /* connect(): void {
    let source = new EventSource('/api/v1/events/register');
    source.addEventListener('message', message => {
        let event = JSON.parse(message.data);
        console.log(event);
        // TODO - display & update events in the Tasks window.
    });
  }
*/
}
