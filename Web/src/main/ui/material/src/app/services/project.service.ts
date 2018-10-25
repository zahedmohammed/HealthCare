import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import 'rxjs/add/operator/toPromise';
import { Project } from '../models/issues-project.model';
import { ProjectSync } from '../models/project-sync.model';
import { AutoCodeConfig } from '../models/project-autocode-config.model';
import { Env } from '../models/project-env.model';
import { HttpXsrfCookieExtractor } from '@angular/common/http/src/xsrf';


@Injectable()
export class ProjectService {
  private serviceUrl = '/api/v1/projects';
  private envServiceUrl = '/api/v1/envs';
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

  saveNewProjectAutoCodeConfig(autoCodeConfig: AutoCodeConfig, projectId: string) {
    return this.http.post(this.serviceUrl + "/" +  projectId + "/new/autocodeconfig", autoCodeConfig);
  }

  getAutoCodeConfig(projectId: string) {
    return this.http.get(this.serviceUrl + "/" + projectId + "/autocodeconfig");
  }

  saveEnv(env: Env, projectId: string) {
    return this.http.post(this.envServiceUrl, env);
  }
  updateEnv(env: Env, projectId: string) {
    return this.http.put(this.serviceUrl + "/" +  projectId + "/env/" + env.id, env);
  }
  saveEnvs(envs: Env[], projectId: string) {
    return this.http.post(this.serviceUrl + "/" +  projectId + "/envs", envs);
  }

  getEnvsByProjectId(projectId: string) {
    return this.http.get(this.envServiceUrl + "/project-id/" +  projectId);
  }

  deleteEnv(env: Env) {
    return this.http.delete(this.envServiceUrl + "/" + env.id);
  }

 getEnvById(envid:string) {
    return this.http.get(this.envServiceUrl + "/" + envid);
  }

  searchProject(keyword: string, page, pageSize){
    let params =new HttpParams();
    params = params.append('keyword', keyword);
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl + '/search', {params});

  }

  getAutoSuggestions(id: string) {
    return this.http.get(this.serviceUrl + "/" + id + "/auto-suggestions");
  }

  skipAutoSuggestion(jobId: string, suiteName: string, tcNumber: string) {
    return this.http.get(this.serviceUrl + "/" + jobId + "/auto-suggestions/skip/"+suiteName+"/"+tcNumber);
  }


}
