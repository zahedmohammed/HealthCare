import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { VaultService } from '../../../services/vault.service';
import { OrgService } from '../../../services/org.service';
import { Vault } from '../../../models/vault.model';

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
  constructor(private vaultService: VaultService, private orgService: OrgService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getById(params['id']);
        this.getOrgs();
      }
    });
  }

  getById(id: string) {
    this.showSpinner = true;
    this.vaultService.getById(id).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.entry = results['data'];
      console.log(this.entry);
    }, error => {
      console.log("Unable to fetch vault");
      alert(error);
    });
  }

  update() {
    console.log(this.entry);
    this.vaultService.update(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/vault']);
    }, error => {
      console.log("Unable to update vault");
      alert(error);
    });
  }

  delete() {
    console.log(this.entry);
    this.vaultService.delete(this.entry).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      console.log(results);
      this.router.navigate(['/app/vault']);
    }, error => {
      console.log("Unable to delete entry");
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
