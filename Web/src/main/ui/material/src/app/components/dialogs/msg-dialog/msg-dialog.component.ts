import {AfterViewInit, Component, Inject, OnInit, ViewChild} from "@angular/core";
import { MatDialogRef,MAT_DIALOG_DATA } from '@angular/material';
import 'brace';
import 'brace/mode/yaml';
import 'brace/theme/github';
import 'brace/ext/language_tools';
import { AceEditorComponent } from 'ng2-ace-editor';

@Component({
    selector: 'app-msg-dialog',
    templateUrl: './msg-dialog.component.html',
    styleUrls: ['./msg-dialog.component.scss']
})
export class MsgDialogComponent implements AfterViewInit {

    @ViewChild('editor') editor:AceEditorComponent

    constructor(@Inject(MAT_DIALOG_DATA) public data: any,
                public dialogRef: MatDialogRef<MsgDialogComponent>) {

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
