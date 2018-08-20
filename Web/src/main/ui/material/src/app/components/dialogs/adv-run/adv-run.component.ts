import{Component, Inject, OnInit}from '@angular/core';
import {Routes, RouterModule, Router, ActivatedRoute}from "@angular/router";
import {RunService}from '../../../services/run.service';
import { VERSION, MatDialog, MatDialogRef, MatSnackBar, MatSelectModule , MAT_DIALOG_DATA} from '@angular/material';
import {RegionsService}from '../../../services/regions.service';
import {Handler}from '../handler/handler';
import { Categories } from '../../../models/project-autocode-config.model';

@Component({
  selector: 'app-adv-run',
  templateUrl: './adv-run.component.html',
  styleUrls: ['./adv-run.component.scss'],
  providers: [RegionsService, RunService]
})
export class AdvRunComponent implements OnInit {

  list;
  showSpinner: boolean = false;
  length = 0;
  page = 0;
  pageSize = 100;
  regions;
  tags_;
  suites;
  newRegion;
  //categories: string[]=[];
  category: string[];
  selectedCategories:string[]=[];
  categories=['SimpleGET','Functional','Negative','UnSecured','DDOS','XSS_Injection','SQL_Injection','Log_Forging','RBAC'];

  constructor(private regionService: RegionsService, private runService: RunService, private router: Router, private handler: Handler,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<AdvRunComponent>
  ) {}

  ngOnInit() {
    this.newRegion = this.data.regions;
    this.get();
    this.tags_ = '';//this.data.tags.join(',');
    this.suites = '';

    this.selectedCategories = this.data.categories.split(",")
    .map(function(item) {
      return item.trim();
    }); 
     //this.selectedCategories=['XSS_Injection'];
    console.log("onAdvRun dialog","--->"+this.selectedCategories);
 
    //alert(this.tags_);
   }

  get() {
    this.handler.activateLoader();
    this.regionService.getEntitled(this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.list = results['data'];
      this.length = results['totalElements'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  run() {
    if (this.regions) {
      this.newRegion = this.regions['org']['name'] + "/" + this.regions['name'];
    }
      this.dialogRef.close()
    this.runService.advRun(this.data.id, this.newRegion, this.tags_, this.suites,this.selectedCategories).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.router.navigate(['/app/projects/' , this.data.project.id,  'jobs', this.data.id, 'runs']);
    }, error => {
      this.handler.error(error);
    });
  }

}
