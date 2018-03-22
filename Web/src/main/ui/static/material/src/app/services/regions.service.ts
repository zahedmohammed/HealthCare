import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';
import { Region } from '../models/regions.model';

@Injectable()
export class RegionsService {
  private serviceUrl = '/api/v1/clusters';
  constructor(private http: HttpClient) {
  }

  /**
   * Get the jobs in observable from endpoint
   */
  get() {
      return this.http.get(this.serviceUrl);
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



}



