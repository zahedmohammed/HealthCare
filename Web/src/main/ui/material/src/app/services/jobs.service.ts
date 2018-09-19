import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import 'rxjs/add/operator/toPromise';
import { Jobs, Noti, Cron } from '../models/jobs.model';

@Injectable()
export class JobsService {
  private serviceUrl = '/api/v1/jobs';
  constructor(private http: HttpClient) {
  }

  get(page, pageSize) {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl, {params});
  }

  getById(id: string) {
    return this.http.get(this.serviceUrl + "/" + id);
  }

  /**
   * Get the jobs in observable from endpoint
   */
  getJobs(id: string, page, pageSize) {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl + "/project-id/" + id, {params});
  }

  getCountJobs() {
    return this.http.get(this.serviceUrl + "/count" );
  }

  getCountTests() {
    return this.http.get(this.serviceUrl + "/count-tests" );
  }

  create(obj: Jobs, categories: string[]) {
    obj.categories = categories.join(,);
    return this.http.post(this.serviceUrl, obj);
  }

  update(obj: Jobs) {
    return this.http.put(this.serviceUrl, obj);
  }

  delete(obj: Jobs) {
    return this.http.delete(this.serviceUrl + "/" + obj['id']);
  }

}
