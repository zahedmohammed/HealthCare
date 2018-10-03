import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef, MatSnackBar} from "@angular/material";
import {JobsService} from "../../../services/jobs.service";
import {SnackbarService} from "../../../services/snackbar.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Handler} from "../handler/handler";
import {ProjectService} from "../../../services/project.service";
import {RunService} from "../../../services/run.service";
import {TestSuiteService} from "../../../services/test-suite.service";
import {TestCase, TestSuite} from "../../../models/test-suite.model";
import {Project} from "../../../models/project.model";
import {MsgDialogComponent} from "../msg-dialog/msg-dialog.component";
import {TestsuiteRunComponent} from "../testsuite-run/testsuite-run.component";

@Component({
  selector: 'app-testsuit-edit-dialog',
  templateUrl: './testsuit-edit-dialog.component.html',
  styleUrls: ['./testsuit-edit-dialog.component.scss'],
  providers: [JobsService, RunService, ProjectService, SnackbarService]

})
export class TestsuitEditDialogComponent implements OnInit {
    @ViewChild('editor') editor;
    firstFormGroup: FormGroup;
    testSuite: TestSuite = new TestSuite();
    project: Project = new Project();
    projectId:string;
    testSuiteId:string;
    testCases: FormArray;
    currentSuiteName:string;

    constructor(private testSuiteService: TestSuiteService, private runService: RunService,private dialog: MatDialog,
                @Inject(MAT_DIALOG_DATA) public data: any,private _formBuilder: FormBuilder,public dialogRef: MatDialogRef<MsgDialogComponent>,
                private projectService: ProjectService, private handler: Handler, private snackbarService: SnackbarService) {
        this.projectId = data[0];
        this.testSuiteId = data[1];
        this.currentSuiteName = data[2];
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


        this.loadTestSuite(this.testSuiteId);
        this.firstFormGroup = this._formBuilder.group({
            nameCtrl: ['', Validators.required],
            testCases: this._formBuilder.array([])
        });
  }

    saveTestSuite() {
        var groupVal = this.firstFormGroup.value;
        this.testSuite.testCases = groupVal.testCases;
        this.handler.activateLoader();
        this.snackbarService.openSnackBar("'TestSuite '" + this.testSuite.name + "' updating...", "");
        this.testSuiteService.update(this.testSuite).subscribe(results => {
            this.handler.hideLoader();
            if (this.handler.handle(results)) {
                return;
            }
            this.snackbarService.openSnackBar("'TestSuite '" + this.testSuite.name + "' updated successfully", "");
            this.dialogRef.close();

        }, error => {
            this.handler.hideLoader();
            this.handler.error(error);
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
            console.log(this.firstFormGroup);
        }, error => {
            this.handler.hideLoader();
            this.handler.error(error);
        });
    }

    addItem1(obj): void {
        this.testCases = this.firstFormGroup.get('testCases') as FormArray;
        this.testCases.push(this.createItem1(obj));
    }

    createItem1(obj: TestCase): FormGroup {
        var k = this._formBuilder.group({
            id: obj.id,
            body: [obj.body, Validators.required]
        });
        return k;
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
