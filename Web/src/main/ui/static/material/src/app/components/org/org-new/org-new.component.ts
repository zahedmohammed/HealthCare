import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { OrgService } from '../../../services/org.service';
import { Org } from '../../../models/org.model';


@Component({
  selector: 'app-org-new',
  templateUrl: './org-new.component.html',
  styleUrls: ['./org-new.component.scss'],
  providers: [OrgService]
})
export class OrgNewComponent implements OnInit {

  showSpinner: boolean = false;
  orgs;
  entry: Org = new Org();
  constructor(private orgService: OrgService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
  }

  create() {
    console.log(this.entry);
    this.showSpinner = true;
    this.orgService.create(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/orgs']);
    }, error => {
      console.log("Unable to save org entry");
    });
  }

}
