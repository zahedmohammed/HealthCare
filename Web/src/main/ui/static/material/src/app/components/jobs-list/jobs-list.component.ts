import { Component, OnInit, ViewChild } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { JobsService } from '../../services/jobs.service';
import { RunService } from '../../services/run.service';
import { ProjectService } from '../../services/project.service';
import { Base } from '../../models/base.model';
//import { MatSort, MatSortable, MatTableDataSource } from '@angular/material';


@Component({
  selector: 'app-jobs-list',
  templateUrl: './jobs-list.component.html',
  styleUrls: ['./jobs-list.component.scss'],
  providers: [JobsService, RunService, ProjectService]
})
export class JobslistComponent implements OnInit {

  jobs;
  projectId: string = "";
  project: Base = new Base();
  showSpinner: boolean = false;
  constructor(private jobsService: JobsService, private runService: RunService, private projectService: ProjectService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {   
    this.showSpinner = true;
    this.route.params.subscribe(params => {
      console.log(params);
      //if (params['id']) {
        //this.projectId = params['id'];
        this.list();
        //this.loadProject(this.projectId);
      //}
    });
  }

  loadProject(id: string) {
    this.projectService.getById(id).subscribe(results => {
        if (!results)
            return;
        this.project = results['data'];
    });
  }

  list() {
    this.jobsService.get().subscribe(results => {
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
      this.router.navigate(['/app/job/' , id, 'runs']);
    }, error => {
      console.log("Unable to fetch jobs");
    });
  }
}
