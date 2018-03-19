import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { OrgService } from '../../../services/org.service';
import { Org } from '../../../models/org.model';


@Component({
  selector: 'app-org-edit',
  templateUrl: './org-edit.component.html',
  styleUrls: ['./org-edit.component.scss'],
  providers: [OrgService]
})
export class OrgEditComponent implements OnInit {

  showSpinner: boolean = false;
  entry: Org = new Org();
  orgs;
  constructor(private orgService: OrgService, private route: ActivatedRoute, private router: Router) { }

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
    this.orgService.getById(id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.entry = results['data'];
      console.log(this.entry);
    }, error => {
      console.log("Unable to fetch org");
    });
  }

  update() {
    console.log(this.entry);
    this.orgService.update(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/orgs']);
    }, error => {
      console.log("Unable to update org");
    });
  }

}
