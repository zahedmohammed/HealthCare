import { Component, OnInit, ViewChild } from '@angular/core';
import { ProjectService } from '../../../services/project.service';
import { Handler } from '../../dialogs/handler/handler';

@Component({
  selector: 'app-projects-list',
  templateUrl: './projects-list.component.html',
  styleUrls: ['./projects-list.component.scss'],
  providers: [ProjectService]
})
export class ProjectsListComponent implements OnInit {
  projects;
  autocodeConfig; 
  projectTitle:string = "Projects";
  showSpinner: boolean = false;
  constructor(private projectService: ProjectService, private handler: Handler) { }

  ngOnInit() {
    this.list();
  }

  list() {
    this.handler.activateLoader();
    this.projectService.getProjects(this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.projects = results['data'];
      this.length = results['totalElements'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  length = 0;
  page = 0;
  pageSize = 19;
  change(evt) {
    this.page = evt['pageIndex'];
    this.list();
  }

}
