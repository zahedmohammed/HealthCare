import { Component, OnInit, ViewChild } from '@angular/core';
import { RegionsService } from '../../services/regions.service';
import { Handler } from '../dialogs/handler/handler';

@Component({
  selector: 'app-regions-list',
  templateUrl: './regions-list.component.html',
  styleUrls: ['./regions-list.component.scss'],
  providers: [RegionsService]
})
export class RegionsListComponent implements OnInit {

  list;
  title:string = "Bot Regions";
  showSpinner: boolean = false;
  constructor(private regionService: RegionsService, private handler: Handler) { }

  ngOnInit() {
    this.get();
  }

  get() {
    this.handler.activateLoader();
    this.regionService.get(this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.list = results['data'];
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

}