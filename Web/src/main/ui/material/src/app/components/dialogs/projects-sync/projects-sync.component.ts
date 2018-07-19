import { Component, OnInit } from '@angular/core';
import { ProjectService } from '../../../services/project.service';
import { ProjectSync } from '../../../models/project-sync.model';

@Component({
  selector: 'app-projects-sync',
  templateUrl: './projects-sync.component.html',
  styleUrls: ['./projects-sync.component.scss'],
  providers: [ProjectService]

})
export class ProjectsSyncComponent implements OnInit {

 // projectSync: ProjectSync = new ProjectSync();

  constructor(private projectService: ProjectService) { }

  ngOnInit() {
  }

//  projectSync() {
//    this.projectService.projectSync(this.projectSync).subscribe(results => {
//     this.handler.hideLoader();
//     if (this.handler.handle(results)) {
//       return;
//     }
//   }, error => {
//     this.handler.hideLoader();
//     this.handler.error(error);
//   });
// }
}
