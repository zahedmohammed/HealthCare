import { Component, OnInit } from '@angular/core';
import { CloudAccountService } from '../../../services/cloud-account.service';

@Component({
  selector: 'app-cloud-account-list',
  templateUrl: './cloud-account-list.component.html',
  styleUrls: ['./cloud-account-list.component.scss'],
  providers: [CloudAccountService]
})
export class CloudAccountListComponent implements OnInit {

  accounts;
  showSpinner: boolean = false;
  constructor(private cloudAccountService: CloudAccountService) { }

  ngOnInit() {
    this.showSpinner = true;
    this.cloudAccountService.get().subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.accounts = results['data'];
    }, error => {
      console.log("Unable to fetch accounts");
    });
  }

}
