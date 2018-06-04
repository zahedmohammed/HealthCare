import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { OrgService } from '../../../services/org.service';
import { Org } from '../../../models/org.model';
import { Handler } from '../../dialogs/handler/handler';


@Component({
  selector: 'app-org-edit',
  templateUrl: './org-edit.component.html',
  styleUrls: ['./org-edit.component.scss'],
  providers: [OrgService]
})
export class OrgEditComponent implements OnInit {

  showSpinner: boolean = false;
  entry: Org = new Org();
  orgs;
  constructor(private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getById(params['id']);
      }
    });
  }

  getById(id: string) {
    this.handler.activateLoader();
    this.orgService.getById(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.entry = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  update() {
    this.handler.activateLoader();
    this.orgService.update(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.router.navigate(['/app/orgs']);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

}