import { Component, OnInit, ViewChild } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { OrgService } from '../../../services/org.service';
import { Org, OrgUser } from '../../../models/org.model';
import { User } from '../../../models/users.model';
import { Handler } from '../../dialogs/handler/handler';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
  providers: [OrgService]
})
export class UserListComponent implements OnInit {

  showSpinner: boolean = false;
  org: Org = new Org();
  id;
  orgUsers;
  displayedColumns: string[] = ['name', 'role', 'status', 'createDate', 'password'];
  dataSource = null;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.id = params['id'];
        this.getById(params['id']);
        this.getOrgUsersById(params['id']);
      }
    });
  }

  length = 0;
  page = 0;
  pageSize = 10;
  change(evt) {
    this.page = evt['pageIndex'];
    this.getOrgUsersById(this.id);
  }

  getById(id: string) {
    this.handler.activateLoader();
    this.orgService.getById(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.org = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getOrgUsersById(id: string) {
    this.handler.activateLoader();
    this.orgService.getOrgUsersById(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.orgUsers = results['data'];
      this.length = results['totalElements'];
      this.dataSource = new MatTableDataSource(this.orgUsers);
      this.dataSource.sort = this.sort;
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

}
