import {Component, OnInit, Inject} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {Routes, RouterModule, Router, ActivatedRoute}from "@angular/router";
import {AccountService}from '../../../services/account.service';
import {OrgService}from '../../../services/org.service';
import {SnackbarService}from '../../../services/snackbar.service';
import {Account} from '../../../models/account.model';
import {Handler}from '../../dialogs/handler/handler';

@Component({
  selector: 'app-register',
  templateUrl: './issue-tracker-register.component.html',
  styleUrls: ['./issue-tracker-register.component.scss'],
  providers: [AccountService, OrgService, SnackbarService]
})
export class IssueTrackerRegisterComponent implements OnInit{
  entry: Account = new Account();
  accountTypes = ['GitHub', 'Jira'];
  isValid: boolean = true;
  constructor(private accountService: AccountService,
    private orgService: OrgService,
    private handler: Handler,
    private snackbarService: SnackbarService,
    private route: ActivatedRoute,
    private router: Router,
    public dialogRef: MatDialogRef<IssueTrackerRegisterComponent>,
    private dialog: MatDialog) {}

  ngOnInit() {
  }

  create() {
    this.handler.activateLoader();
    this.snackbarService.openSnackBar(this.entry.name + " registering...", "");
    this.accountService.create(this.entry).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
    this.snackbarService.openSnackBar(this.entry.name + " registered successfully", "");
    this.onClose();
    // this.router.navigateByUrl('/app/projects', {skipLocationChange: true}).then(()=>
    //   this.router.navigate(['/app/projects/new']));

    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  onClose(){
    this.dialogRef.close();
  }

  changeValue(valid: boolean) {
      this.isValid = valid;
  }



}
