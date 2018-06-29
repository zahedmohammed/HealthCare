import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { VaultService } from '../../../services/vault.service';
import { OrgService } from '../../../services/org.service';
import { Vault } from '../../../models/vault.model';
import { Handler } from '../../dialogs/handler/handler';
import {VERSION, MatDialog, MatDialogRef, MatSnackBar, MatSnackBarConfig }from '@angular/material';
import {DeleteDialogComponent}from '../../dialogs/delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-vault-edit',
  templateUrl: './vault-edit.component.html',
  styleUrls: ['./vault-edit.component.scss'],
  providers: [VaultService, OrgService]
})
export class VaultEditComponent implements OnInit {

  showSpinner: boolean = false;
  entry: Vault = new Vault();
  orgs;
  config = new MatSnackBarConfig();
  constructor(private vaultService: VaultService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler, public dialog: MatDialog, public snackBar: MatSnackBar) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getById(params['id']);
        //this.getOrgs();
      }
    });
  }

  getById(id: string) {
    this.handler.activateLoader();
    this.vaultService.getById(id).subscribe(results => {
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
    this.vaultService.update(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
        this.config.verticalPosition = 'top';
        this.config.horizontalPosition = 'right';
        this.config.duration = 3000;
        this.snackBar.open("Vault " + this.entry.key + " Successfully Updated", "", this.config);
        this.router.navigate(['/app/vault']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

 delete() {
    let dialogRef = this.dialog.open(DeleteDialogComponent, {
        data: {
            entry: this.entry
        }
    });

    dialogRef.afterClosed().subscribe(result => {
        if (result != null) {
            this.handler.activateLoader();
            this.vaultService.delete(this.entry).subscribe(results => {
                this.handler.hideLoader();
                if (this.handler.handle(results)) {
                    return;
                }
                let config = new MatSnackBarConfig();
                config.verticalPosition = 'top';
                config.horizontalPosition = 'right';
                config.duration = 3000;
                this.snackBar.open("Vault " + this.entry.key + " Successfully Deleted", "", config);
                this.router.navigate(['/app/vault']);
            }, error => {
                this.handler.hideLoader();
                this.handler.error(error);

            });
        }
    });
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

  visibilities = ['PRIVATE', 'ORG_PUBLIC'];

}
