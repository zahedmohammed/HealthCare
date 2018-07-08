import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import 'rxjs/add/operator/toPromise';
import { IssueTracker } from '../models/issue-tracker.model';

@Injectable()
export class IssueTrackerService {
  private serviceUrl = '/api/v1/issue-trackers'
  constructor(private http: HttpClient) {
  }

  /**
   * Get observable from endpoint
   */
  get(type: string, page, pageSize) {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl + "/skill-type/" + type, {params});
  }

  getById(id: string) {
    return this.http.get(this.serviceUrl + "/" + id);
  }

  createITBot(obj: IssueTracker) {
    return this.http.post(this.serviceUrl + "/issue-tracker-bot", obj);
  }

  update(obj: IssueTracker) {
    return this.http.put(this.serviceUrl + "/" + obj['id'], obj);
  }

  deleteITBot(obj: IssueTracker) {
    return this.http.delete(this.serviceUrl + "/issue-tracker-bot/" + obj['id']);
  }

}

