import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs';


import { Tasks } from './../models/tasks.model';

@Injectable()
export class TasksService {
  private serviceUrl = 'api/v1/events/orgevents';
  //private serviceUrl = '/api/v1/events/register';
  constructor(private http: HttpClient) { }

  /**
   * Get observable from endpoint
   */

  get(page, pageSize) {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl, {params});
  }

  getById(id: string){
    return this.http.get(this.serviceUrl + "/" + id)
  }
}
