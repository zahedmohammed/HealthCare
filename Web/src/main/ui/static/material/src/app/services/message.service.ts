import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class MessageService {
  private url = '/api/v1/alerts';
  constructor(private http: HttpClient) { }
  /**
   * Get the jobs in observable from endpoint
   */
  get() {
    return this.http.get(this.url);
  }

  getById(id: string) {
    return this.http.get(this.url + "/" + id);
  }

}