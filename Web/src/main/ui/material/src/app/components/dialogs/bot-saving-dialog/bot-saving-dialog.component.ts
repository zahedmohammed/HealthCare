import { Component, OnInit, Inject } from '@angular/core';
import { Saving } from '../../../models/system-setting.model';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-bot-saving-dialog',
  templateUrl: './bot-saving-dialog.component.html',
  styleUrls: ['./bot-saving-dialog.component.scss']
})
export class BotSavingDialogComponent implements OnInit {
  saving: Saving;

  constructor(public dialogRef: MatDialogRef<BotSavingDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
    this.saving = this.data;
  }

}
