import { Component, OnInit, ViewChild } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { RegionsService } from '../../../services/regions.service';
import { Handler } from '../../dialogs/handler/handler';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-superbotnetwork-list',
  templateUrl: './superbotnetwork-list.component.html',
  styleUrls: ['./superbotnetwork-list.component.scss'],
  providers: [RegionsService]
})
export class SuperbotnetworkListComponent implements OnInit {

  list;
  showSpinner: boolean = false;
  displayedColumns: string[] = ['name', 'region', 'count', 'cost'];
  dataSource = null;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
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
      this.dataSource = new MatTableDataSource(this.list);
      this.dataSource.sort = this.sort;
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  length = 0;
  page = 0;
  pageSize = 10;
  change(evt) {
    this.page = evt['pageIndex'];
    this.get();
  }
}
