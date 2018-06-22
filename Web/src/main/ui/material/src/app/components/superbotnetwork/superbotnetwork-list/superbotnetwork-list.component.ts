import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { RegionsService } from '../../../services/regions.service';
import { Handler } from '../../dialogs/handler/handler';

@Component({
  selector: 'app-superbotnetwork-list',
  templateUrl: './superbotnetwork-list.component.html',
  styleUrls: ['./superbotnetwork-list.component.scss'],
  providers: [RegionsService]
})
export class SuperbotnetworkListComponent implements OnInit {

  list;
  showSpinner: boolean = false;
  constructor(private regionService: RegionsService, private handler: Handler) { }

  ngOnInit() {
    this.get();
  }


 get() {
    this.handler.activateLoader();
    this.regionService.getSuperBotNetwork(this.page, this.pageSize).subscribe(results => {
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
