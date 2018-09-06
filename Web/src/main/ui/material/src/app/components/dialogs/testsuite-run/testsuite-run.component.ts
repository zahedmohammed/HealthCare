import{Component, Inject, OnInit}from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute}from "@angular/router";
import {RunService}from '../../../services/run.service';
import { JobsService } from '../../../services/jobs.service';
import { ProjectService } from '../../../services/project.service';
import { VERSION, MatDialog, MatDialogRef, MatSnackBar, MatSelectModule , MAT_DIALOG_DATA} from '@angular/material';
import {RegionsService}from '../../../services/regions.service';
import {Handler}from '../handler/handler';
import { SnackbarService}from '../../../services/snackbar.service';


@Component({
  selector: 'app-testsuite-run',
  templateUrl: './testsuite-run.component.html',
  styleUrls: ['./testsuite-run.component.scss'],
  providers: [RegionsService, RunService,SnackbarService,JobsService,ProjectService]

})
export class TestsuiteRunComponent implements OnInit {

  runResult;
  jobs;
  envs;
  environment;
  region;
  constructor(
    private regionService: RegionsService,private projectService: ProjectService, private runService: RunService, private router: Router, private handler: Handler,
    @Inject(MAT_DIALOG_DATA) public data: any,private snackbarService: SnackbarService,private jobsService: JobsService,
    public dialogRef: MatDialogRef<TestsuiteRunComponent>
  ) { }

  ngOnInit() {
    this.list(this.data.project.id);
    this.envlist(this.data.project.id);
  }


  length = 0;
  page = 0;
  pageSize = 20;

  list(id: string) {
    this.handler.activateLoader();
    this.jobsService.getJobs(id, this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.jobs = results['data'];
      console.log("job",this.jobs);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  envlist(id: string) {
    this.handler.activateLoader();
    this.projectService.getEnvsByProjectId(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.envs = results['data'];
      console.log("env",this.envs);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }



testSuiterun(){
  console.log("Run data","-->"+this.region+"--"+this.environment+"---"+this.data);
  this.runService.advTestSuiteRun(this.region, this.environment, this.data).subscribe(results => {
    this.handler.hideLoader();
    if (this.handler.handle(results)) {
      return;
    }
    this.runResult = results['data'];
   // this.router.navigate(['/app/projects/' , this.data.id,  'jobs', this.data.id, 'runs']);
  }, error => {
    this.handler.error(error);
  });
}

}
