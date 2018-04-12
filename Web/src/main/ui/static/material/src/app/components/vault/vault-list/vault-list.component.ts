import { Component, OnInit, ViewChild } from '@angular/core';
import { VaultService } from '../../../services/vault.service';

@Component({
  selector: 'app-vault-list',
  templateUrl: './vault-list.component.html',
  styleUrls: ['./vault-list.component.scss'],
  providers: [VaultService]
})
export class VaultListComponent implements OnInit {

  keys;
  showSpinner: boolean = false;
  constructor(private vaultService: VaultService) { }

  ngOnInit() {
    this.showSpinner = true;
    this.vaultService.get().subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.keys = results['data'];
    }, error => {
      console.log("Unable to fetch keys");
      alert(error);
    });
  }

}