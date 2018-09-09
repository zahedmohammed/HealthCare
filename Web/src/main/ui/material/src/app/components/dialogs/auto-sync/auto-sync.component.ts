import { Component, OnInit, Inject } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { NgModel } from '@angular/forms';
import { TestSuiteService } from '../../../services/test-suite.service';
import { RunService } from '../../../services/run.service';
import { ProjectService } from '../../../services/project.service';
import { Handler } from '../../dialogs/handler/handler';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import 'rxjs/add/observable/timer';
import { ProjectSync } from '../../../models/project-sync.model';
import { SnackbarService } from '../../../services/snackbar.service';
@Component({
  selector: 'app-auto-sync',
  templateUrl: './auto-sync.component.html',
  styleUrls: ['./auto-sync.component.scss'],
  providers: [TestSuiteService, RunService, ProjectService, SnackbarService]
})
export class AutoSyncComponent implements OnInit {

  id; // project id
  checked = false;
  showCategories = false;
  projectId: string = "";
  showSpinner: boolean = false;
  projectSync: ProjectSync = new ProjectSync();
  project;
  categories=['SimpleGET','Functional','SLA', 'Negative','UnSecured','DDOS','XSS_Injection','SQL_Injection','Log_Forging','RBAC'];


  constructor(private testSuiteService: TestSuiteService, private runService: RunService, private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private projectService: ProjectService, private route: ActivatedRoute, private router: Router, private handler: Handler, private snackbarService: SnackbarService) { }

  ngOnInit() {
    this.project = this.data;
    this.handler.activateLoader();
    this.route.params.subscribe(params => {
      this.id = params['id'];
    });
  }

  selectAll(select: NgModel, values, array) {
    if (this.checked){
       select.update.emit(values);
       this.projectSync.deleteAll = true;
    }
    if (!this.checked){
        select.update.emit([]);
        this.projectSync.deleteAll = false;
    }
  }
  synchronization() {
    this.showSpinner = true;
    this.projectSync.projectId = this.project.id;
    this.projectService.projectSync(this.projectSync).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.snackbarService.openSnackBar(this.project.name + " syncing.... ", "");
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
}
