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
  showCategories1 = false;
  showCategories2 = false;
  projectId: string = "";
  showSpinner: boolean = false;
  projectSync: ProjectSync = new ProjectSync();
  deleteAllChecked=false;
  deleteOrCreate:boolean = true;
  checked2: boolean = false;
  checked3: boolean = false;
  project;
  categories=['SimpleGET','Functional','SLA', 'Negative','UnSecured','DDOS','XSS_Injection','SQL_Injection','Log_Forging','RBAC'];


  constructor(private testSuiteService: TestSuiteService, private runService: RunService, private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private projectService: ProjectService, private route: ActivatedRoute, private router: Router, private handler: Handler, private snackbarService: SnackbarService) { }

  ngOnInit() {
    this.project = this.data;
    this.route.params.subscribe(params => {
      this.id = params['id'];
    });
  }

  selectAll(select: NgModel, values, array) {
    if (this.checked){
       select.update.emit(values);
       this.projectSync.deleteAll = true;
       this.deleteAllChecked=true;
    }
    if (!this.checked){
        select.update.emit([]);
        this.projectSync.deleteAll = false;
        this.deleteAllChecked=true;
    }

  }
  selectionChange(){
    console.log("check length",this.projectSync.categories.length)
    if(this.projectSync.categories.length<10){
      this.projectSync.deleteAll = false;
        this.deleteAllChecked=true;
    }
  }

  synchronization() {
    if(this.showCategories2)
      this.projectSync.recreate = true;

    console.log(this.projectSync)
    this.showSpinner = true;
    this.projectSync.projectId = this.project.id;
    this.projectSync.deleteAll = this.checked2;
      this.handler.activateLoader();
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
