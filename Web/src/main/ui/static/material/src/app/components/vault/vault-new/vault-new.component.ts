import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { VaultService } from '../../../services/vault.service';
import { Vault } from '../../../models/vault.model';

@Component({
  selector: 'app-vault-new',
  templateUrl: './vault-new.component.html',
  styleUrls: ['./vault-new.component.scss'],
  providers: [VaultService]
})
export class VaultNewComponent implements OnInit {

  showSpinner: boolean = false;
  entry: Vault = new Vault('', '', '', 'PRIVATE');
  constructor(private vaultService: VaultService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
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
    });
  }

}
