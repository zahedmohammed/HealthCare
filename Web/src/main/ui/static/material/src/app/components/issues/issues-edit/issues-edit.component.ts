import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { SkillSubscriptionService } from '../../../services/skill-subscription.service';
import { SkillService } from '../../../services/skill.service';
import { OrgService } from '../../../services/org.service';
import { CloudAccountService } from '../../../services/cloud-account.service';
import { CloudAccount } from '../../../models/cloud-account.model';
import { Subscription } from '../../../models/subscription.model';
import { Base } from '../../../models/base.model';
import { Handler } from '../../dialogs/handler/handler';

@Component({
  selector: 'app-issues-edit',
  templateUrl: './issues-edit.component.html',
  styleUrls: ['./issues-edit.component.scss'],
  providers: [SkillSubscriptionService, SkillService, OrgService]
})
export class IssuesEditComponent implements OnInit {
  skills;
  orgs;
  cloudAccounts;
  showSpinner: boolean = false;
  subscription: Subscription = new Subscription();
  constructor(private skillSubscriptionService: SkillSubscriptionService, private cloudAccountService: CloudAccountService, private skillService: SkillService,
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
    this.skillSubscriptionService.getById(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.subscription = results['data'];
      //console.log(this.subscription);
    }, error => {
      console.log("Unable to fetch vault");
      this.handler.error(error);
    });
  }

  update() {
    this.handler.activateLoader();
    this.skillSubscriptionService.update(this.subscription).subscribe(results => {
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
    this.skillSubscriptionService.deleteITBot(this.subscription).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.router.navigate(['/app/issues']);
    }, error => {
      console.log("Unable to delete subscription");
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
     this.subscription.cloudAccount.accountType =  cloudAccount.accountType;
 }
  visibilities = ['PRIVATE', 'ORG_PUBLIC'];
}
