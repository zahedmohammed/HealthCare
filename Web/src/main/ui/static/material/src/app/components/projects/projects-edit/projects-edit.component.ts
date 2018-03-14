import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { ProjectService } from '../../../services/project.service';
import { Project } from '../../../models/project.model';

@Component({
  selector: 'app-projects-edit',
  templateUrl: './projects-edit.component.html',
  styleUrls: ['./projects-edit.component.scss'],
  providers: [ProjectService]
})
export class ProjectsEditComponent implements OnInit {

  showSpinner: boolean = false;
  project: Project = new Project('', '', '', '', 'GIT');
  constructor(private projectService: ProjectService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getById(params['id']);
      }
    });
  }

  getById(id: string) {
    this.showSpinner = true;
    this.projectService.getById(id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.project = results['data'];
      console.log(this.project);
    }, error => {
      console.log("Unable to fetch projects");
    });
  }

  update() {
    console.log(this.project);
    this.projectService.update(this.project).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/projects']);
    }, error => {
      console.log("Unable to update project");
    });
  }

  delete() {
    console.log(this.project);
    this.projectService.delete(this.project).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/projects']);
    }, error => {
      console.log("Unable to delete project");
    });
  }

   projectTypes = ['Git', 'GitHub', 'BitBucket', 'GitLab'];
   visibilities = ['PRIVATE', 'ORG_PUBLIC'];

}
