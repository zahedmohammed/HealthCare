import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { VaultService } from '../../../services/vault.service';
import { OrgService } from '../../../services/org.service';
import { Vault } from '../../../models/vault.model';
import { Handler } from '../../dialogs/handler/handler';
import { MatSnackBar, MatSnackBarConfig }from '@angular/material';

@Component({
  selector: 'app-vault-new',
  templateUrl: './vault-new.component.html',
  styleUrls: ['./vault-new.component.scss'],
  providers: [VaultService, OrgService]
})
export class VaultNewComponent implements OnInit {

  showSpinner: boolean = false;
  orgs;
  entry: Vault = new Vault();
  config = new MatSnackBarConfig();
  constructor(private vaultService: VaultService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler, public snackBar: MatSnackBar) { }

  ngOnInit() {
    //this.getOrgs();
  }

  create() {
    this.handler.activateLoader();
    this.vaultService.create(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
        this.config.verticalPosition = 'top';
        this.config.horizontalPosition = 'right';
        this.config.duration = 3000;
        this.snackBar.open("Vault " + this.entry.key + " Successfully Created", "", this.config);
        this.router.navigate(['/app/vault']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
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
