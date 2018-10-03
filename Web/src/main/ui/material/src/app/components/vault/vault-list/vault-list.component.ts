import { Component, OnInit, ViewChild } from '@angular/core';
import { VaultService } from '../../../services/vault.service';
import { Handler } from '../../dialogs/handler/handler';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-vault-list',
  templateUrl: './vault-list.component.html',
  styleUrls: ['./vault-list.component.scss'],
  providers: [VaultService]
})
export class VaultListComponent implements OnInit {

  keys;
  showSpinner: boolean = false;
  displayedColumns: string[] = ['key', 'createDate'];
  dataSource = null;
  keyword: string = '';

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private vaultService: VaultService, private handler: Handler) { }

  ngOnInit() {
    this.list();
  }

  list() {
    this.handler.activateLoader();
    this.vaultService.get(this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.keys = results['data'];
      this.length = results['totalElements'];
      this.dataSource = new MatTableDataSource(this.keys);
      this.dataSource.sort = this.sort;
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  searchVault() {
if(this.keyword.length>1){
    this.handler.activateLoader();
    this.vaultService.searchVault(this.keyword, this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.keys = results['data'];
      this.length = results['totalElements'];
      this.dataSource = new MatTableDataSource(this.keys);
      this.dataSource.sort = this.sort;
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
  else if(this.keyword.length==0){
    this.list();
   }
  }

  length = 0;
  page = 0;
  pageSize = 10;
  change(evt) {
    this.page = evt['pageIndex'];
    this.list();
  }

}