import { Component, OnInit } from '@angular/core';
import { TestSuiteService } from '../../../services/test-suite.service';
import { Handler } from '../../dialogs/handler/handler';

@Component({
  selector: 'app-marketplace-list',
  templateUrl: './marketplace-list.component.html',
  styleUrls: ['./marketplace-list.component.scss'],
  providers: [TestSuiteService]
})
export class MarketplaceListComponent implements OnInit {

  offers;
  showSpinner: boolean = false;
  constructor(private testSuiteService: TestSuiteService, private handler: Handler) { }

  ngOnInit() {
    this.get();
  }

  get() {
    this.handler.activateLoader();
    this.testSuiteService.get(this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.offers = results['data'];
      this.length = results['totalElements'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  length = 0;
  page = 0;
  pageSize = 20;
  change(evt) {
    this.page = evt['pageIndex'];
    this.get();
  }

  search(keyword: string) {
    if (!keyword)
      this.get();
    this.handler.activateLoader();
    this.testSuiteService.search(keyword).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.offers = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
    console.log(keyword);

  }

  types = ['ALL', 'Dataset', 'Consulting_Services', 'AI_Skills'];

}
