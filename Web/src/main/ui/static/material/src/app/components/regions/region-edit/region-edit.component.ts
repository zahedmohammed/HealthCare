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


  GCP_REGIONS = ['NORTHAMERICA-NORTHEAST1','US-CENTRAL','US-EAST1','US-EAST4','SOUTHAMERICA-EAST1','EUROPE-WEST','EUROPE-WEST2','EUROPE-WEST3','ASIA-NORTHEAST1','ASIA-SOUTH1','AUSTRALIA-SOUTHEAST1'];
  AWS_REGIONS = ['US-EAST-1','US-EAST-2','US-WEST-1','US-WEST-2','CA-CENTRAL-1','EU-CENTRAL-1','EU-WEST-1','EU-WEST-2','EU-WEST-3','AP-NORTHEAST-1','AP-NORTHEAST-2','AP-NORTHEAST-3','AP-SOUTHEAST-1','AP-SOUTHEAST-2','AP-SOUTH-1','SA-EAST-1'];
  AZURE_REGIONS = [];

  regions = [];


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

 getRegions(){
    if (this.entry.cloudType == 'GCP'){
        this.regions = this.GCP_REGIONS;
    }
    if (this.entry.cloudType == 'AWS'){
        this.regions = this.AWS_REGIONS;
    }
    if (this.entry.cloudType == 'AZURE'){
        this.regions = this.AZURE_REGIONS;
    }

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



