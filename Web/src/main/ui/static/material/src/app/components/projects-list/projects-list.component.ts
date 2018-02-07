import { Component, OnInit, ViewChild } from '@angular/core';
import { ProjectService } from '../../services/project.service';

@Component({
  selector: 'app-projects-list',
  templateUrl: './projects-list.component.html',
  styleUrls: ['./projects-list.component.scss'],
  providers: [ProjectService]
})
export class ProjectlistComponent implements OnInit {
  projects;
  projectTitle:string = "Projects";
  showSpinner: boolean = false;
  constructor(private projectService: ProjectService) { }

  ngOnInit() {  
    this.showSpinner = true; 
    this.projectService.getProjects().subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.projects = results['data'];
    }, error => {
      console.log("Unable to fetch projects");
    });
  }

}
