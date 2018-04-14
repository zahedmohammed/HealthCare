import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';
import { Subscription } from '../models/subscription.model';

@Injectable()
export class SkillSubscriptionService {
  private serviceUrl = '/api/v1/subscriptions'
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

  createITBot(obj: Subscription) {
    return this.http.post(this.serviceUrl + "/issue-tracker-bot", obj);
  }

  update(obj: Subscription) {
    return this.http.put(this.serviceUrl + "/" + obj['id'], obj);
  }

  deleteITBot(obj: Subscription) {
    return this.http.delete(this.serviceUrl + "/issue-tracker-bot/" + obj['id']);
  }

}

