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
import {AutoSyncComponent} from "../../dialogs/auto-sync/auto-sync.component";

@Component({
  selector: 'app-auto-sync-save-config',
  templateUrl: './auto-sync-save-config.component.html',
  styleUrls: ['./auto-sync-save-config.component.scss'],
  providers: [TestSuiteService, RunService, ProjectService, SnackbarService]
})
export class AutoSyncSaveConfigComponent implements OnInit {
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


  constructor(private testSuiteService: TestSuiteService, private runService: RunService, private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private projectService: ProjectService, private route: ActivatedRoute, private router: Router, private handler: Handler, private snackbarService: SnackbarService) { }

  ngOnInit() {
    this.project = this.data;
  }


  autoSync(){
    const dialogRef = this.dialog.open(AutoSyncComponent, {
      //width:'450px',
      data: this.project
  });
  dialogRef.afterClosed().subscribe(result => {
  });
  }
}
