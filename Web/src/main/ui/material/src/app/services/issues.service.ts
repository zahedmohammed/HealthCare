import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Issue } from '../models/issue.model';

@Injectable()
export class IssuesService {
  private serviceUrl = '/api/v1/issues';
  constructor(private http: HttpClient) { }

  // get(page, pageSize) {
  //   let params = new HttpParams();
  //   params = params.append('page', page);
  //   params = params.append('pageSize', pageSize);
  //   return this.http.get(this.serviceUrl, {params});
  // }

  getById(id: string) {
    return this.http.get(this.serviceUrl + "/" + id);
  }

  /**
   * Get the jobs in observable from endpoint
   */
  getIssue(id: string) {
    let params = new HttpParams();
    params = params.append('page', '1');
    params = params.append('pageSize', '10');
    return this.http.get(this.serviceUrl + "/project/" + id, { params });
  }

  getCountIssue() {
    return this.http.get(this.serviceUrl + "/count");
  }

  create(issue: Issue) {
    return this.http.post(this.serviceUrl, issue);
  }

  update(issue: Issue) {
    return this.http.put(this.serviceUrl, issue);
  }

  delete(issue: Issue) {
    return this.http.delete(this.serviceUrl + "/project" + issue['id']);
  }
  get() {
    return this.http.get(this.serviceUrl + "/issue");
  }
}


