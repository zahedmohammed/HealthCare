import {AfterViewInit, Component, Inject, OnInit, ViewChild} from "@angular/core";
import { MatDialogRef,MAT_DIALOG_DATA } from '@angular/material';
import 'brace';
import 'brace/mode/yaml';
import 'brace/theme/github';
import 'brace/ext/language_tools';
import { AceEditorComponent } from 'ng2-ace-editor';
import {Router} from "@angular/router";
import {RunService} from "../../../services/run.service";
import {Handler} from "../handler/handler";
import {ProjectService} from "../../../services/project.service";
import {JobsService} from "../../../services/jobs.service";
import {SnackbarService} from "../../../services/snackbar.service";

@Component({
    selector: 'app-msg-dialog',
    templateUrl: './msg-dialog.component.html',
    styleUrls: ['./msg-dialog.component.scss'],
    providers: [JobsService, RunService, ProjectService, SnackbarService]
})
export class MsgDialogComponent implements AfterViewInit {

    @ViewChild('editor') editor:AceEditorComponent;

    currentSuiteName:string;
    suitNames :any;
    projectId:string;
    jobId:string;
    runId:string;
    logData;
    list;
    nextSuitName:string;
    suitePosition:number;

    constructor(@Inject(MAT_DIALOG_DATA) public data: any,
                public dialogRef: MatDialogRef<MsgDialogComponent>,private router: Router,
                private runService: RunService,private handler: Handler) {

        this.logData = data[0];
        this.currentSuiteName = data[1];
        this.suitNames = data[2]
        this.projectId = data[3]
        this.jobId = data[4]
        this.runId = data[5]


    }


    nextSuite(){

        this.suitePosition = this.suitNames.indexOf(this.currentSuiteName);
        this.suitePosition = this.suitePosition + 1;
        if(this.suitePosition < this.suitNames.length){
            this.nextSuitName = this.suitNames[this.suitePosition]
            this.getTestSuiteResponseByName(this.nextSuitName)
        }
        else
        {
            window.alert("Reached end of the page")
        }

    }

    previousSuite(){

        this.suitePosition = this.suitNames.indexOf(this.currentSuiteName);
        this.suitePosition = this.suitePosition - 1;

        if(this.suitePosition >= 0) {
            this.nextSuitName = this.suitNames[this.suitePosition]
            this.getTestSuiteResponseByName(this.nextSuitName)
        }
        else
        {
            window.alert("Reached beginning of the page")
        }

    }


    getTestSuiteResponseByName(suiteName:string)
    {

        this.currentSuiteName = suiteName;

        this.handler.activateLoader();
        this.runService.getTestSuiteResponseByName(this.runId, suiteName).subscribe(results => {
            this.handler.hideLoader();
            if (this.handler.handle(results)) {
                return;
            }
            this.list = results['data'];
            var arrayLength = this.list.length;
            var msg = '';
            for (var i = 0; i < arrayLength; i++) {
                msg += this.list[i].logs;
            }
            this.logData = msg;
        }, error => {
            this.handler.hideLoader();
            this.handler.error(error);
        });

    }


    ngAfterViewInit() {

        var array2:any = []

        var numberoflines = this.editor.getEditor().session.getLength()

        var words = this.editor.getEditor().session.getLines(0,numberoflines)

        for(var i=0;i<words.length;i++) {
            var array1  = words[i].split(" ")
            for(var j=0;j<array1.length;j++)
                if(array1[j] == "ERROR")
                {
                    j = array1.length
                    array2.push({row:i,text:"Error",type:"error"})

                }
        }

        this.editor.getEditor().getSession().setAnnotations(array2)

    }


}
