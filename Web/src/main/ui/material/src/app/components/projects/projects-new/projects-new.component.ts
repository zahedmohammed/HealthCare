import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { ProjectService } from '../../../services/project.service';
import { OrgService } from '../../../services/org.service';
import { AccountService } from '../../../services/account.service';
import { Account } from '../../../models/account.model';
import { Project } from '../../../models/project.model';
import { OrgUser } from '../../../models/org.model';
import { Handler } from '../../dialogs/handler/handler';
import { APPCONFIG } from '../../../config';

@Component({
  selector: 'app-projects-new',
  templateUrl: './projects-new.component.html',
  styleUrls: ['./projects-new.component.scss'],
  providers: [ProjectService, OrgService]
})
export class ProjectsNewComponent implements OnInit {

  showSpinner: boolean = false;
  project: Project = new Project();
  orgs;
  accounts;
  public AppConfig: any;
  constructor(private projectService: ProjectService, private accountService: AccountService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) {
    //this.project.genPolicy = "None";
  }

  ngOnInit() {
    this.AppConfig = APPCONFIG;
    //this.getOrgs();
    this.getAccountsForProjectPage();
  }

  create() {
    this.handler.activateLoader();
    this.projectService.create(this.project).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.router.navigate(['/app/projects']);
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

  getAccountsForProjectPage() {
    this.handler.activateLoader();
    this.accountService.getAccountByAccountType('PROJECT').subscribe(results => {
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
     this.project.account.accountType =  account.accountType;
  }

  projectTypes = ['Git', 'GitHub', 'BitBucket', 'GitLab', 'Microsoft_TFS_Git', 'Microsoft_VSTS_Git', 'Local'];
  visibilities = ['PRIVATE', 'ORG_PUBLIC'];
  genPolicies = ['None', 'Create'];

}
