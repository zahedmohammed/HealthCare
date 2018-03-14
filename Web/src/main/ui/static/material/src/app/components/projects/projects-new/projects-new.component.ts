import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { ProjectService } from '../../../services/project.service';
import { OrgService } from '../../../services/org.service';
import { Project } from '../../../models/project.model';
import { OrgUser } from '../../../models/org.model';

@Component({
  selector: 'app-projects-new',
  templateUrl: './projects-new.component.html',
  styleUrls: ['./projects-new.component.scss'],
  providers: [ProjectService, OrgService]
})
export class ProjectsNewComponent implements OnInit {

  showSpinner: boolean = false;
  project: Project = new Project('', '', '', '', 'GIT');
  orgs;
  constructor(private projectService: ProjectService, private orgService: OrgService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.getOrgs();
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
  getOrgs() {
    this.orgService.get().subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.orgs = results['data'];
    }, error => {
      console.log("Unable to fetch orgs");
    });
  }
  projectTypes = ['Git', 'GitHub', 'BitBucket', 'GitLab'];
  visibilities = ['PRIVATE', 'ORG_PUBLIC'];

}

