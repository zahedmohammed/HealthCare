import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
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
  get() {
      return this.http.get(this.serviceUrl);
  }

  getById(id: string) {
    return this.http.get(this.serviceUrl + "/" + id);
  }

  create(obj: Subscription) {
    return this.http.post(this.serviceUrl, obj);
  }

  update(obj: Subscription) {
    return this.http.put(this.serviceUrl + "/" + obj['id'], obj);
  }

  delete(obj: Subscription) {
    return this.http.delete(this.serviceUrl + "/" + obj['id']);
  }

}

