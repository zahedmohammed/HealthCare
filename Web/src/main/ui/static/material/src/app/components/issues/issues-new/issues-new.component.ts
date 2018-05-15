import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { IssueTrackerService } from '../../../services/issue-tracker.service';
import { SkillService } from '../../../services/skill.service';
import { AccountService } from '../../../services/account.service';
import { Account } from '../../../models/account.model';
import { OrgService } from '../../../services/org.service';
import { IssueTracker } from '../../../models/issue-tracker.model';
import { Base } from '../../../models/base.model';
import { Handler } from '../../dialogs/handler/handler';

@Component({
  selector: 'app-issues-new',
  templateUrl: './issues-new.component.html',
  styleUrls: ['./issues-new.component.scss'],
  providers: [IssueTrackerService, SkillService, OrgService]
})
export class IssuesNewComponent implements OnInit {
  skills;
  showSpinner: boolean = false;
  orgs;
  accounts;
  entry: IssueTracker = new IssueTracker();
  constructor(private issueTrackerService: IssueTrackerService, private accountService: AccountService, private skillService: SkillService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.listSkills();
    //this.getOrgs();
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
    this.issueTrackerService.createITBot(this.entry).subscribe(results => {
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
    this.accountService.getAccountByAccountType('ISSUE_TRACKER').subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.accounts = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
  setAccount(account){
     this.entry.account.accountType =  account.accountType;
  }

  visibilities = ['PRIVATE', 'ORG_PUBLIC'];

}
