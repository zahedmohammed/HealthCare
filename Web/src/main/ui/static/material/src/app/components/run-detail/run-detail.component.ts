import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { RunService } from '../../services/run.service';


@Component({
  selector: 'app-run-detail',
  templateUrl: './run-detail.component.html',
  styleUrls: ['./run-detail.component.scss'],
  providers: [RunService]
})
export class RunDetailComponent implements OnInit {
  run;
  list;
  suites;
  id;
  projectId:string = "";
  jobId:string =  "";
  total = 0;
  failed = 0;
  size = 0;
  time = 0;
  showSpinner: boolean = false;
  constructor(private runService: RunService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
       this.projectId = params['id'];
      }
      if (params['jobId']) {
        this.jobId = params['jobId'];
      }
      if (params['runId']) {
        this.id = params['runId'];
        this.getRunById();
        this.getSummary();
      }
    });
  }

  calSum() {
    for(var i = 0; i < this.suites.length; i++){
        this.total += this.suites[i].tests;
        this.failed += this.suites[i].failed;
        this.size += this.suites[i].size;
        this.time += this.suites[i].time;
    }
  }

  getRunById() {
    this.showSpinner = true;
    this.runService.getDetails(this.id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.run = results['data'];
    }, error => {
      console.log("Unable to fetch regions");
    });
  }

  getTestSuiteResponsesByRunId() {
    this.showSpinner = true;
    this.runService.getTestSuiteResponses(this.id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.list = results['data'];
    }, error => {
      console.log("Unable to fetch regions");
    });
  }

  getSummary() {
    this.showSpinner = true;
    this.runService.getSummary(this.id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.suites = results['data'];
      this.calSum();
    }, error => {
      console.log("Unable to fetch regions");
    });
  }


}
