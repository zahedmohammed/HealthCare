import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { MatTabChangeEvent } from '@angular/material';
import { Routes, RouterModule, Router, ActivatedRoute } from "@angular/router";
import { JobsService } from '../../../services/jobs.service';
import { ProjectService } from '../../../services/project.service';
import { Project } from '../../../models/project.model';
import { Jobs, Noti, Cron } from '../../../models/jobs.model';
import { AccountService } from '../../../services/account.service';
import { TestSuiteService } from '../../../services/test-suite.service';
import { RunService } from '../../../services/run.service';
import { Account } from '../../../models/account.model';
import { Handler } from '../../dialogs/handler/handler';
import { APPCONFIG } from '../../../config';
import { VERSION, MatDialog, MatDialogRef, MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { DeleteDialogComponent } from '../../dialogs/delete-dialog/delete-dialog.component';
import { SnackbarService } from '../../../services/snackbar.service';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
//import { MatStepper } from '@angular/material';
import { TestSuite, TestCase } from '../../../models/test-suite.model';
import 'brace/theme/github';
import 'brace/mode/yaml';
import { TestsuiteRunComponent } from '../../dialogs/testsuite-run/testsuite-run.component';
import 'brace';
import 'brace/ext/language_tools';
// import 'ace-builds/src-min-noconflict/snippets/html';

@Component({
  selector: 'app-test-suite-edit',
  templateUrl: './test-suite-edit.component.html',
  styleUrls: ['./test-suite-edit.component.scss'],
  providers: [ProjectService, SnackbarService, RunService, JobsService]
})
export class TestSuiteEditComponent implements OnInit {
  @ViewChild('editor') editor;
  text: string = "Fxlabs Yaml-editor";
  id: string;
  project: Project = new Project();
  testSuite: TestSuite = new TestSuite();
  yml = true;
  jobs;
  runResult;
  bsc = false;
  adv = false;
  testSuiteId: string;
  testCases: FormArray;
  advance: boolean = false;
  runCategories: string[] = [];
  severities: any[] = [{ id: "Critical", value: "Critical" }, { id: "Major", value: "Major" }, { id: "Minor", value: "Minor" }, { id: "Trivial", value: "Trivial" }];
  methods: string[] = ["GET", "POST", "PUT", "DELETE"];//, "OPTIONS", "TRACE", "HEAD", "PATCH"];
  categories: string[] = ["Bug", "Use_Case", "Functional", "Positive", "Negative", "Weak_Password", "Security_UnSecured", "Security_DDOS", "Security_XSS", "Security_SQL_Injection", "UnSecured", "DDOS", "XSS_Injection", "SQL_Injection", "Log_Forging", "RBAC"];
  types: any[] = [{ id: "SUITE", value: "Suite" }, { id: "ABSTRACT", value: "Abstract" }];//,{id:"DATASET",value: "Dataset"},{id:"CONSULTING_SERVICES",value: "Consulting_Services"},{id:"AI_SKILLS",value: "AI_Skills"}];
  testSuite1: any = { assertion: '' };

  context: string = "New";
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;

  constructor(private projectService: ProjectService, private testSuiteService: TestSuiteService,
    private route: ActivatedRoute, private router: Router, private handler: Handler, private dialog: MatDialog,
    public snackBar: MatSnackBar, private snackbarService: SnackbarService, private _formBuilder: FormBuilder, private jobsService: JobsService, private runService: RunService) { }

  ngOnInit() {

    var myKeywords = (function () {
      var words = "FakerName,FakerPassword,FakerCity".split(",")
      var transformationWords = "trim,trimToNull,trimToEmpty,truncate,strip,indexOf,indexOfIgnoreCase,lastIndexOf,left,right,substringBefore,substringAfter,substringBeforeLast,substringAfterLast,substringBetween,removeStart,removeStartIgnoreCase,removeEnd,removeEndIgnoreCase,remove,removeIgnoreCase,removeAll,removeFirst,removePattern,chomp,chop,repeat,rightPad,leftPad,upperCase,lowerCase,capitalize,uncapitalize,reverse".split(",")
      var dataInjection = "@Random,@RandomAlphabetic,@RandomAlphanumeric,@RandomNumeric,@Date,@RandomUUID".split(",")
      var entityKeyWords = "@Request,@StatusCode,@ResponseHeaders,@Response,@NULL,@EMPTY,@ResponseTime,@ResponseSize".split(",")
      var array1 = [];
      for (var i = 0; i < words.length; i++)
        array1.push({
          name: words[i],
          value: words[i],
          meta: "Faker-Words"
        });
      for (var i = 0; i < transformationWords.length; i++)
        array1.push({
          name: transformationWords[i],
          value: transformationWords[i],
          meta: "Transformation"
        });
      for (var i = 0; i < dataInjection.length; i++)
        array1.push({
          name: dataInjection[i],
          value: dataInjection[i],
          meta: "Data-Injection"
        })
      for (var i = 0; i < entityKeyWords.length; i++)
        array1.push({
          name: entityKeyWords[i],
          value: entityKeyWords[i],
          meta: "Entity-Keyword"
        })
      return array1;
    })();
    var myCompleter = {
      getCompletions: function (editor, session, pos, prefix, callback) {
        callback(null, myKeywords);
      }
    }

    this.editor.setOptions({
      enableLiveAutocompletion: [myCompleter]
    });

    this.route.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.loadProject(this.id);
        this.list(this.id);
      }
      this.testSuiteId = params['testSuiteId'];
      if (params['testSuiteId']) {
        this.loadTestSuite(params['testSuiteId']);
      }
    });
    this.thirdFormGroup = this._formBuilder.group({
      nameCtrl: ['', Validators.required],
      testCases: this._formBuilder.array([])
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
      console.log("testSuite", this.testSuite);
      let k: TestCase = new TestCase();
      if (this.testSuite.testCases.length == 0) this.testSuite.testCases = [k];
      for (var i = 0; i < this.testSuite.testCases.length; i++) this.addItem1(this.testSuite.testCases[i]);
      console.log(this.thirdFormGroup);
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

  geInfo(obj) {
    console.log("sss");
  }

  createItem(): FormGroup {
    return this._formBuilder.group({
      id: null,
      body: [null, Validators.required]
    });
  }

  createItem1(obj: TestCase): FormGroup {
    var k = this._formBuilder.group({
      id: obj.id,
      body: [obj.body, Validators.required]
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
      console.log("project", this.project);

      this.testSuite.project = this.project;
      //this.job['project'] = this.project;
      // this.context = this.project.name + " > Edit";
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  saveTestSuite() {
    var groupVal = this.thirdFormGroup.value;
    this.testSuite.testCases = groupVal.testCases;
    this.handler.activateLoader();
    this.snackbarService.openSnackBar("'TestSuite '" + this.testSuite.name + "' updating...", "");
    this.testSuiteService.update(this.testSuite).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.snackbarService.openSnackBar("'TestSuite '" + this.project.name + "' updated successfully", "");
      this.router.navigate(['/app/projects', this.id, 'test-suites']);
      // this.project = results['data'];

    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }
  saveTestSuiteYaml() {
    this.handler.activateLoader();
    this.snackbarService.openSnackBar(" updating...", "");
    this.testSuiteService.createFromYaml(this.text, this.project.id).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.snackbarService.openSnackBar("'TestSuite '" + this.project.name + "' updated successfully", "");
      this.router.navigate(['/app/projects', this.id, 'test-suites']);
      // this.project = results['data'];

    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  cloneTestSuite() {
    localStorage.setItem('suiteClone', this.testSuite.yaml);
    this.router.navigate(['/app/projects', this.id, 'test-suites', 'new']);
  }

  deleteSuite() {
    var r = confirm("Are you sure you want to delete '" + this.testSuite.name + "'?");
    if (r == true) {
      this.testSuiteService.delete(this.testSuite.id).subscribe(results => {
        if (this.handler.handle(results)) {
          return;
        }
        this.snackbarService.openSnackBar("'TestSuite '" + this.testSuite.name + "' deleted", "");
        this.router.navigate(['/app/projects', this.id, 'test-suites']);
      }, error => {
        this.handler.hideLoader();
        this.handler.error(error);
      });

    }
  }
  length = 0;
  page = 0;
  pageSize = 20;

  list(id: string) {
    this.handler.activateLoader();
    this.jobsService.getJobs(id, this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.jobs = results['data'];
      console.log("job", this.jobs);
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  run() {
    const dialogRef = this.dialog.open(TestsuiteRunComponent, {
      width: '80%',
      //height:'85%',
      data: this.testSuite
    });
    dialogRef.afterClosed().subscribe(result => {
    });
  }
}
