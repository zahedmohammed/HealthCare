import {Component, OnInit, Inject, ViewChild} from '@angular/core';
import { MatTabChangeEvent } from '@angular/material';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
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
import { DeleteDialogComponent } from '../../dialogs/delete-dialog/delete-dialog.component';
import { SnackbarService } from '../../../services/snackbar.service';

import { FormBuilder, FormGroup, Validators, FormArray, FormControl } from '@angular/forms';
//import { MatStepper } from '@angular/material';
import { TestSuite, TestCase } from '../../../models/test-suite.model';
import 'brace/theme/github';
import 'brace/mode/yaml';


@Component({
  selector: 'app-test-suite-new',
  templateUrl: './test-suite-new.component.html',
  styleUrls: ['./test-suite-new.component.scss'],
  providers: [ProjectService, SnackbarService,]
})

export class TestSuiteNewComponent implements OnInit {
    @ViewChild('editor') editor;
    text: string = "";
  id: string;
  project: Project = new Project();
  testSuite: TestSuite = new TestSuite();
  testCases: FormArray;
  yml = true;
  bsc = false;
  adv = false;

  advance: boolean = false;
  severities: any[] = [{ id: "Critical", value: "Critical" }, { id: "Major", value: "Major" }, { id: "Minor", value: "Minor" }, { id: "Trivial", value: "Trivial" }];
  methods: string[] = ["GET", "POST", "PUT", "DELETE"];//, "OPTIONS", "TRACE", "HEAD", "PATCH"];
  categories: string[] = ["Bug", "Use_Case", "Functional", "Positive", "Negative", "Weak_Password", "Security_UnSecured", "Security_DDOS", "Security_XSS", "Security_SQL_Injection", "UnSecured", "DDOS", "XSS_Injection", "SQL_Injection", "Log_Forging", "RBAC"];
  types: any[] = [{ id: "SUITE", value: "Suite" }, { id: "ABSTRACT", value: "Abstract" }];//,{id:"DATASET",value: "Dataset"},{id:"CONSULTING_SERVICES",value: "Consulting_Services"},{id:"AI_SKILLS",value: "AI_Skills"}];
  testSuite1: any = { assertion: '' };


  context: string = "New";
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  yamlFormGroup: FormGroup;

  constructor(private projectService: ProjectService, private testSuiteService: TestSuiteService,
    private route: ActivatedRoute, private router: Router, private handler: Handler,
    public snackBar: MatSnackBar, private snackbarService: SnackbarService, private _formBuilder: FormBuilder) { }

  ngOnInit() {

    // Auto completion code starts here
      var myKeywords = (function() {
          var words = "FakerName,FakerPassword,FakerCity".split(",")
          var transformationWords = "trim,trimToNull,trimToEmpty,truncate,strip,indexOf,indexOfIgnoreCase,lastIndexOf,left,right,substringBefore,substringAfter,substringBeforeLast,substringAfterLast,substringBetween,removeStart,removeStartIgnoreCase,removeEnd,removeEndIgnoreCase,remove,removeIgnoreCase,removeAll,removeFirst,removePattern,chomp,chop,repeat,rightPad,leftPad,upperCase,lowerCase,capitalize,uncapitalize,reverse".split(",")
          var dataInjection = " @Random,@RandomAlphabetic,@RandomAlphanumeric,@RandomNumeric,@Date,@RandomUUID".split(",")
          var entityKeyWords = "@Request,@StatusCode,@ResponseHeaders,@Response,@NULL,@EMPTY,@ResponseTime,@ResponseSize".split(",")
          var array1 = [];
          for (var i = 0; i < words.length; i++)
              array1.push({
                  name: words[i],
                  value: words[i],
                  meta: "Standard"
              });
          for(var i = 0 ; i< transformationWords.length;i++)
              array1.push({
                  name:transformationWords[i],
                  value:transformationWords[i],
                  meta:"Transformation"
              });
          for(var i = 0; i< dataInjection.length;i++)
              array1.push({
                  name:dataInjection[i],
                  value:dataInjection[i],
                  meta:"Data-Injection"
              })
          for(var i=0;i<entityKeyWords.length;i++)
              array1.push({
                  name:entityKeyWords[i],
                  value:entityKeyWords[i],
                  meta:"Entity-Keyword"
              })
          return array1;
      })();
      var myCompleter = {
          getCompletions: function(editor, session, pos, prefix, callback) {
              callback(null, myKeywords);
          }
      }

      this.editor.setOptions({
          enableLiveAutocompletion: [myCompleter]
      });

      // Auto completion code Ends here




      this.route.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.loadProject(this.id);
      }
    });
    this.thirdFormGroup = this._formBuilder.group({
      nameCtrl: ['', Validators.required],
      // testCases: this._formBuilder.array([this.createItem()]),
      // type: ''
    });

    if(localStorage.getItem('suiteClone') === null){
      this.testSuiteService.getSample().subscribe(results => {
        this.text = results;
      }, error => {
        this.handler.hideLoader();
        this.handler.error(error);
      });

    }
    else{
      this.text = localStorage.getItem('suiteClone');
      localStorage.removeItem('suiteClone');
    }
  
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
  geInfo(obj) {
    console.log("sss");
  }
  createItem(): FormGroup {
    return this._formBuilder.group({
      id: null,
      body: [null, Validators.required],
      inactive: null
    });
  }
  createItem1(obj: TestCase): FormGroup {
    var k = this._formBuilder.group({
      id: obj.id,
      body: [obj.body, Validators.required],
      inactive: obj.inactive
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
      this.testSuite.type = 'SUITE';
      this.testSuite.method = 'POST';
      this.testSuite.category = 'Functional';
      this.testSuite.severity = 'Major';
      this.testSuite.inactive = false;
      this.testSuite.auth = 'Default';
      this.testSuite.project = this.project;

    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }


  saveTestSuite() {
    var groupVal = this.thirdFormGroup.value;
    this.testSuite.testCases = groupVal.testCases;
    this.handler.activateLoader();
    this.snackbarService.openSnackBar("'TestSuite '" + this.testSuite.name + "' creating...", "");
    this.testSuiteService.create(this.testSuite).subscribe(results => {
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
  yaml() {
    this.yml = true;
    this.bsc = false;
    this.adv = false;
  }
  basic() {
    this.bsc = true;
    this.yml = false;
    this.adv = false;
  }
  advanced() {
    this.adv = true;
    this.yml = false;
    this.bsc = false;
  }

  saveTestSuiteYaml() {
    this.handler.activateLoader();
    this.snackbarService.openSnackBar(" creating...", "");
    this.testSuiteService.createFromYaml(this.text, this.project.id).subscribe(results => {
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
