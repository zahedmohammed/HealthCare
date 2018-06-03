import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { OrgService } from '../../../services/org.service';
import { Org, OrgUser } from '../../../models/org.model';
import { Handler } from '../../dialogs/handler/handler';


@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss'],
  providers: [OrgService]
})
export class UserProfileComponent implements OnInit {

  entry: OrgUser = new OrgUser();
  constructor(private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.getLoggedInUser();
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

}
