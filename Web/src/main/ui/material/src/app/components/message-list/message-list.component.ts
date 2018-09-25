import { Component, OnInit } from '@angular/core';
import { MessageService } from '../../services/message.service';
import { Handler } from '../dialogs/handler/handler';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import { Subscription } from 'rxjs/Subscription';
import { Jobs } from './../../models/jobs.model';
import { Project } from './../../models/project.model';
import { ProjectService } from './../../services/project.service';
import { JobsService } from './../../services/jobs.service';
import { APPCONFIG } from '../../config';
import { TasksService } from './../../services/tasks.service';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import * as EventSource from 'eventsource';

@Component({
  selector: 'app-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.scss'],
  providers: [MessageService, TasksService, ProjectService, JobsService]
})
export class MessageListComponent implements OnInit {
  public AppConfig: any;
  tasksRes;
  id: string;
  jobId: string;
  count: number;
  project: Project = new Project();
  job: Jobs = new Jobs();
  items;
  showSpinner: boolean = false;
  private _clockSubscription: Subscription;
  infoMsg = 'Congratulations! The syncing task has successfully ran without any issues. So if you want you can chech the logs. Thank you!';
  errorMsg = 'Unfortunately while syncing the task, the system encountered with some issues with this task. So please check the logs and re-run. Thank you!';
  constructor(private messageService: MessageService, private handler: Handler, private tasksService: TasksService, private projectService: ProjectService, private jobsService: JobsService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
   // this.tasks();
    this.connect();
    //this.list();
    let timer = Observable.timer(1, 5000);
    this._clockSubscription = timer.subscribe(t => {
       this.tasks();
    });
  }

  ngOnDestroy(): void {
    this._clockSubscription.unsubscribe();
  }

  tasks() {
    this.handler.activateLoader();
    this.tasksService.get(this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.tasksRes = results['data'];
      this.length = results['totalElements'];
      this.count = 0;
      for (let entry of this.tasksRes) {
        if (entry.status == 'In_progress') {
          this.count = this.count + 1;
        }
      }

    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  connect() {
    let source = new EventSource('/api/v1/events/register');
    source.addEventListener('message', message => {
      this.tasks();
    });
  }

  list() {
    this.handler.activateLoader();
    this.messageService.get(this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.items = results['data'];
      this.length = results['totalElements'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  length = 0;
  page = 0;
  pageSize = 10;
  change(evt) {
    this.page = evt['pageIndex'];
    //this.list();
    this.tasks();
  }

}