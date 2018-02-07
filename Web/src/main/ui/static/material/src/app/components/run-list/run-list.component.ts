import { Component, OnInit, ViewChild } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { RunService } from '../../services/run.service';

@Component({
  selector: 'app-run-list',
  templateUrl: './run-list.component.html',
  styleUrls: ['./run-list.component.scss'],
  providers: [RunService]
})
export class RunListComponent implements OnInit {
  list;
  title:string = "";
  showSpinner: boolean = false;
  constructor(private runService: RunService, private route: ActivatedRoute) {

  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getRunByJob(params['id'])
      }
    });
  }

  getRunByJob(id: string) {
    this.showSpinner = true;
    this.runService.get(id).subscribe(results => {
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