import { Component, OnInit } from '@angular/core';
import { CloudAccountService } from '../../../services/cloud-account.service';
import { Handler } from '../../dialogs/handler/handler';

@Component({
  selector: 'app-cloud-account-list',
  templateUrl: './cloud-account-list.component.html',
  styleUrls: ['./cloud-account-list.component.scss'],
  providers: [CloudAccountService]
})
export class CloudAccountListComponent implements OnInit {

  accounts;
  showSpinner: boolean = false;
  constructor(private cloudAccountService: CloudAccountService, private handler: Handler) { }

  ngOnInit() {
    this.handler.activateLoader();
    this.cloudAccountService.get().subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.accounts = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

}
