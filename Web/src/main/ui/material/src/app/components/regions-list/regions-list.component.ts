import { Component, OnInit, ViewChild } from '@angular/core';
import { RegionsService } from '../../services/regions.service';
import { Handler } from '../dialogs/handler/handler';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';

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
  displayedColumns: string[] = ['name', 'region', 'createdDate', 'count', 'status'];
  dataSource = null;
  keyword: string = '';

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
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
      this.dataSource = new MatTableDataSource(this.list);
      this.dataSource.sort = this.sort;
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  //search bots by name
  searchBot(){
    this.handler.activateLoader();
    this.regionService.searchBot(this.keyword, this.page, this.pageSize).subscribe(results => {
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