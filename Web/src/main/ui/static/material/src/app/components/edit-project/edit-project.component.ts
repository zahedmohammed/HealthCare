import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { ProjectService } from '../../services/project.service';
import { Project } from '../../models/project.model';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.scss'],
  providers: [ProjectService]
})
export class EditProjectComponent implements OnInit {

  showSpinner: boolean = false;
  project: Project;// = new Project('', '', '', '', 'GIT');
  constructor(private projectService: ProjectService, private route: ActivatedRoute) { }

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
      console.log("Unable to fetch regions");
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
      //this.project = new Project('','','','','GIT');
    }, error => {
      console.log("Unable to fetch regions");
    });
  }

}
