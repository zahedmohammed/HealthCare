import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';


@Injectable()
export class OrgService {
  private serviceUrl = '/api/v1/orgs';
  constructor(private http: HttpClient) {
  }

  get() {
    return this.http.get(this.serviceUrl);
  }

}
