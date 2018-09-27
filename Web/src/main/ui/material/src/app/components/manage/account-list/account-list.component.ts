import { Component, OnInit, ViewChild } from '@angular/core';
import { AccountService } from '../../../services/account.service';
import { Handler } from '../../dialogs/handler/handler';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.scss'],
  providers: [AccountService]
})
export class AccountListComponent implements OnInit {

  accounts;
  showSpinner: boolean = false;
  displayedColumns: string[] = ['name', 'type', 'create-date'];
  dataSource = null;
  keyword: string = '';

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private accountService: AccountService, private handler: Handler) { }

  ngOnInit() {
    this.list();
  }

  list() {
    this.handler.activateLoader();
    this.accountService.get(this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.accounts = results['data'];
      this.dataSource = new MatTableDataSource(this.accounts);
      this.dataSource.sort = this.sort;
      this.length = results['totalElements'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  //Search Account By Name
  searchAccount(){
    this.handler.activateLoader();
    this.accountService.searchAccount(this.keyword, this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.accounts = results['data'];
      this.length = results['totalElements'];
      this.dataSource = new MatTableDataSource(this.accounts);
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
    this.list();
  }

}
