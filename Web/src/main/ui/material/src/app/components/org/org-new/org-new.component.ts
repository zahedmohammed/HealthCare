import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { OrgService } from '../../../services/org.service';
import { Org } from '../../../models/org.model';
import { Handler } from '../../dialogs/handler/handler';


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
  constructor(private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
  }

  create() {
    this.handler.activateLoader();
    this.orgService.create(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.router.navigate(['/app/orgs']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

}
