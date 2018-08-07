import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import 'rxjs/add/operator/toPromise';
import { Jobs } from '../models/jobs.model';
import { TestSuite } from '../models/test-suite.model';


@Injectable()
export class TestSuiteService {

private serviceUrl = '/api/v1/test-suites'
constructor(private http: HttpClient) {
  }
  /**
   * Get observable from endpoint
   */
  getById(id,page, pageSize) {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl+"/project-id/"+id, {params});
  }
 getByTestSuiteId(id) {
    let params = new HttpParams();
  //  params = params.append('page', page);
  //  params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl+"/"+id, {params});
  }
 get(page, pageSize) {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl, {params});
  }
  search(keyword: string) {
    return this.http.get(this.serviceUrl + "/search/" + keyword);
  }
  create(obj: TestSuite) {
    return this.http.post(this.serviceUrl + "/ui", obj);
  }
 update(obj: TestSuite) {
    return this.http.put(this.serviceUrl, obj);
  }

  delete(obj: TestSuite) {
    return this.http.delete(this.serviceUrl + "/" + obj['id']);
  }
}
