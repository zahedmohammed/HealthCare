import { Component, OnInit } from '@angular/core';
import { SkillSubscriptionService } from '../../../services/skill-subscription.service';
import { Handler } from '../../dialogs/handler/handler';

@Component({
  selector: 'app-issues-list',
  templateUrl: './issues-list.component.html',
  styleUrls: ['./issues-list.component.scss'],
  providers: [SkillSubscriptionService]
})
export class IssuesListComponent implements OnInit {
  keys;
  showSpinner: boolean = false;
  constructor(private skillSubscriptionService: SkillSubscriptionService, private handler: Handler) { }

  ngOnInit() {
    this.list();
  }

  list() {
    this.handler.activateLoader();
    this.skillSubscriptionService.get("ISSUE_TRACKER", this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.keys = results['data'];
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
