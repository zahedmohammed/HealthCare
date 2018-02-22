import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class RunService {
private serviceUrl = '/api/v1/runs';
constructor(private http: HttpClient) {
  }

  /**
   * Get the jobs in observable from endpoint
   */
  get(jobId:string) {
      return this.http.get(this.serviceUrl + "/job/" + jobId);
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
 }
