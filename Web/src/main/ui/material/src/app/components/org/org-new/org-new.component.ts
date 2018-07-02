import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { OrgService } from '../../../services/org.service';
import { Org } from '../../../models/org.model';
import { Handler } from '../../dialogs/handler/handler';
import { MatSnackBar, MatSnackBarConfig }from '@angular/material';


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
  config;
  constructor(private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler, public snackBar: MatSnackBar) { }

  ngOnInit() {
  this.config = new MatSnackBarConfig();
  }

  create() {
    this.handler.activateLoader();
    this.orgService.create(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
        this.config.verticalPosition = 'top';
        this.config.horizontalPosition = 'right';
        this.config.duration = 3000;
        this.snackBar.open("Organization " + this.entry.name + " Successfully Created", "", this.config);
      this.router.navigate(['/app/orgs']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

}
