import { Component, OnInit } from '@angular/core';
import { ProjectService } from '../../services/project.service';
import { Project } from '../../models/project.model';

@Component({
  selector: 'app-new-project',
  templateUrl: './new-project.component.html',
  styleUrls: ['./new-project.component.scss'],
  providers: [ProjectService]
})
export class NewProjectComponent implements OnInit {

  showSpinner: boolean = false;
  project: Project = new Project('', '', '', '', 'GIT');
  constructor(private projectService: ProjectService) { }

  ngOnInit() {
  }

  create() {
    console.log(this.project);
    this.projectService.create(this.project).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      //this.project = new Project('','','','','GIT');
    }, error => {
      console.log("Unable to fetch regions");
    });
  }

}

