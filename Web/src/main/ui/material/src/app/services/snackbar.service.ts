import { Injectable } from '@angular/core';
import { MatSnackBar }from '@angular/material';

@Injectable()
export class SnackbarService {

  constructor(public snackBar: MatSnackBar) { }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 5000,
      verticalPosition: 'bottom',
      horizontalPosition: 'left'
    });
  }

}
