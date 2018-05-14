import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { RegionsService } from '../../../services/regions.service';
import { OrgService } from '../../../services/org.service';
import { AccountService } from '../../../services/account.service';
import { Region } from '../../../models/regions.model';
import { Handler } from '../../dialogs/handler/handler';


@Component({
  selector: 'app-regions-edit',
  templateUrl: './region-edit.component.html',
  styleUrls: ['./region-edit.component.scss'],
  providers: [RegionsService, OrgService]
})
export class RegionEditComponent implements OnInit {


  GCP_REGIONS = ['NORTHAMERICA-NORTHEAST1','US-CENTRAL','US-EAST1','US-EAST4','SOUTHAMERICA-EAST1','EUROPE-WEST','EUROPE-WEST2','EUROPE-WEST3','ASIA-NORTHEAST1','ASIA-SOUTH1','AUSTRALIA-SOUTHEAST1'];
 //AWS_REGIONS = ['US-EAST-1','US-EAST-2','US-WEST-1','US-WEST-2','CA-CENTRAL-1','EU-CENTRAL-1','EU-WEST-1','EU-WEST-2','EU-WEST-3','AP-NORTHEAST-1','AP-NORTHEAST-2','AP-NORTHEAST-3','AP-SOUTHEAST-1','AP-SOUTHEAST-2','AP-SOUTH-1','SA-EAST-1'];
    AWS_REGIONS = ['US-WEST-1'];
  AZURE_REGIONS = [];

  regions = [];


  showSpinner: boolean = false;
  accounts;
  entry: Region = new Region();
  orgs;
  constructor(private regionsService: RegionsService, private accountService: AccountService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

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
    this.handler.activateLoader();
    this.regionsService.getById(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.entry = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  update() {
    this.handler.activateLoader();
    this.regionsService.update(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.router.navigate(['/app/regions']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  delete() {
    this.handler.activateLoader();
    this.regionsService.delete(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.router.navigate(['/app/regions']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getRegions(){
    if (this.entry.account.cloudType === 'GCP'){
        this.regions = this.GCP_REGIONS;
    } else
    if (this.entry.account.cloudType === 'AWS'){
        this.regions = this.AWS_REGIONS;
    } else
    if (this.entry.account.cloudType === 'AZURE'){
        this.regions = this.AZURE_REGIONS;
    }
  }

  getOrgs() {
    this.handler.activateLoader();
    this.orgService.getByUser().subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.orgs = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  visibilities = ['PRIVATE', 'PUBLIC'];

}



