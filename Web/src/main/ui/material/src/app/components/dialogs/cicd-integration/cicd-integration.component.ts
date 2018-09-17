import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {Handler}from '../handler/handler';
import { RegionsService } from '../../../services/regions.service';

@Component({
  selector: 'app-cicd-integration',
  templateUrl: './cicd-integration.component.html',
  styleUrls: ['./cicd-integration.component.scss'],
  providers: [RegionsService]

})
export class CicdIntegrationComponent implements OnInit {
  integration: string = "wget https://raw.githubusercontent.com/intesar/Fx-Docker-Script/master/fx_job_invoke_script.sh?token=AElo1orWSWln6w6y5uX0-1ylzPH8TX_3ks5bqJBywA%3D%3D -O fx_job_invoke_script.sh;bash fx_job_invoke_script.sh {Username} {Password}";

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
  public dialogRef: MatDialogRef<CicdIntegrationComponent>, private handler: Handler, private regionService: RegionsService) { }

  ngOnInit() {
  }

}
