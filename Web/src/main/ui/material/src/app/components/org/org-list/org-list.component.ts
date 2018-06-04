import { Component, OnInit } from '@angular/core';
import { OrgService } from '../../../services/org.service';
import { Handler } from '../../dialogs/handler/handler';

@Component({
  selector: 'app-org-list',
  templateUrl: './org-list.component.html',
  styleUrls: ['./org-list.component.scss'],
  providers: [OrgService]
})
export class OrgListComponent implements OnInit {

  orgs;
  showSpinner: boolean = false;
  constructor(private orgService: OrgService, private handler: Handler) { }

  ngOnInit() {
    this.list();
  }

  list() {
    this.handler.activateLoader();
    this.orgService.get(this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.orgs = results['data'];
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