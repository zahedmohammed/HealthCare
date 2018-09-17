import { Component, OnInit, ViewChild } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { JobsService } from '../../services/jobs.service';
import { RunService } from '../../services/run.service';
import { ProjectService } from '../../services/project.service';
import { Base } from '../../models/base.model';
import { Run } from '../../models/run.model';
import { VERSION, MatDialog, MatDialogRef } from '@angular/material';
import { MsgDialogComponent } from '../dialogs/msg-dialog/msg-dialog.component';
import { Handler } from '../dialogs/handler/handler';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import { Subscription } from 'rxjs/Subscription';
import {SnackbarService} from '../../services/snackbar.service';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-run-detail',
  templateUrl: './run-detail.component.html',
  styleUrls: ['./run-detail.component.scss'],
  providers: [JobsService, RunService, ProjectService, SnackbarService]
})
export class RunDetailComponent implements OnInit {
  run:Run = new Run();
  list;
  suites;
  id;
  projectId:string = "";
  jobId:string =  "";
  total = 0;
  failed = 0;
  size = 0;
  time = 0;
  duration = 0;
  success = 0;
  length = 0;
  page = 0;
  pageSize = 10;
  private _clockSubscription: Subscription;
  project: Base = new Base();
  job: Base = new Base();
  showSpinner: boolean = false;
  keyword: string = '';
  category: string = '';

  nextRunId : number = 0;
  prevRunId: number = 0;
    times;
    totalTimeSaved = 0;
    disableButtonNext : boolean = false
    disableButtonPrev : boolean = false
    displayedColumns: string[] = ['suite', 'category', 'severity', 'status', 'data', 'time', 'analytics'];
    dataSource = null;
  
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

  constructor(private jobsService: JobsService, private runService: RunService, private projectService: ProjectService,
    private route: ActivatedRoute, private router: Router, private dialog: MatDialog, private handler: Handler,  private snackbarService: SnackbarService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['projectId']) {
       this.projectId = params['projectId'];
       this.loadProject(this.projectId);
      }
      if (params['jobId']) {
        this.jobId = params['jobId'];
        this.loadJob(this.jobId);
      }
      if (params['runId']) {
        this.id = params['runId'];
        let timer = Observable.timer(100, 10000);
        this._clockSubscription = timer.subscribe(t => {
          this.getRunById();
          this.getSummary();
          if (this.run.task.status == 'COMPLETED' || this.run.task.status == 'TIMEOUT') {
            this._clockSubscription.unsubscribe();
          }
        });
      }
    });

      this.getRunByJob(this.jobId)

  }

  ngOnDestroy(): void {
    this._clockSubscription.unsubscribe();
  }

  loadProject(id: string) {
    this.projectService.getById(id).subscribe(results => {
      if (this.handler.handle(results)) {
        return;
      }
      this.project = results['data'];

    });
  }

  loadJob(id: string) {
    this.jobsService.getById(id).subscribe(results => {
      if (this.handler.handle(results)) {
        return;
      }
      this.job = results['data'];
    });
  }

  calSum() {
    //this.total = 0;
    //this.failed = 0;
    //this.size = 0;
    //this.time = 0;
    this.success = 0;
    //this.duration = 0;

    /*for(var i = 0; i < this.suites.length; i++){
        this.total += this.suites[i].tests;
        this.failed += this.suites[i].failed;
        this.size += this.suites[i].size;
        this.time += this.suites[i].time;
    }*/
    this.success = (this.run.task['totalTests'] - this.run.task['failedTests']) / (this.run.task['totalTests']);
    //this.duration = Date.parse(this.run.modifiedDate) - Date.parse(this.run.task.startTime);
  }

  getRunById() {
    this.handler.activateLoader();
    this.runService.getDetails(this.id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.run = results['data'];
      this.calSum();
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getTestSuiteResponsesByRunId() {
    this.handler.activateLoader();
    this.runService.getTestSuiteResponses(this.id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.list = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getSummary() {
    this.handler.activateLoader();
    this.runService.getSummary(this.id, this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.suites = results['data'];
      this.length = results['totalElements'];
      this.dataSource = new MatTableDataSource(this.suites);
      this.dataSource.sort = this.sort;
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  search() {
    if (this.keyword == '' && this.category == '') {
      return this.getSummary();
    }
    this.handler.activateLoader();
    this.runService.search(this.id, this.category, this.keyword, this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.suites = results['data'];
      this.length = results['totalElements'];
      this.dataSource = new MatTableDataSource(this.suites);
      this.dataSource.sort = this.sort;
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getTestSuiteResponseByName(name: string) {
    this.handler.activateLoader();
    this.runService.getTestSuiteResponseByName(this.id, name).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.list = results['data'];
      var arrayLength = this.list.length;
      var msg = '';
      for (var i = 0; i < arrayLength; i++) {
        //alert(this.list[i]);
        msg += this.list[i].logs;
        //Do something
      }
      this.showDialog(msg);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  showDialog(msg) {
    this.dialog.open(MsgDialogComponent, {
        width:'100%',
        //height:'90%',
        data: msg
    });
  }


  change(evt) {
    this.page = evt['pageIndex'];
    this.getSummary();
  }



  cancel(){

    this.runService.stopRun(this.id).subscribe(results => {

      this.handler.hideLoader();
     if (this.handler.handle(results)) {
       return;
     }
     //this.router.navigate(['/app/projects/' , this.project.id,  'jobs', this.job.id, 'runs']);
   }, error => {
     this.handler.error(error);
   });
    
  }

  deleteRun(){
    var r = confirm("Are you sure you want to delete '" + this.run.runId + "'?");
    if (r == true) {
    this.runService.deleteByJobIdAndRunId(this.jobId, this.run.runId).subscribe(results =>{
      if (this.handler.handle(results)) {
        return;
      }
          this.snackbarService.openSnackBar("'JobRun '" + this.run.runId + "' deleted", "");
          this.router.navigate(['/app/projects', this.project.id, 'jobs', this.jobId, 'runs']);
        }, error => {
          this.handler.hideLoader();
          this.handler.error(error);
        });

      }
    }

  rerun(){
    this.runService.reRunByJobIdAndRunId(this.jobId, this.run.runId).subscribe(results =>{
      if (this.handler.handle(results)) {
        return;
      }
      this.snackbarService.openSnackBar("'Rerun  no '" + this.run.runId + "' processing", "");
      this.router.navigate(['/app/projects', this.project.id, 'jobs', this.jobId, 'runs']);
     }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
     });
  }

    nextRun(){

      var arrayIndex = 0;
    this.nextRunId = parseInt(this.run.runId) + 1;
    arrayIndex = this.list.findIndex(i=>i.runId === this.run.runId);
    this.nextRunId = arrayIndex - 1
        if(this.nextRunId === 0) {
            this.disableButtonNext = true
        }

        if(this.nextRunId !== this.list.length) {
            this.disableButtonPrev = false
        }
        
        this.router.navigate(['/app/projects' , this.projectId, 'jobs' , this.job.id, 'runs', this.list[this.nextRunId]['id']])
    }

    prevRun(){
        var arrayIndex = 0;
        this.nextRunId = parseInt(this.run.runId) - 1;
        arrayIndex = this.list.findIndex(i=>i.runId === this.run.runId);
        this.nextRunId = arrayIndex + 1

        if(this.nextRunId === this.list.length -1) {
            this.disableButtonPrev = true
        }

        if(this.nextRunId !== 0) {
            this.disableButtonNext = false
        }
        this.router.navigate(['/app/projects' , this.projectId, 'jobs' , this.job.id, 'runs', this.list[this.nextRunId]['id']])
    }
    getRunByJob(id: string) {
        this.handler.activateLoader();
        this.runService.get(id, this.page, this.pageSize).subscribe(results => {
            this.handler.hideLoader();
            if (this.handler.handle(results)) {
                return;
            }
            this.list = results['data'];
            this.length = results['totalElements'];
            this.times = 0;
            for (var  i = 0; i < this.list.length; i++){
                this.times += this.list[i].task.timeSaved;
            }
            this.totalTimeSaved = this.times;
        }, error => {
            this.handler.hideLoader();
            this.handler.error(error);
        });


    }


}
