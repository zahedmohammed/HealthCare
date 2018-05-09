import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';
import { NotificationAccount } from '../models/notification-account.model';

@Injectable()
export class NotificationService {

  private serviceUrl = '/api/v1/notifications'
  constructor(private http: HttpClient) {
  }


  /**
   * Get observable from endpoint
   */
  get(page, pageSize) {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl, {params});
  }

  getById(id: string) {
    return this.http.get(this.serviceUrl + "/" + id);
  }

  create(obj: NotificationAccount) {
    return this.http.post(this.serviceUrl, obj);
  }

  update(obj: NotificationAccount) {
    return this.http.put(this.serviceUrl + "/" + obj['id'], obj);
  }

  delete(obj: NotificationAccount) {
    return this.http.delete(this.serviceUrl + "/" + obj['id']);
  }

}