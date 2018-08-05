import { Component, OnInit, Inject } from '@angular/core';
import { MatTabChangeEvent}from '@angular/material';
import { Routes, RouterModule, Router, ActivatedRoute} from "@angular/router";
import { JobsService } from '../../../services/jobs.service';
import { ProjectService } from '../../../services/project.service';
import { Project } from '../../../models/project.model';
import { Jobs, Noti, Cron } from '../../../models/jobs.model';
import { AccountService } from '../../../services/account.service';
import { TestSuiteService } from '../../../services/test-suite.service';
import { Account } from '../../../models/account.model';
import { Handler } from '../../dialogs/handler/handler';
import { APPCONFIG } from '../../../config';
import { VERSION, MatDialog, MatDialogRef, MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { DeleteDialogComponent}from '../../dialogs/delete-dialog/delete-dialog.component';
import { SnackbarService}from '../../../services/snackbar.service';

import {FormBuilder, FormGroup, Validators , FormArray}from '@angular/forms';
//import { MatStepper } from '@angular/material';
import { TestSuite,TestCase} from '../../../models/test-suite.model';


@Component({
 selector: 'app-test-suite-edit',
  templateUrl: './test-suite-edit.component.html',
  styleUrls: ['./test-suite-edit.component.scss'],
  providers: [ProjectService, SnackbarService]
})
export class TestSuiteEditComponent implements OnInit {
  id: string;
  project: Project = new Project();
  testSuite: TestSuite = new TestSuite();
testSuiteId:string;
testCases: FormArray;

  types: string[] = ["Suite", "Abstract", "Dataset", "Consulting_Services", "AI_Skills"];
methods: string[] = ["GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE"];
 testSuite1:any={assertion:''};

  context: string = "New";
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;

  constructor(private projectService: ProjectService,private testSuiteService: TestSuiteService,
            private route: ActivatedRoute, private router: Router, private handler: Handler,
            public snackBar: MatSnackBar, private snackbarService: SnackbarService, private _formBuilder: FormBuilder) { }

  ngOnInit() {

    this.route.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.loadProject(this.id);

     //   this.getITAccounts();
     //   this.getNotifyAccounts();
      }
 this.testSuiteId = params['testSuiteId'];
if (params['testSuiteId']) {
 this.loadTestSuite(params['testSuiteId']);
}
    });
    this.thirdFormGroup = this._formBuilder.group({
 nameCtrl:  ['', Validators.required],
testCases: this._formBuilder.array([  ])
    });
  }

  loadTestSuite(id) {
    this.handler.activateLoader();
    this.testSuiteService.getByTestSuiteId(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.testSuite = results['data'];
     let k:TestCase = new TestCase();
if(this.testSuite.assertions!=null)
this.testSuite1.assertion=this.testSuite.assertions.join(",");
if(this.testSuite.headers!=null)
this.testSuite1.headers=this.testSuite.headers.join(",");
if(this.testSuite.tags!=null)
this.testSuite1.tags=this.testSuite.tags.join(",");
if(this.testSuite.authors!=null)
this.testSuite1.authors=this.testSuite.authors.join(",");
if(this.testSuite.init!=null)
this.testSuite1.init=this.testSuite.init.join(",");
if(this.testSuite.cleanup!=null)
this.testSuite1.cleanup=this.testSuite.cleanup.join(",");
 if(this.testSuite.testCases.length==0 ) this.testSuite.testCases=[k];
          for(var i=0;i<this.testSuite.testCases.length;i++) this.addItem1(this.testSuite.testCases[i]);
//this.thirdFormGroup.value.type=this.testSuite.type;
console.log(this.thirdFormGroup);
   //   this.loadProject();
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
removeItem(i: number) {
    // remove address from the list
    const control = <FormArray>this.thirdFormGroup.controls['testCases'];
    control.removeAt(i);
}
addItem(): void {
  this.testCases = this.thirdFormGroup.get('testCases') as FormArray;
  this.testCases.push(this.createItem());
}
addItem1(obj): void {
  this.testCases = this.thirdFormGroup.get('testCases') as FormArray;
  this.testCases.push(this.createItem1(obj));
}
geInfo(obj){
console.log("sss");
}
createItem(): FormGroup {
  return this._formBuilder.group({
  id:null,
body: [null, Validators.required],
inactive:null
  });
}
createItem1(obj:TestCase): FormGroup {
  var k= this._formBuilder.group({
  id:obj.id,
body: [obj.body, Validators.required],
inactive:obj.inactive
  });
return k;
}
  loadProject(id: string) {
    this.handler.activateLoader();
    this.projectService.getById(id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.project = results['data'];
this.testSuite.project=this.project;
      //this.job['project'] = this.project;
     // this.context = this.project.name + " > Edit";
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
saveTestSuite(){
var groupVal=this.thirdFormGroup.value;
this.testSuite.testCases=groupVal.testCases;

if(this.testSuite1.assertion!=null)
this.testSuite.assertions=this.testSuite1.assertion.split(",");
if(this.testSuite1.headers!=null)
this.testSuite.headers=this.testSuite1.headers.split(",");
 if(this.testSuite1.tags!=null)
this.testSuite.tags=this.testSuite1.tags.split(",");
 if(this.testSuite1.authors!=null)
this.testSuite.authors=this.testSuite1.authors.split(",");
 if(this.testSuite1.authors!=null)
this.testSuite.init=this.testSuite1.init.split(",");
 if(this.testSuite1.cleanup!=null)
this.testSuite.cleanup=this.testSuite1.cleanup.split(",");

//console.log(this.testSuite);
this.handler.activateLoader();
      this.snackbarService.openSnackBar("'TestSuite '" + this.testSuite.name + "' creating...", "");
      this.testSuiteService.update(this.testSuite).subscribe(results => {
        this.handler.hideLoader();
        if (this.handler.handle(results)) {
            return;
        }
        this.snackbarService.openSnackBar("'TestSuite '" + this.project.name + "' created successfully", "");
        this.router.navigate(['/app/projects', this.id, 'test-suites']);
       // this.project = results['data'];

    }, error => {
        this.handler.hideLoader();
        this.handler.error(error);
    });
}
}
