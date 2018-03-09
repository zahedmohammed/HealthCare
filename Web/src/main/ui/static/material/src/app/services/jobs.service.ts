import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class JobsService {
  private serviceUrl = '/api/v1/jobs';
  constructor(private http: HttpClient) {
  }

  /**
   * Get the jobs in observable from endpoint
   */
  getJobs(id: string) {
    return this.http.get(this.serviceUrl + "/project-id/" + id);
  }

  getCountJobs() {
    return this.http.get(this.serviceUrl + "/count" );
  }

  getCountTests() {
    return this.http.get(this.serviceUrl + "/count-tests" );
  }

}
