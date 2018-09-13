import { Component, Injectable, Directive } from '@angular/core';
import { VERSION, MatDialog, MatDialogRef } from '@angular/material';
import { ResponseDialogComponent } from '../response-dialog/response-dialog.component';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';
import { APPCONFIG } from '../../../config';

import { filter } from 'rxjs/operators';

@Injectable()
export class Handler {

  AppConfig;

  constructor(private dialog: MatDialog) {
    this.AppConfig = APPCONFIG;
  }

  ngOnInit() {

  }

  activateLoader() {
    this.AppConfig['isLoadingResults'] = true;
  }

  hideLoader() {
    this.AppConfig['isLoadingResults'] = false;
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
