import { Component, OnInit } from '@angular/core';
import { OrgService } from '../../../services/org.service';

@Component({
  selector: 'app-org-list',
  templateUrl: './org-list.component.html',
  styleUrls: ['./org-list.component.scss'],
  providers: [OrgService]
})
export class OrgListComponent implements OnInit {

  orgs;
  showSpinner: boolean = false;
  constructor(private orgService: OrgService) { }

  ngOnInit() {
    this.showSpinner = true;
    this.orgService.get().subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.orgs = results['data'];
    }, error => {
      console.log("Unable to fetch orgs");
    });
  }

}
