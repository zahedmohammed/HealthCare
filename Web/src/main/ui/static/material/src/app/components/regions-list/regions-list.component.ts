import { Component, OnInit, ViewChild } from '@angular/core';
import { RegionsService } from '../../services/regions.service';

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
  constructor(private regionService: RegionsService) { }

  ngOnInit() {
    this.showSpinner = true;
    this.regionService.get().subscribe(results => {
      this.showSpinner = false;
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      this.list = results['data'];
    }, error => {
      console.log("Unable to fetch regions");
    });
  }

}