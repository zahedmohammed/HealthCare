import { Component, OnInit, Inject } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { RegionsService } from '../../../services/regions.service';
import {Routes, RouterModule, Router, ActivatedRoute}from "@angular/router";

@Component({
  selector: 'app-bot-dialog',
  templateUrl: './bot-dialog.component.html',
  styleUrls: ['./bot-dialog.component.scss'],
  providers: [RegionsService]
})
export class BotDialogComponent implements OnInit {

  constructor(
    private router: Router, public dialogRef: MatDialogRef<BotDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

    ngOnInit() {
    }

}
