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
  keyword: string = '';
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

  //  Search project By Name

  searchProject(){
    if(this.keyword.length>1){
    this.handler.activateLoader();
    this.projectService.searchProject(this.keyword, this.page, this.pageSize).subscribe(results => {
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
   if(this.keyword.length<=1){
    this.list();
   }
  }

  length = 0;
  page = 0;
  pageSize = 11;
  change(evt) {
    this.page = evt['pageIndex'];
    this.list();
  }

}
