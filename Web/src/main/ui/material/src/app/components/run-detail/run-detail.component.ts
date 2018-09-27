import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
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
import { SnackbarService } from '../../services/snackbar.service';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { listenOnPlayer } from "@angular/animations/browser/src/render/shared";
import { DeleteDialogComponent } from "../dialogs/delete-dialog/delete-dialog.component";
import { ConfirmDialogComponent } from './../dialogs/confirm-dialog/confirm-dialog.component';

@Component({
    selector: 'app-run-detail',
    templateUrl: './run-detail.component.html',
    styleUrls: ['./run-detail.component.scss'],
    providers: [JobsService, RunService, ProjectService, SnackbarService]
})
export class RunDetailComponent implements OnInit {
    run: Run = new Run();
    list;
    suites;
    id;
    projectId: string = "";
    jobId: string = "";
    total = 0;
    failed = 0;
    size = 0;
    time = 0;
    duration = 0;
    success = 0;
    length = 0;
    page = 0;
    pageSize = 10;
    pageSizeOptions: number[] = [20, 50, 100];
    project: Base = new Base();
    job: Base = new Base();
    showSpinner: boolean = false;
    keyword: string = '';
    category: string = '';
    status: string = '';
    nextRunId: number = 0;
    prevRunId: number = 0;
    runNumResult: any = [];
    runNumbers: any = [];
    i: number = 0;
    totalRuns: number = 0
    times;
    totalTimeSaved = 0;
    searchFlag:boolean;
    prevKeyword: string = '';
    prevCategory: string = '';
    prevStatus: string = '';

    displayedColumns: string[] = ['suite', 'category', 'severity', 'status', 'data', 'time', 'analytics'];
    dataSource = null;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;
    private _clockSubscription: Subscription;

    constructor(private jobsService: JobsService, private runService: RunService, private projectService: ProjectService,
        private route: ActivatedRoute, private router: Router, private dialog: MatDialog, private handler: Handler, private snackbarService: SnackbarService) {
    }

    ngOnInit() {

        this.runNumbers = JSON.parse(localStorage.getItem('listData'));
        this.totalRuns = parseInt(localStorage.getItem('totalRuns'));
        localStorage.removeItem('totalRuns')
        localStorage.removeItem('listData')

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
                // let timer = Observable.timer(100, 10000);
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
        this.success = 0;
        this.success = (this.run.task['totalTests'] - this.run.task['failedTests']) / (this.run.task['totalTests']);
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
        this.searchFlag = false;
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
        if(this.prevKeyword != this.keyword)
            this.searchFlag = true
        else
            if(this.prevCategory != this.category)
                this.searchFlag = true
        else
            if(this.prevStatus != this.status)
                this.searchFlag = true
        else
            {
                this.prevKeyword = this.keyword
                this.prevCategory = this.category
                this.prevStatus = this.status
                this.searchFlag = false
            }

        if(this.searchFlag)
        {
            this.page = 0;
            this.paginator.pageIndex = 0
            this.searchFlag = false

        }
        this.prevKeyword = this.keyword
        this.prevCategory = this.category
        this.prevStatus = this.status
        //
        if (this.keyword == '' && this.category == '' && this.status == '') {
            // this.page = 0;
            // this.paginator.pageIndex = 0
            return this.getSummary();
        }


        // this.page = 0;
        // this.paginator.pageIndex = 0

        this.handler.activateLoader();
        this.runService.search(this.id, this.category, this.keyword, this.status, this.page, this.pageSize).subscribe(results => {
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
            width: '100%',
            //height:'90%',
            data: msg
        });
    }

    change(evt) {
        this.page = evt['pageIndex'];
        this.pageSize = evt.pageSize;
        // this.getSummary();
        this.search();
    }

    cancel() {
        let dialogRef = this.dialog.open(ConfirmDialogComponent, {
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result != null) {
                this.snackbarService.openSnackBar("Run Number '" + this.run.runId + "' stopping...", "");
                this.runService.stopRun(this.id).subscribe(results => {
                    this.handler.hideLoader();
                    if (this.handler.handle(results)) {
                        return;
                    }
                }, error => {
                    this.handler.error(error);
                });
            }
        });
    }

    deleteRun() {
        let dialogRef = this.dialog.open(DeleteDialogComponent, {
            data: this.run.runId + ' run number'
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result != null) {
                this.runService.deleteByJobIdAndRunId(this.jobId, this.run.runId).subscribe(results => {
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
        });
    }

    rerun() {
        this.runService.reRunByJobIdAndRunId(this.jobId, this.run.runId).subscribe(results => {
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



    nextRun() {
        this.getDetailsByJobIdRunNumber("next")
    }

    prevRun() {
        this.getDetailsByJobIdRunNumber("prev")
    }


    getDetailsByJobIdRunNumber(action:string){
        this.prevKeyword = "";
        this.prevCategory = "";
        this.prevStatus = "";
        this.searchFlag = false;

        this.keyword ="";
        this.category ="";
        this.status ="";


        this.handler.activateLoader();
        this.runService.getDetailsByJobIdRunNum(this.jobId, this.run.runId, action).subscribe(runNumRes => {
            this.handler.hideLoader();
            if (this.handler.handle(runNumRes)) {
                return;
            }
            this.runNumResult = runNumRes['data'];
            this.job = this.runNumResult['job'];
            this.project = this.job['project']
            this.run = this.runNumResult;
            this.calSum();
            this.router.navigate(['/app/projects', this.projectId, 'jobs', this.job.id, 'runs', this.run['id']])

        }, error => {
            this.handler.hideLoader();
            this.handler.error(error);
        });
    }

}
