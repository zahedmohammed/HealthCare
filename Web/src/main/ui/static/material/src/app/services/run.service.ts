import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';

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

  getDetails(runId:string) {
    return this.http.get(this.serviceUrl + "/" + runId);
  }

  getTestSuiteResponses(runId:string) {
    return this.http.get(this.serviceUrl + "/" + runId + "/test-suite-responses");
  }

  getSummary(runId:string) {
    return this.http.get(this.serviceUrl + "/" + runId + "/test-suite-summary");
  }

  getTestSuiteResponseByName(id:string, name:string) {
    return this.http.get(this.serviceUrl + "/" + id + "/test-suite-response/" + name);
  }

 }
