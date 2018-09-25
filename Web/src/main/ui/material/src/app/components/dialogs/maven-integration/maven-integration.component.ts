import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-maven-integration',
  templateUrl: './maven-integration.component.html',
  styleUrls: ['./maven-integration.component.scss']
})
export class MavenIntegrationComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<MavenIntegrationComponent>) { }

  ngOnInit() {
  }

}
