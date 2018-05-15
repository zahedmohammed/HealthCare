import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { VaultService } from '../../../services/vault.service';
import { OrgService } from '../../../services/org.service';
import { Vault } from '../../../models/vault.model';
import { Handler } from '../../dialogs/handler/handler';

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
  constructor(private vaultService: VaultService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

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
