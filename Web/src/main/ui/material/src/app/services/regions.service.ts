import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';
import { Region } from '../models/regions.model';

@Injectable()
export class RegionsService {
  private serviceUrl = '/api/v1/bot-clusters';
  constructor(private http: HttpClient) {
  }

  /**
   * Get the jobs in observable from endpoint
   */
  get(page, pageSize) {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl, {params});
  }

  getEntitled(page, pageSize) {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl + "/entitled", {params});
  }

  getById(id: string) {
    return this.http.get(this.serviceUrl + "/" + id);
  }

  create(obj: Region) {
    return this.http.post(this.serviceUrl, obj);
  }

  update(obj: Region) {
    return this.http.put(this.serviceUrl + "/" + obj['id'], obj);
  }

  delete(obj: Region) {
    return this.http.delete(this.serviceUrl + "/" + obj['id']);
  }

  ping(obj: Region) {
    return this.http.get(this.serviceUrl + "/" + obj['id'] + "/ping");
  }

  getSuperBotNetwork(page, pageSize) {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl +  "/superbotnetwork", {params});
  }

}



