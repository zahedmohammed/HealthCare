import { Component, OnInit } from '@angular/core';
import { SkillSubscriptionService } from '../../../services/skill-subscription.service';

@Component({
  selector: 'app-issues-list',
  templateUrl: './issues-list.component.html',
  styleUrls: ['./issues-list.component.scss'],
  providers: [SkillSubscriptionService]
})
export class IssuesListComponent implements OnInit {
  keys;
  showSpinner: boolean = false;
  constructor(private skillSubscriptionService: SkillSubscriptionService) { }

  ngOnInit() {
    this.showSpinner = true;
    this.skillSubscriptionService.get().subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.keys = results['data'];
    }, error => {
      console.log("Unable to fetch Subscriptions");
    });
  }
}
