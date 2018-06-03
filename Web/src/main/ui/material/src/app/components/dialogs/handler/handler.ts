import { Component, Injectable, Directive } from '@angular/core';
import { VERSION, MatDialog, MatDialogRef } from '@angular/material';
import { ResponseDialogComponent } from '../response-dialog/response-dialog.component';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';

import { filter } from 'rxjs/operators';

@Injectable()
export class Handler {

  constructor(private dialog: MatDialog) {}

  activateLoader() {

  }

  hideLoader() {

  }

  error(error) {
    console.error(error);
    this.dialog.open(ErrorDialogComponent, {
      data: error['message']
    });
  }

  handle(response) {
    if (!response['errors']) {
       return false;
    }
    console.error(response);
    this.dialog.open(ResponseDialogComponent, {
      data: response['messages']
    });
    return true;
  }

}
