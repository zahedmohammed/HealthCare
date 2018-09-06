import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import 'rxjs/add/operator/toPromise';
import { Categories } from '../models/project-autocode-config.model';
import { TestSuite, TestCase } from '../models/test-suite.model';

@Injectable()
export class RunService {
private serviceUrl = '/api/v1/runs';
constructor(private http: HttpClient) {
  }

  getById(id: string) {
    return this.http.get(this.serviceUrl + "/" + id);
  }

  /**
   * Get the jobs in observable from endpoint
   */
  get(jobId:string, page, pageSize) {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl + "/job/" + jobId, {params});
  }

  run(jobId:string) {
    //return this.http.post(
    console.log("JobId: " + jobId);
    return this.http.post(this.serviceUrl + "/job/" + jobId, null);
  }

  advRun(jobId: string, region: string, tags: string, suites: string,categories: string[]) {
   var cat = "";
   if(categories){
    for (let categorie of categories) {
      console.log(categorie);
       cat = cat + categorie+", " ; 

  }
}

    let params = new HttpParams();
    params = params.append('region', region);
    params = params.append('tags', tags);
    params = params.append('suites', suites);
    params = params.append('categories',cat);
    console.log("JobId: " + jobId + " Region: " + region );
    return this.http.post(this.serviceUrl + "/job/" + jobId, null, {params});
  }


  advTestSuiteRun(region: string, env: string, testSuite: TestSuite) {
    let params = new HttpParams();
    params = params.append('region', region);
    params = params.append('env', env);
    return this.http.post(this.serviceUrl + "/testsuite", testSuite, {params});
  }

  getDetails(runId:string) {
    return this.http.get(this.serviceUrl + "/" + runId);
  }

  getTestSuiteResponses(runId:string) {
    return this.http.get(this.serviceUrl + "/" + runId + "/test-suite-responses");
  }

  getSummary(runId:string, page, pageSize) {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl + "/" + runId + "/test-suite-summary", {params});
  }

  search(runId:string, category:string, keyword:string, page, pageSize) {
    let params = new HttpParams();
    params = params.append('category', category);
    params = params.append('keyword', keyword);
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl + "/" + runId + "/test-suite-summary/search", {params});
  }

  getTestSuiteResponseByName(id:string, name:string) {
    return this.http.get(this.serviceUrl + "/" + id + "/test-suite-response/" + name);
  }

  getTestSuiteResponseHistoryByName(jobId:string, name:string) {
    return this.http.get(this.serviceUrl + "/" + jobId + "/testSuite/test-suite-responses/" + name);
  }

 }
