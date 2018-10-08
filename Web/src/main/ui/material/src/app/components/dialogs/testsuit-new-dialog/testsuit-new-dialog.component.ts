import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material";
import {MsgDialogComponent} from "../msg-dialog/msg-dialog.component";
import {SnackbarService} from "../../../services/snackbar.service";
import {Handler} from "../handler/handler";
import {ActivatedRoute, Router} from "@angular/router";
import {ProjectService} from "../../../services/project.service";
import {RunService} from "../../../services/run.service";
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {TestSuiteService} from "../../../services/test-suite.service";
import {Project} from "../../../models/project.model";
import {TestSuite} from "../../../models/test-suite.model";
import {JobsService} from "../../../services/jobs.service";

@Component({
  selector: 'app-testsuit-new-dialog',
  templateUrl: './testsuit-new-dialog.component.html',
  styleUrls: ['./testsuit-new-dialog.component.scss'],
  providers: [JobsService, RunService, ProjectService, SnackbarService]

})
export class TestsuitNewDialogComponent implements OnInit {

    @ViewChild('editor') editor;
    firstFormGroup: FormGroup;
    testSuite: TestSuite = new TestSuite();
    project: Project = new Project();
    projectId:string;
    testSuiteId:string;
    testCases: FormArray;
    testSuitText: string = "";
    // testSuitText: TestSuite = new TestSuite();


    constructor(private testSuiteService: TestSuiteService, private runService: RunService,private dialog: MatDialog,
                @Inject(MAT_DIALOG_DATA) public data: any,private _formBuilder: FormBuilder,public dialogRef: MatDialogRef<TestsuitNewDialogComponent>,
                private projectService: ProjectService, private handler: Handler, private snackbarService: SnackbarService,
                private router: Router,private route: ActivatedRoute) {
        this.projectId = data[0];
        this.testSuitText = data[1];
    }

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

      if (this.projectId) {
          this.loadProject(this.projectId);
      }

      if(!this.testSuitText){
          this.testSuiteService.getSample().subscribe(results => {
              this.testSuitText = results;
          }, error => {
              this.handler.hideLoader();
              this.handler.error(error);
          });

      }
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

    saveTestSuiteYaml() {

        var array2:any = []

        var numberoflines = this.editor.getEditor().session.getLength()

        var words = this.editor.getEditor().session.getLines(0,numberoflines)

        this.testSuitText = ''
        for(var i=0;i<words.length;i++) {
          if(words[i] == 'autoGenerated: true'){
              words[i] = 'autoGenerated: false';
          }
          if(i == words.length-1)
            this.testSuitText = this.testSuitText + words[i];
          else
            this.testSuitText = this.testSuitText + words[i] + '\n';
        }
        this.handler.activateLoader();
        this.snackbarService.openSnackBar(" creating...", "");
        this.testSuiteService.createFromYaml(this.testSuitText, this.project.id).subscribe(results => {
            this.handler.hideLoader();
            if (this.handler.handle(results)) {
                return;
            }
            this.dialogRef.close();
            this.snackbarService.openSnackBar("'New TestSuite created successfully", "");
            this.router.navigate(['/app/projects', this.projectId, 'test-suites']);

        }, error => {
            this.handler.hideLoader();
            this.handler.error(error);
        });
    }


}
