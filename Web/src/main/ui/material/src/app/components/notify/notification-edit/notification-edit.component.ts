import { Component, OnInit } from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { NotificationService } from '../../../services/notification.service';
import { AccountService } from '../../../services/account.service';
import { OrgService } from '../../../services/org.service';
import { Account } from '../../../models/account.model';
import { Notification } from '../../../models/notification.model';
import { Handler } from '../../dialogs/handler/handler';
import {VERSION, MatDialog, MatDialogRef, MatSnackBar, MatSnackBarConfig }from '@angular/material';
import {DeleteDialogComponent}from '../../dialogs/delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-notification-edit',
  templateUrl: './notification-edit.component.html',
  styleUrls: ['./notification-edit.component.scss'],
  providers: [NotificationService, AccountService, OrgService]
})
export class NotificationEditComponent implements OnInit {

  showSpinner: boolean = false;
  entry: Notification = new Notification();
  orgs;
  accounts;
  config = new MatSnackBarConfig();
  types = ['SLACK','EMAIL'];
  constructor(private notificationService: NotificationService, private accountService: AccountService, private orgService: OrgService, private route: ActivatedRoute, private router: Router, private handler: Handler, public dialog: MatDialog, public snackBar: MatSnackBar) { }

    ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getById(params['id']);
        //this.getOrgs();
        this.getAccountyForNotificationHubType();
      }
    });
  }

  getById(id: string) {
    this.handler.activateLoader();
    this.notificationService.getById(id).subscribe(results => {
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
      this.notificationService.update(this.entry).subscribe(results => {
          this.handler.hideLoader();
          if (this.handler.handle(results)) {
              return;
          }
          this.config.verticalPosition = 'top';
          this.config.horizontalPosition = 'right';
          this.config.duration = 3000;
          this.snackBar.open("Notification " + this.entry.name + " Successfully Updated", "", this.config);
          this.router.navigate(['/app/notification-accounts']);
      }, error => {
          this.handler.hideLoader();
          this.handler.error(error);
      });
  }

  delete() {
    let dialogRef = this.dialog.open(DeleteDialogComponent, {
        data: {
            entry: this.entry
        }
    });

    dialogRef.afterClosed().subscribe(result => {
        if (result != null) {
            this.handler.activateLoader();
            this.notificationService.delete(this.entry).subscribe(results => {
                this.handler.hideLoader();
                if (this.handler.handle(results)) {
                    return;
                }
                let config = new MatSnackBarConfig();
                config.verticalPosition = 'top';
                config.horizontalPosition = 'right';
                config.duration = 3000;
                this.snackBar.open("Notification " + this.entry.name + " Successfully Deleted", "", config);
                this.router.navigate(['/app/notification-accounts']);
            }, error => {
                this.handler.hideLoader();
                this.handler.error(error);

            });
        }
    });
}

  getOrgs() {
    this.orgService.getByUser().subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.orgs = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  getAccountyForNotificationHubType() {
    this.handler.activateLoader();
    this.accountService.getAccountByAccountType('NOTIFICATION_HUB').subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.accounts = results['data'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

 setAccount(account){
     this.entry.account.accountType =  account.accountType;
 }
  visibilities = ['PRIVATE', 'ORG_PUBLIC'];
}


