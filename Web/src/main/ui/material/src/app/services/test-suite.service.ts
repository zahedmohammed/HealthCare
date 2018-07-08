import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import 'rxjs/add/operator/toPromise';
import { TestSuite } from '../models/test-suite.model';

@Injectable()
export class TestSuiteService {

private serviceUrl = '/api/v1/test-suites'
constructor(private http: HttpClient) {
  }

  /**
   * Get observable from endpoint
   */
  get(page, pageSize) {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl, {params});
  }

  search(keyword: string) {
    return this.http.get(this.serviceUrl + "/search/" + keyword);
  }

}
