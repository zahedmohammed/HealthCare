import { Component, OnInit } from '@angular/core';
import { APPCONFIG } from '../../config';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { OrgService } from '../../services/org.service';
import { Org, OrgUser } from '../../models/org.model';
import { Handler } from '../../components/dialogs/handler/handler';
import { Observable} from "rxjs";
import * as EventSource from 'eventsource';
import { APPCONFIG } from '../../../config';

@Component({
  selector: 'my-app-header',
  styles: [
    `
    .badge1{
      background-color: red;
    }
    .dropdown {
      display:inline-block;
      margin-left:20px;
      padding:10px;
    }  
  .glyphicon-bell {
     
      font-size:1.5rem;
    }
    .notifications {
     min-width:420px; 
    }
    .notifications-wrapper {
       overflow:auto;
        max-height:250px;
      }
    .menu-title {
       color:#ff7788;
       font-size:1.5rem;
        display:inline-block;
        }
     .glyphicon-circle-arrow-right {
        margin-left:10px;     
     }    
    .notification-heading, .notification-footer  {
     padding:2px 10px;
         }          
  .dropdown-menu.divider {
    margin:5px 0;          
    }  
  .item-title {
    
   font-size:1.3rem;
   color:#000;
      
  }  
  .notifications a.content {
   text-decoration:none;
   background:#ccc;
  
   }      
  .notification-item {
   padding:4px;
   margin:5px;
   background:#ccc;
   border-radius:2px;
   }
   .active{
    color: #555;
    background-color: #e7e7e7;
   }
    `
  ],
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
    //this.connect();
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

  connect() {
    let source = new EventSource('/api/v1/events/register');
    source.addEventListener('message', message => {
      //let event = JSON.parse(message.data);
      console.log('event-------', event);
      // TODO - display & update events in the Tasks window.
    });
  }

}
