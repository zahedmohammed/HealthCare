import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { RegionsService } from '../../../services/regions.service';
import { CloudAccountService } from '../../../services/cloud-account.service';
import { OrgService } from '../../../services/org.service';
import { Region } from '../../../models/regions.model';


@Component({
  selector: 'app-region-new',
  templateUrl: './region-new.component.html',
  styleUrls: ['./region-new.component.scss'],
  providers: [RegionsService, OrgService]
})
export class RegionNewComponent implements OnInit {


  GCP_REGIONS = ['NORTHAMERICA-NORTHEAST1','US-CENTRAL','US-EAST1','US-EAST4','SOUTHAMERICA-EAST1','EUROPE-WEST','EUROPE-WEST2','EUROPE-WEST3','ASIA-NORTHEAST1','ASIA-SOUTH1','AUSTRALIA-SOUTHEAST1'];
  AWS_REGIONS = ['US-EAST-1','US-EAST-2','US-WEST-1','US-WEST-2','CA-CENTRAL-1','EU-CENTRAL-1','EU-WEST-1','EU-WEST-2','EU-WEST-3','AP-NORTHEAST-1','AP-NORTHEAST-2','AP-NORTHEAST-3','AP-SOUTHEAST-1','AP-SOUTHEAST-2','AP-SOUTH-1','SA-EAST-1'];
  AZURE_REGIONS = [];

  regions = [];

  showSpinner: boolean = false;
  cloudAccounts;
  entry: Region = new Region();
  constructor(private regionsService: RegionsService, private cloudAccountService: CloudAccountService,  private orgService: OrgService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.getCloudAccounts();
  }

  create() {
    console.log(this.entry);
    this.showSpinner = true;
    this.regionsService.create(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/regions']);
    }, error => {
      console.log("Unable to save Bot entry");
    });
  }

  getRegions(){
    alert('getRegions');
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

  getCloudAccounts() {
    this.cloudAccountService.get().subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.cloudAccounts = results['data'];
    }, error => {
      console.log("Unable to fetch Cloud Accounts");
    });
  }
  visibilities = ['PRIVATE', 'PUBLIC'];
}

