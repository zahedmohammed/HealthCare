import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';
import { Project } from '../models/project.model';

@Injectable()
export class ProjectService {
  private serviceUrl = '/api/v1/projects'
  constructor(private http: HttpClient) {
  }

  /**
   * Get the jobs in observable from endpoint
   */
  getProjects(page, pageSize) {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl, {params});
  }

  getById(id: string) {
    return this.http.get(this.serviceUrl + "/" + id);
  }

  create(project: Project) {
    return this.http.post(this.serviceUrl, project);
  }

  update(project: Project) {
    return this.http.put(this.serviceUrl + "/" + project['id'], project);
  }

  delete(project: Project) {
    return this.http.delete(this.serviceUrl + "/" + project['id']);
  }

  getCount() {
    return this.http.get(this.serviceUrl + "/count" );
  }

}