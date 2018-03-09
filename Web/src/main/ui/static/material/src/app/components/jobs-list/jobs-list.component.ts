import { Component, OnInit, ViewChild } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { JobsService } from '../../services/jobs.service';
import { RunService } from '../../services/run.service';
//import { MatSort, MatSortable, MatTableDataSource } from '@angular/material';


@Component({
  selector: 'app-jobs-list',
  templateUrl: './jobs-list.component.html',
  styleUrls: ['./jobs-list.component.scss'],
  providers: [JobsService, RunService]
})
export class JobslistComponent implements OnInit {

  jobs;
  projectId: string = "";
  showSpinner: boolean = false;
  constructor(private jobsService: JobsService, private runService: RunService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {   
    this.showSpinner = true;
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.projectId = params['id'];
        this.list(this.projectId);
      }
    });
  }

  list(id: string) {
    this.jobsService.getJobs(id).subscribe(results => {
      this.showSpinner = false;
      if (!results)
        return;
      this.jobs = results['data'];
    }, error => {
      console.log("Unable to fetch jobs");
    });
  }

  runJob(id: string) {
    this.runService.run(id).subscribe(results => {
      this.showSpinner = false;
      if (!results)
        return;
      //this.jobs = results['data'];
      this.router.navigate(['/app/runs', id]);
    }, error => {
      console.log("Unable to fetch jobs");
    });
  }
}
