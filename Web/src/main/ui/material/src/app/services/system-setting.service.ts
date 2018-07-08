import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class SystemSettingService {
private serviceUrl = '/api/v1/system-settings';
constructor(private http: HttpClient) {
  }
}
