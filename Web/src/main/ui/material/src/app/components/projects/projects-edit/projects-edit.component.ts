import { Component, OnInit, Inject } from '@angular/core';
import { MatTabChangeEvent } from '@angular/material';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { ProjectService } from '../../../services/project.service';
import { OrgService } from '../../../services/org.service';
import { AccountService } from '../../../services/account.service';
import { DashboardService } from '../../../services/dashboard.service';
import { TestSuiteService } from '../../../services/test-suite.service';
import { Project } from '../../../models/project.model';
import { Handler } from '../../dialogs/handler/handler';
import { APPCONFIG } from '../../../config';
import { VERSION, MatDialog, MatDialogRef, MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { SnackbarService } from '../../../services/snackbar.service';
import { ProjectSync } from '../../../models/project-sync.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-projects-edit',
  templateUrl: './projects-edit.component.html',
  styleUrls: ['./projects-edit.component.scss'],
  providers: [ProjectService, OrgService, DashboardService, TestSuiteService, SnackbarService]
})
export class ProjectsEditComponent implements OnInit {

  showSpinner: boolean = false;
  orgs;
  project: Project = new Project();
  accounts;
  TestSuite;
  id;
  firstFormGroup: FormGroup;
  projectSync: ProjectSync = new ProjectSync();
  suiteFile;
  suiteFileData;
  public AppConfig: any;
  constructor(private _formBuilder: FormBuilder, private projectService: ProjectService, private accountService: AccountService,
    private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler,
    public dialog: MatDialog, public snackBar: MatSnackBar, private dashboardService: DashboardService, private snackbarService: SnackbarService) { }

  ngOnInit() {
    this.firstFormGroup = this._formBuilder.group({
      nameCtrl: ['', Validators.required],
    });
    this.AppConfig = APPCONFIG;
    this.route.params.subscribe(params => {
      //console.log(params);
      this.id = params['id'];
      // this.getFiles(this.id);
      this.getById(this.id);
    });
  }
  updateProject() {
    if (this.project.id) {
      this.update();
    }
  }

  getById(id: string) {
    this.showSpinner = true;
    this.projectService.getById(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.project = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  update() {
    this.snackbarService.openSnackBar(this.project.name + " updating...", "");
    this.projectService.update(this.project).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.snackbarService.openSnackBar(this.project.name + "  updated successfully", "");
      this.router.navigate(['/app/projects']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  delete() {
    var r = confirm("Are you sure you want to delete this project ?");
      if (r == true) {
    this.handler.activateLoader();
    this.snackbarService.openSnackBar(this.project.name + " deleting...", "");
    this.projectService.delete(this.project).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.snackbarService.openSnackBar(this.project.name + " deleted successfully", "");
      this.router.navigate(['/app/projects']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
  }

  getOrgs() {
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
  getTestSuite(id: string) {
    this.handler.activateLoader();
    this.dashboardService.projectSavings(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.TestSuite = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getFiles(id: string) {
    this.handler.activateLoader();
    this.projectService.getFiles(id, this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.suiteFile = results['data'];
      this.length = results['totalElements'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getFileDetail(fileId: string) {
    this.handler.activateLoader();
    this.projectService.getFilesDetails(this.id, fileId).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.suiteFileData = results['data'];
      var log = this.suiteFileData;
      // this.showDialog(log);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });

  }

  setAccount(account) {
    this.project.account.accountType = account.accountType;
  }

  projectTypes = ['Git', 'GitHub', 'BitBucket', 'GitLab', 'Microsoft_TFS_Git', 'Microsoft_VSTS_Git', 'Local'];
  visibilities = ['PRIVATE', 'ORG_PUBLIC'];
  genPolicies = ['None', 'Create'];

  length = 0;
  page = 0;
  pageSize = 20;
  change(evt) {
    this.page = evt['pageIndex'];
    this.getFiles(this.id);
  }

  // showDialog(log) {
  //     this.dialog.open(SuitDailogComponent, {
  //         width:'50%',
  //         height:'80%',
  //         data: log
  //     });
  // }
  projectSynchronization() {
    this.showSpinner = true;
    this.snackbarService.openSnackBar(this.project.name + " syncing...", "");
    this.projectSync.projectId = this.project.id;
    this.projectService.projectSync(this.projectSync).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
}
