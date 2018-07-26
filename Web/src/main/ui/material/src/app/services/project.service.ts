import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import 'rxjs/add/operator/toPromise';
import { Project } from '../models/project.model';
import { ProjectSync } from '../models/project-sync.model';
import { AutoCodeConfig } from '../models/project-autocode-config.model';


@Injectable()
export class ProjectService {
  private serviceUrl = '/api/v1/projects';
  private autocodeURL = './assets/auto-code.json';
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

  getFiles(id: string, page, pageSize){
      let params = new HttpParams();
      params = params.append('page', page);
      params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl + "/" + id + "/files", {params} )
  }

  getFilesDetails(id: string, fileId: string) {

      return this.http.get(this.serviceUrl + "/" + id + "/files" + "/" + fileId);
  }

  projectSync(projectsync: ProjectSync) {
    return this.http.post(this.serviceUrl + "/" +  projectsync['id'] + "/project-sync", projectsync);
  }

  saveAutoCodeConfig(autoCodeConfig: AutoCodeConfig, projectId: string) {
    return this.http.post(this.serviceUrl + "/" +  projectId + "/autocodeconfig", autoCodeConfig);
  }

 getAutoCodeConfigByProjectId(projectId: string) {
    return this.http.get(this.serviceUrl + "/" + projectId + "/autocodeconfig");
  }


  //Auto-code for testing from static json file

  getAutoCodeConfig(){
    return this.http.get(this.autocodeURL);
  }

}
