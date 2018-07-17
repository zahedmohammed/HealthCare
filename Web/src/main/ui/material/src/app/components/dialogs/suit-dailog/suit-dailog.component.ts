import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-suit-dailog',
  templateUrl: './suit-dailog.component.html',
  styleUrls: ['./suit-dailog.component.scss']
})
export class SuitDailogComponent implements OnInit {

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: any,
        public dialogRef: MatDialogRef<SuitDailogComponent>
    ) { }
    ngOnInit() {
    }

}
