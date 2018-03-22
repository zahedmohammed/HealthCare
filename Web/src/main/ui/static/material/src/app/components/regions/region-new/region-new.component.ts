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

  regions = ['US_WEST', 'US_EAST' , 'EUROPE_1'];

}

