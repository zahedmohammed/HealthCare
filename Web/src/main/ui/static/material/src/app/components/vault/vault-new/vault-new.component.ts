import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { VaultService } from '../../../services/vault.service';
import { OrgService } from '../../../services/org.service';
import { Vault } from '../../../models/vault.model';

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
  constructor(private vaultService: VaultService, private orgService: OrgService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.getOrgs();
  }

  create() {
    console.log(this.entry);
    this.showSpinner = true;
    this.vaultService.create(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/vault']);
    }, error => {
      console.log("Unable to save vault entry");
      alert(error);
    });
  }

  getOrgs() {
    this.orgService.getByUser().subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.orgs = results['data'];
    }, error => {
      console.log("Unable to fetch orgs");
      alert(error);
    });
  }
  visibilities = ['PRIVATE', 'ORG_PUBLIC'];

}
