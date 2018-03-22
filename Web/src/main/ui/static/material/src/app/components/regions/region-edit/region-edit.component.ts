import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { RegionsService } from '../../../services/regions.service';
import { OrgService } from '../../../services/org.service';
import { Region } from '../../../models/regions.model';


@Component({
  selector: 'app-regions-edit',
  templateUrl: './region-edit.component.html',
  styleUrls: ['./region-edit.component.scss'],
  providers: [RegionsService, OrgService]
})
export class RegionEditComponent implements OnInit {

  showSpinner: boolean = false;
  entry: Region = new Region();
  orgs;
  constructor(private regionsService: RegionsService, private orgService: OrgService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getById(params['id']);
        this.getOrgs();
      }
    });
  }

  getById(id: string) {
    this.showSpinner = true;
    this.regionsService.getById(id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.entry = results['data'];
      console.log(this.entry);
    }, error => {
      console.log("Unable to fetch Bot");
    });
  }

  update() {
    console.log(this.entry);
    this.regionsService.update(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/regions']);
    }, error => {
      console.log("Unable to update Bot");
    });
  }

  delete() {
    console.log(this.entry);
    this.regionsService.delete(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/regions']);
    }, error => {
      console.log("Unable to delete entry");
    });
  }

  getOrgs() {
    this.orgService.getByUser().subscribe(results => {
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

  visibilities = ['PRIVATE', 'PUBLIC'];

}



