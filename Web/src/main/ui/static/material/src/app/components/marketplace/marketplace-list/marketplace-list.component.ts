import { Component, OnInit } from '@angular/core';
import { TestSuiteService } from '../../../services/test-suite.service';

@Component({
  selector: 'app-marketplace-list',
  templateUrl: './marketplace-list.component.html',
  styleUrls: ['./marketplace-list.component.scss'],
  providers: [TestSuiteService]
})
export class MarketplaceListComponent implements OnInit {

  offers;
  showSpinner: boolean = false;
  constructor(private testSuiteService: TestSuiteService) { }

  ngOnInit() {
    this.get();
  }

  get() {
    this.showSpinner = true;
    this.testSuiteService.get().subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.offers = results['data'];
    }, error => {
      console.log("Unable to fetch keys");
      alert(error);
    });
  }

  search(keyword: string) {
    if (!keyword)
      this.get();
    this.showSpinner = true;
    console.log(keyword);
    this.testSuiteService.search(keyword).subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.offers = results['data'];
    }, error => {
      console.log("Unable to fetch keys");
      alert(error);
    });
    console.log(keyword);

  }

  types = ['ALL', 'Dataset', 'Consulting_Services', 'AI_Skills'];

}
