import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
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
  constructor(private projectService: ProjectService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
  }

  create() {
    console.log(this.project);
    this.showSpinner = true;
    this.projectService.create(this.project).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/projects']);
    }, error => {
      console.log("Unable to fetch regions");
    });
  }

}

