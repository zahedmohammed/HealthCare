import { Component, OnInit } from '@angular/core';
import { JobsService } from '../../../services/jobs.service';
import { Handler } from '../../dialogs/handler/handler';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import {Jobs} from '../../../models/jobs.model';

@Component({
 selector: 'app-job-details',
 templateUrl: './job-details.component.html',
 styleUrls: ['./job-details.component.scss'],
 providers: [JobsService]
})
export class JobDetailsComponent implements OnInit {
 jobs: Jobs = new Jobs();
 id;
 constructor(private jobsService: JobsService, private handler: Handler, private route: ActivatedRoute) { }

 ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params['id'];
      if (params['id']) {
        this.getJobsById(params['id']);
      }
    });
  }

getJobsById(id: string) {
 this.jobsService.getById(id).subscribe(results => {
   this.handler.hideLoader();
   if (this.handler.handle(results)) {
     return;
   }
   this.jobs = results['data'];
 }, error => {
   this.handler.hideLoader();
   this.handler.error(error);
 });
}

/*getEnvById(id: string){
    this.envService.getEnv(id).subscribe(results => {
        this.handler.hideLoader();
        if (this.handler.handle(results)) {
            return;
        }
        this.jobs = results['data'];
        console.log(this.jobs);
    }, error => {
        this.handler.hideLoader();
        this.handler.error(error);
    });

}*/
}