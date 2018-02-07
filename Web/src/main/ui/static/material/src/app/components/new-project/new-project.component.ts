import { Component, OnInit } from '@angular/core';
import { ProjectService } from '../../services/project.service';

@Component({
  selector: 'app-new-project',
  templateUrl: './new-project.component.html',
  styleUrls: ['./new-project.component.scss'],
  providers: [ProjectService]
})
export class NewProjectComponent implements OnInit {

  project = new Object();
  constructor(private projectService: ProjectService) { }

  ngOnInit() {
  }

  save() {
    console.log(this.project);
  }

}

