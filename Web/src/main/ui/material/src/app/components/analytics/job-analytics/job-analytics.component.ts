import { Component, OnInit } from '@angular/core';
import {TestSuiteService} from "../../../services/test-suite.service";
import {ProjectService} from "../../../services/project.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Handler} from "../../dialogs/handler/handler";
import {Project} from "../../../models/project.model";
import {MatTableDataSource} from "@angular/material";
import {JobsService} from "../../../services/jobs.service";
import {CHARTCONFIG} from "../../../charts/charts.config";
import {Observable} from "rxjs/Observable";
import { Chart } from 'chart.js';
import 'chartjs-plugin-labels';
import 'chartjs-plugin-datalabels';
import {Subscription} from "rxjs/Subscription";
import {RunService} from "../../../services/run.service";
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-job-analytics',
  templateUrl: './job-analytics.component.html',
  styleUrls: ['./job-analytics.component.scss'],
  providers: [ProjectService, JobsService,TestSuiteService,RunService]

})
export class JobAnalyticsComponent implements OnInit {
    projectId; // project id
    jobId; // job id
    jobs;
    selectedValue;
    project: Project = new Project();
    private _clockSubscription: Subscription;
    constructor(private testSuiteService: TestSuiteService, private projectService: ProjectService,private jobsService: JobsService,
                private route: ActivatedRoute, private router: Router, private handler: Handler,private runService: RunService,
                private datePipe: DatePipe) { }
    config = CHARTCONFIG;
    graph1: Chart = []; // This will hold our chart info
    graph2: Chart = []; // This will hold our chart info
    graph3: Chart = []; // This will hold our chart info
    graph4: Chart = []; // This will hold our chart info
    line1: any;
    totalData:any;

    ngOnInit() {
        this.route.params.subscribe(params => {
            if (params['id']) {
                this.projectId = params['id'];
                this.loadProject(this.projectId);
                this.list(this.projectId)
            }
        });
    }

    loadProject(id: string) {
        this.projectService.getById(id).subscribe(results => {
            if (this.handler.handle(results)) {
                return;
            }
            this.project = results['data'];
        });
    }

    list(id: string) {
        this.handler.activateLoader();
        this.jobsService.getJobs(id, 0, 20).subscribe(results => {
            this.handler.hideLoader();
            if (this.handler.handle(results)) {
                return;
            }
            this.jobs = results['data'];
            this.jobId = this.jobs[0]['id'];
            this.getRunByJob();
        }, error => {
            this.handler.hideLoader();
            this.handler.error(error);
        });
    }

    getRunByJob() {
        this.handler.activateLoader();
        this.runService.get(this.jobId, 0, 10).subscribe(results => {
            this.handler.hideLoader();
            if (this.handler.handle(results)) {
                return;
            }
            this.list = results['data'];
            this.getAnalyticsData();
        }, error => {
            this.handler.hideLoader();
            this.handler.error(error);
        });
    }

    //Graph Analytics - Start
    getAnalyticsData() {
        if(this.totalData != null)
        {
            this.graph1.destroy();
            this.graph2.destroy();
            this.graph3.destroy();
            this.graph4.destroy();

        }
        this.totalData = this.list;
        let totalPass: any = [];
        let totalFail: any = [];
        let totalBytes: any = [];
        let dateTime: any = [];
        let dtDateConvert: any = [];
        let crDate: any = [];
        let totalOpenIssues: any = [];
        let issuesClosed: any = [];
        let issuesLogged: any = [];
        let issuesReopen: any = [];
        let totalTime: any = [];
        let runId: any = [];

        for (let i = 0; i < this.totalData.length; i++) {
            let rid: any[] = this.totalData[i].runId;
            runId.push(rid);

            let openIssues: any[] = this.totalData[i].task.totalOpenIssues;
            totalOpenIssues.push(openIssues);

            let failed: any[] = this.totalData[i].task.failedTests;
            totalFail.push(failed);

            let passed: any[] = this.totalData[i].task.totalTestCompleted;
            totalPass.push(passed);

            let dateTimeX: any[] = this.totalData[i].createdDate;
            dateTime.push(dateTimeX);
            crDate[i] = dateTimeX;
            let dt = new Date(crDate[i]);
            dtDateConvert[i] = this.datePipe.transform(dt, "MMM dd");

            let bytes = this.totalData[i].task.totalBytes / 1024;
            bytes = Math.round(bytes);
            totalBytes.push(bytes);

            let closed: any[] = this.totalData[i].task.issuesClosed;
            issuesClosed.push(closed);

            let logged: any[] = this.totalData[i].task.issuesLogged;
            issuesLogged.push(logged);

            let reopen: any[] = this.totalData[i].task.issuesReopen;
            issuesReopen.push(reopen);

            let totalTimeArr = this.totalData[i].task.totalTime / 1000;
            totalTimeArr = Math.round(totalTimeArr);
            totalTime.push(totalTimeArr);
        }
        totalOpenIssues.reverse();
        totalFail.reverse();
        totalPass.reverse();
        dtDateConvert.reverse();
        totalBytes.reverse();
        issuesClosed.reverse();
        issuesLogged.reverse();
        issuesReopen.reverse();
        totalTime.reverse();
        runId.reverse();

        // Options Start for Graphs
        let graph1Options = {
            responsive: true,
            legend: {
                display: true
            },
            title: {
                display: false,
                text: 'Passed & Failed Analytics'
            },
            scales: {
                xAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Run No'
                    }
                }],
                yAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Pass / Fail'
                    }
                }]
            },
            plugins: {
                datalabels: {
                    backgroundColor: function(context) {
                        return context.dataset.backgroundColor;
                    },
                    borderRadius: 5,
                    color: 'white',
                    datalabels: {
                        align: 'end',
                        anchor: 'end'
                    }
                }
            }
        };

        let graph2Options = {
            responsive: true,
            legend: {
                display: true
            },
            title: {
                display: false,
                text: 'Issues Analytics'
            },
            scales: {
                xAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Run No'
                    }
                }],
                yAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Issues'
                    }
                }]
            },
            plugins: {
                datalabels: {
                    backgroundColor: function(context) {
                        return context.dataset.backgroundColor;
                    },
                    borderRadius: 5,
                    color: 'white',
                    datalabels: {
                        align: 'end',
                        anchor: 'end'
                    }
                }
            }
        };

        let graph3Options = {
            responsive: true,
            legend: {
                display: true
            },
            title: {
                display: false,
                text: 'Data Analytics'
            },
            scales: {
                xAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Run No'
                    }
                }],
                yAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Data ( In KBs )'
                    }
                }]
            },
            plugins: {
                datalabels: {
                    backgroundColor: function(context) {
                        return context.dataset.backgroundColor;
                    },
                    borderRadius: 5,
                    color: 'white',
                    datalabels: {
                        align: 'end',
                        anchor: 'end'
                    }
                }
            }
        };

        let graph4Options = {
            responsive: true,
            legend: {
                display: true
            },
            title: {
                display: false,
                text: 'Time Analytics'
            },
            scales: {
                xAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Run No'
                    }
                }],
                yAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Total Time ( In Seconds )'
                    }
                }]
            },
            plugins: {
                datalabels: {
                    backgroundColor: function(context) {
                        return context.dataset.backgroundColor;
                    },
                    borderRadius: 5,
                    color: 'white',
                    datalabels: {
                        align: 'end',
                        anchor: 'end'
                    }
                }
            }
        };

        //Graph 1 Data - Passed Failed
        let graph1Data = {
            labels: runId,
            datasets: [
                {
                    data: totalPass,
                    label: 'Passed',
                    backgroundColor: this.config.success,
                    borderColor: this.config.success,
                    pointBorderColor: this.config.pointborderColor,
                    pointBackgroundColor: this.config.whiteColor,
                    pointHoverBackgroundColor: this.config.whiteColor,
                    pointHoverBorderColor: this.config.pointhoverBorderColor,
                    pointBorderWidth: 1,
                    fill: false,
                    pointRadius: 3,
                    pointHoverRadius: 4,
                    datalabels: {
                        align: 'top',
                        anchor: 'top'
                    }
                },
                {
                    data: totalFail,
                    label: 'Failed',
                    backgroundColor: this.config.danger,
                    borderColor: this.config.danger,
                    pointBorderColor: this.config.pointborderColor,
                    pointBackgroundColor: this.config.whiteColor,
                    pointHoverBackgroundColor: this.config.whiteColor,
                    pointHoverBorderColor: this.config.pointhoverBorderColor,
                    pointBorderWidth: 1,
                    fill: false,
                    pointRadius: 3,
                    pointHoverRadius: 4,
                    datalabels: {
                        align: 'bottom',
                        anchor: 'bottom'
                    }
                }
            ]
        };

        //Graph 2 Data - Issues
        let graph2Data = {
            labels: runId,
            datasets: [
                {
                    data: issuesClosed,
                    label: 'Closed',
                    borderColor: this.config.success,
                    backgroundColor: this.config.success,
                    pointBorderColor: this.config.pointborderColor,
                    pointBackgroundColor: this.config.whiteColor,
                    pointHoverBackgroundColor: this.config.whiteColor,
                    pointHoverBorderColor: this.config.pointhoverBorderColor,
                    pointBorderWidth: 1,
                    fill: false,
                    pointRadius: 3,
                    pointHoverRadius: 4,
                    datalabels: {
                        align: 'top',
                        anchor: 'top'
                    }
                },
                {
                    data: issuesLogged,
                    label: 'New',
                    borderColor: this.config.danger,
                    backgroundColor: this.config.danger,
                    pointBorderColor: this.config.pointborderColor,
                    pointBackgroundColor: this.config.whiteColor,
                    pointHoverBackgroundColor: this.config.whiteColor,
                    pointHoverBorderColor: this.config.pointhoverBorderColor,
                    pointBorderWidth: 1,
                    fill: false,
                    pointRadius: 3,
                    pointHoverRadius: 4,
                    datalabels: {
                        align: 'center',
                        anchor: 'center'
                    }
                },
                {
                    data: totalOpenIssues,
                    label: 'Open',
                    borderColor: this.config.warning,
                    backgroundColor: this.config.warning,
                    pointBorderColor: this.config.pointborderColor,
                    pointBackgroundColor: this.config.whiteColor,
                    pointHoverBackgroundColor: this.config.whiteColor,
                    pointHoverBorderColor: this.config.pointhoverBorderColor,
                    pointBorderWidth: 1,
                    fill: false,
                    pointRadius: 3,
                    pointHoverRadius: 4,
                    datalabels: {
                        align: 'bottom',
                        anchor: 'bottom'
                    }
                }
            ]
        };

        //Graph 3 Data - KiloBytes
        let graph3Data = {
            labels: runId,
            datasets: [
                {
                    data: totalBytes,
                    label: 'Data (In KBs)',
                    borderColor: this.config.info,
                    backgroundColor: this.config.info,
                    pointBorderColor: this.config.pointborderColor,
                    pointBackgroundColor: this.config.whiteColor,
                    pointHoverBackgroundColor: this.config.whiteColor,
                    pointHoverBorderColor: this.config.pointhoverBorderColor,
                    pointBorderWidth: 1,
                    fill: false,
                    pointRadius: 3,
                    pointHoverRadius: 4,
                    datalabels: {
                        align: 'top',
                        anchor: 'top'
                    }
                },
            ]
        };

        //Graph 4 Data - Total Time
        let graph4Data = {
            labels: runId,
            datasets: [
                {
                    data: totalTime,
                    label: 'Time (In Seconds)',
                    borderColor: this.config.infoAlt,
                    backgroundColor: this.config.infoAlt,
                    pointBorderColor: this.config.pointborderColor,
                    pointBackgroundColor: this.config.whiteColor,
                    pointHoverBackgroundColor: this.config.whiteColor,
                    pointHoverBorderColor: this.config.pointhoverBorderColor,
                    pointBorderWidth: 1,
                    fill: false,
                    pointRadius: 3,
                    pointHoverRadius: 4,
                    datalabels: {
                        align: 'top',
                        anchor: 'top'
                    }
                }
            ]
        }

        //Graph 1 Start

        this.graph1 = new Chart('canvasGraph1', {
            type: 'line',
            data: graph1Data,
            options: graph1Options
        });

        //Graph 2 Start
        this.graph2 = new Chart('canvasGraph2', {
            type: 'line',
            data: graph2Data,
            options: graph2Options
        });

        //Graph 3 Start
        this.graph3 = new Chart('canvasGraph3', {
            type: 'line',
            data: graph3Data,
            options: graph3Options
        });

        //Graph 4 Start
        this.graph4 = new Chart('canvasGraph4', {
            type: 'line',
            data: graph4Data,
            options: graph4Options
        });
    }
//End Graph
}
