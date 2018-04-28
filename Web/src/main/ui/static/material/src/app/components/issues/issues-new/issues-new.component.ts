import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { SkillSubscriptionService } from '../../../services/skill-subscription.service';
import { SkillService } from '../../../services/skill.service';
import { CloudAccountService } from '../../../services/cloud-account.service';
import { CloudAccount } from '../../../models/cloud-account.model';
import { OrgService } from '../../../services/org.service';
import { Subscription } from '../../../models/subscription.model';
import { Base } from '../../../models/base.model';
import { Handler } from '../../dialogs/handler/handler';

@Component({
  selector: 'app-issues-new',
  templateUrl: './issues-new.component.html',
  styleUrls: ['./issues-new.component.scss'],
  providers: [SkillSubscriptionService, SkillService, OrgService]
})
export class IssuesNewComponent implements OnInit {
  skills;
  showSpinner: boolean = false;
  orgs;
  cloudAccounts;
  entry: Subscription = new Subscription();
  constructor(private skillSubscriptionService: SkillSubscriptionService, private cloudAccountService: CloudAccountService, private skillService: SkillService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.listSkills();
    this.getOrgs();
    this.getAccountyForIssueTracker();
  }

  listSkills() {
    this.handler.activateLoader();
    this.skillService.getByType('ISSUE_TRACKER').subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.skills = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
  create() {
    this.handler.activateLoader();
    this.skillSubscriptionService.createITBot(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.router.navigate(['/app/issues']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getOrgs() {
    this.handler.activateLoader();
    this.orgService.getByUser().subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.orgs = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getAccountyForIssueTracker() {
    this.handler.activateLoader();
    this.cloudAccountService.getAccountByAccountType('ISSUE_TRACKER').subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.cloudAccounts = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
  setCloudAccount(cloudAccount){
     this.entry.cloudAccount.accountType =  cloudAccount.accountType;
  }

  visibilities = ['PRIVATE', 'ORG_PUBLIC'];

}
