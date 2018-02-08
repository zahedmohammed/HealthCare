import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
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
  getProjects() {   
      return this.http.get(this.serviceUrl); 
  }

  getById(id: string) {
    return this.http.get(this.serviceUrl + "/" + id + "/git-account");
  }

  create(project: Project) {
    return this.http.post(this.serviceUrl + "/add", project);
  }
  update(project: Project) {
    return this.http.put(this.serviceUrl + "/" + project['id'] + "/git-account", project);
  }

}
