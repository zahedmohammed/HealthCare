import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs';


import { Jobs } from '../models/jobs.model';
import { TestSuite } from '../models/test-suite.model';


@Injectable()
export class TestSuiteService {

private serviceUrl = '/api/v1/test-suites'
private testSuiteSample = './assets/test-suite-sample.yaml';

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
    return this.http.post(this.serviceUrl +  "/ui", obj);
  }

   createFromYaml(yaml: string, projectId: string) {
    return this.http.post(this.serviceUrl + "/" + projectId +"/ui", yaml);
  }

 update(obj: TestSuite) {
    return this.http.put(this.serviceUrl, obj);
  }

  delete(id) {
    return this.http.delete(this.serviceUrl + "/" + id);
  }

 searchTestSuite(projectId:string, category:string, keyword:string, page, pageSize) {
    let params = new HttpParams();
    params = params.append('category', category);
    params = params.append('keyword', keyword);
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl + "/" + projectId + "/test-suite/search", {params});
  }

  getSample() {
    return this.http.get(this.testSuiteSample, { responseType: 'text' });
  }

  getApiCoverage(projectId: string) {
    return this.http.get(this.serviceUrl+"/project-id/"+projectId+"/coverage");
  }


}
