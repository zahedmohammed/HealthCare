import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-jenkins-integration',
  templateUrl: './jenkins-integration.component.html',
  styleUrls: ['./jenkins-integration.component.scss']
})
export class JenkinsIntegrationComponent implements OnInit {
  integration: string = "wget https://raw.githubusercontent.com/intesar/Fx-Docker-Script/master/fx_job_invoke_script.sh?token=AElo1orWSWln6w6y5uX0-1ylzPH8TX_3ks5bqJBywA%3D%3D -O fx_job_invoke_script.sh;bash fx_job_invoke_script.sh {Username} {Password}";

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<JenkinsIntegrationComponent>) { }
  ngOnInit() {
  }

}
