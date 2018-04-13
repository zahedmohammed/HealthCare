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
    this.handler.activateLoader();
    this.regionService.get().subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.list = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

}