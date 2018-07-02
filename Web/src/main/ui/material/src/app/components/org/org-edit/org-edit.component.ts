import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { OrgService } from '../../../services/org.service';
import { Org } from '../../../models/org.model';
import { Handler } from '../../dialogs/handler/handler';
import {VERSION, MatDialog, MatDialogRef, MatSnackBar, MatSnackBarConfig }from '@angular/material';

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
  config;
  constructor(private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler, public snackBar: MatSnackBar) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getById(params['id']);
        this.config = new MatSnackBarConfig();
      }
    });
  }

  getById(id: string) {
    this.handler.activateLoader();
    this.orgService.getById(id).subscribe(results => {
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
    this.orgService.update(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
        this.config.verticalPosition = 'top';
        this.config.horizontalPosition = 'right';
        this.config.duration = 3000;
        this.snackBar.open("Organization " + this.entry.name + " Successfully Updated", "", this.config);
        this.router.navigate(['/app/orgs']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

}
