import { Jobs } from './../../models/jobs.model';
import { Project } from './../../models/project.model';
import { ProjectService } from './../../services/project.service';
import { JobsService } from './../../services/jobs.service';
import { Handler } from './../../components/dialogs/handler/handler';
import { Component, OnInit } from '@angular/core';
import { APPCONFIG } from '../../config';
import * as $ from 'jquery';
import { TasksService } from './../../services/tasks.service';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import * as EventSource from 'eventsource';

@Component({
  selector: 'my-app-footer',
  styleUrls: ['./footer.component.scss'],
  templateUrl: './footer.component.html',
  providers: [TasksService, ProjectService, JobsService]
})

export class AppFooterComponent implements OnInit {
  public AppConfig: any;
  tasksRes;
  id: string;
  jobId: string;
  count: number;
  project: Project = new Project();
  job: Jobs = new Jobs();
  //private serviceUrl = 'api/v1/events/orgevents';
  //private serviceUrl = '/api/v1/events/register';
  constructor(private tasksService: TasksService, 
              private handler: Handler, 
              private projectService: ProjectService, 
              private jobsService: JobsService,
              private route: ActivatedRoute, 
              private router: Router){
    // http.get(this.serviceUrl)
    // .subscribe(response => {
    //   console.log(response.json());
    // });
  }
  ngOnInit() {    
    $('.chat-content').slideToggle();
    $("i", this).toggleClass("fa-window-minimize fa-window-maximize");
    // this.tasks();
    // this.connect();
    //console.log('Searching:', this.project.id);
    this.AppConfig = APPCONFIG;
      $('.show-chat-box').click(function(){
      $('.chat-content').slideToggle();
      $("i", this).toggleClass("fa-window-minimize fa-window-maximize");
});

  this.route.params.subscribe(params => {
    this.id = params['entityId'];
    //console.log('this.id---------',this.id);
    //console.log('Searching:', this.project.id);
    this.jobId = params['entityId'];
    if (this.id) {
      this.loadProject();
    }
    if (this.jobId) {
       this.loadJob();
    }
  });

  }

  tasks() {
    this.handler.activateLoader();
    this.tasksService.get(this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
        this.tasksRes = results['data'];
        //console.log('tasksRes---', this.tasksRes);
        //console.log(this.tasksRes.length);
        this.length = results['totalElements'];
        this.count = 0;
        for (let entry of this.tasksRes) {
            if ( entry.status == 'In_progress' ) {
              this.count = this.count + 1;
            }
        }

      }, error => {
        this.handler.hideLoader();
        this.handler.error(error);
      });
  }

  loadProject() {
    this.handler.activateLoader();
    this.projectService.getById(this.id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.project = results['data'];
      this.job['project'] = this.project;
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  loadJob() {
    this.handler.activateLoader();
    this.jobsService.getById(this.jobId).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.job = results['data'];
      this.loadProject();
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  connect() {
    let source = new EventSource('/api/v1/events/register');
    source.addEventListener('message', message => {
      // let event = JSON.parse(message.data);
      //console.log('event-------', event);
      this.tasks();
      // TODO - display & update events in the Tasks window.
    });
  }

  length = 0;
  page = 0;
  pageSize = 10;
}
