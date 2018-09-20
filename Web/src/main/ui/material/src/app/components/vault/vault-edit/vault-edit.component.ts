import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { VaultService } from '../../../services/vault.service';
import { OrgService } from '../../../services/org.service';
import { Vault } from '../../../models/vault.model';
import { Handler } from '../../dialogs/handler/handler';
import { DeleteDialogComponent } from '../../dialogs/delete-dialog/delete-dialog.component';
import { VERSION, MatDialog, MatDialogRef } from '@angular/material';
import { SnackbarService } from '../../../services/snackbar.service';

@Component({
  selector: 'app-vault-edit',
  templateUrl: './vault-edit.component.html',
  styleUrls: ['./vault-edit.component.scss'],
  providers: [VaultService, OrgService, SnackbarService]
})
export class VaultEditComponent implements OnInit {

  showSpinner: boolean = false;
  entry: Vault = new Vault();
  orgs;
  constructor(private vaultService: VaultService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler, public dialog: MatDialog, private snackbarService: SnackbarService) { }

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
    this.snackbarService.openSnackBar(this.entry.key + " saving...", "");
    this.vaultService.update(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.snackbarService.openSnackBar(this.entry.key + " saved successfully", "");
      this.router.navigate(['/app/vault']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  delete() {
    var result = confirm("Are you sure you want to delete '" + this.entry.key + "'?");
    if (result == true) {
      this.handler.activateLoader();
      this.vaultService.delete(this.entry).subscribe(results => {
        this.handler.hideLoader();
        if (this.handler.handle(results)) {
          return;
        }
        this.snackbarService.openSnackBar(this.entry.key + " deleted successfully", "");
        this.router.navigate(['/app/vault']);
      }, error => {
        this.handler.hideLoader();
        this.handler.error(error);

      });
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

  visibilities = ['PRIVATE', 'ORG_PUBLIC'];

}
