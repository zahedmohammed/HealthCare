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
  showSpinner: boolean = false;
  constructor(private runService: RunService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getRunById(params['id']);
        this.getTestSuiteResponsesByRunId(params['id']);
      }
    });
  }

  getRunById(id: string) {
    this.showSpinner = true;
    this.runService.getDetails(id).subscribe(results => {
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

  getTestSuiteResponsesByRunId(id: string) {
    this.showSpinner = true;
    this.runService.getTestSuiteResponses(id).subscribe(results => {
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

}
