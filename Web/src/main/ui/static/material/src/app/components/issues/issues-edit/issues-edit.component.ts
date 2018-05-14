import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { IssueTrackerService } from '../../../services/issue-tracker.service';
import { SkillService } from '../../../services/skill.service';
import { OrgService } from '../../../services/org.service';
import { AccountService } from '../../../services/account.service';
import { Account } from '../../../models/account.model';
import { IssueTracker } from '../../../models/issue-tracker.model';
import { Base } from '../../../models/base.model';
import { Handler } from '../../dialogs/handler/handler';

@Component({
  selector: 'app-issues-edit',
  templateUrl: './issues-edit.component.html',
  styleUrls: ['./issues-edit.component.scss'],
  providers: [IssueTrackerService, SkillService, OrgService]
})
export class IssuesEditComponent implements OnInit {
  skills;
  orgs;
  accounts;
  showSpinner: boolean = false;
  entry: IssueTracker = new IssueTracker();
  constructor(private issueTrackerService: IssueTrackerService, private accountService: AccountService, private skillService: SkillService,
    private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getById(params['id']);
        this.getOrgs();
        this.getAccountyForIssueTracker();
      }
    });
    //this.listSkills();
  }

  getById(id: string) {
    this.handler.activateLoader();
    this.issueTrackerService.getById(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.entry = results['data'];
      //console.log(this.entry);
    }, error => {
      console.log("Unable to fetch vault");
      this.handler.error(error);
    });
  }

  update() {
    this.handler.activateLoader();
    this.issueTrackerService.update(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.router.navigate(['/app/issues']);
    }, error => {
      console.log("Unable to update vault");
      this.handler.error(error);
    });
  }

  delete() {
    this.handler.activateLoader();
    this.issueTrackerService.deleteITBot(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.router.navigate(['/app/issues']);
    }, error => {
      console.log("Unable to delete entry");
      this.handler.error(error);
    });
  }

  listSkills() {
    this.handler.activateLoader();
    this.skillService.get().subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.skills = results['data'];
    }, error => {
      console.log("Unable to delete subscription");
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
      console.log("Unable to fetch orgs");
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
