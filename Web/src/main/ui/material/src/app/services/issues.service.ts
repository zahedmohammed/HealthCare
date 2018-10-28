import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Issue } from '../models/issue.model';

@Injectable()
export class IssuesService {
  private serviceUrl = '/api/v1/issues';
  constructor(private http: HttpClient) { }

  create(issue: Issue) {
    return this.http.post(this.serviceUrl, issue);
  }

  update(issue: Issue) {
    return this.http.put(this.serviceUrl, issue);
  }

  delete(issue: Issue) {
    return this.http.delete(this.serviceUrl + "/" + issue['id']);
  }
  get() {
    return this.http.get(this.serviceUrl + "/issue" );
  }
}


