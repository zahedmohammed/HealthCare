import { Component, OnInit, Inject } from '@angular/core';
import { MatTabChangeEvent}from '@angular/material';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { JobsService } from '../../../services/jobs.service';
import { ProjectService } from '../../../services/project.service';
import { Project } from '../../../models/project.model';
import { Jobs, Noti, Cron } from '../../../models/jobs.model';
import { Env, Auth } from '../../../models/project-env.model';
import { AccountService } from '../../../services/account.service';
import { Account } from '../../../models/account.model';
import { Handler } from '../../dialogs/handler/handler';
import { APPCONFIG } from '../../../config';
import { VERSION, MatDialog, MatDialogRef, MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { DeleteDialogComponent}from '../../dialogs/delete-dialog/delete-dialog.component';
import { SnackbarService}from '../../../services/snackbar.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material';
import { TestSuite} from '../../../models/test-suite.model';



@Component({
  selector: 'app-test-suite-new',
  templateUrl: './test-suite-new.component.html',
  styleUrls: ['./test-suite-new.component.scss'],
  providers: [ProjectService, SnackbarService,]
})
export class TestSuiteNewComponent implements OnInit {
  id: string;
  project: Project = new Project();
  testSuite: TestSuite = new TestSuite();


  types: string[] = ["Suite", "Abstract", "Dataset", "Consulting_Services", "AI_Skills"];


  context: string = "New";
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;

  constructor(private projectService: ProjectService,
            private route: ActivatedRoute, private router: Router, private handler: Handler,
            public snackBar: MatSnackBar, private snackbarService: SnackbarService, private _formBuilder: FormBuilder) { }

  ngOnInit() {

    this.route.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.loadProject(this.id);
     //   this.getEnvs();
     //   this.getITAccounts();
     //   this.getNotifyAccounts();
      }
    });
    this.thirdFormGroup = this._formBuilder.group({
 nameCtrl:  ['', Validators.required],
    });

  }

  loadProject(id: string) {
    this.handler.activateLoader();
    this.projectService.getById(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.project = results['data'];
      //this.job['project'] = this.project;
     // this.context = this.project.name + " > Edit";
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
}
