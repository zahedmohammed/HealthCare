import { Component, OnInit, ViewChild } from '@angular/core';
import { VaultService } from '../../../services/vault.service';
import { Handler } from '../../dialogs/handler/handler';

@Component({
  selector: 'app-vault-list',
  templateUrl: './vault-list.component.html',
  styleUrls: ['./vault-list.component.scss'],
  providers: [VaultService]
})
export class VaultListComponent implements OnInit {

  keys;
  showSpinner: boolean = false;
  constructor(private vaultService: VaultService, private handler: Handler) { }

  ngOnInit() {
    this.list();
  }

  list() {
    this.handler.activateLoader();
    this.vaultService.get().subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.keys = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

}