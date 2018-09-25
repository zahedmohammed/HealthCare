import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-gradle-integration',
  templateUrl: './gradle-integration.component.html',
  styleUrls: ['./gradle-integration.component.scss']
})
export class GradleIntegrationComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<GradleIntegrationComponent>) { }

  ngOnInit() {
  }

}
