import { Component, OnInit, ViewChild } from '@angular/core';
import { OrgService } from '../../../services/org.service';
import { Handler } from '../../dialogs/handler/handler';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-org-list',
  templateUrl: './org-list.component.html',
  styleUrls: ['./org-list.component.scss'],
  providers: [OrgService]
})
export class OrgListComponent implements OnInit {

  orgs;
  showSpinner: boolean = false;
  displayedColumns: string[] = ['name', 'company', 'billing', 'plan', 'date/time'];
  dataSource = null;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(private orgService: OrgService, private handler: Handler) { }

  ngOnInit() {
    this.list();
  }

  list() {
    this.handler.activateLoader();
    this.orgService.get(this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.orgs = results['data'];
      this.length = results['totalElements'];
      this.dataSource = new MatTableDataSource(this.orgs);
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
