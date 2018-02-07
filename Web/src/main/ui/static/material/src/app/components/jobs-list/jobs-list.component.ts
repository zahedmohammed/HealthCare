import { Component, OnInit, ViewChild } from '@angular/core';
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
  showSpinner: boolean = false;
  constructor(private jobsService: JobsService, private runService: RunService) { }

  ngOnInit() {   
    this.showSpinner = true;
    this.jobsService.getJobs().subscribe(results => {
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
    }, error => {
      console.log("Unable to fetch jobs");
    });
  }
}
