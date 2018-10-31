import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs';


import { Skill } from '../models/skill.model';

@Injectable()
export class SkillService {
private serviceUrl = '/api/v1/skills'
  constructor(private http: HttpClient) {
  }

  /**
   * Get observable from endpoint
   */
  get() {
      return this.http.get(this.serviceUrl);
  }

  getByType(type: string) {
    return this.http.get(this.serviceUrl + "/type/" + type);
  }

  getById(id: string) {
    return this.http.get(this.serviceUrl + "/" + id);
  }

  create(obj: Skill) {
    return this.http.post(this.serviceUrl, obj);
  }

  update(obj: Skill) {
    return this.http.put(this.serviceUrl + "/" + obj['id'], obj);
  }

  delete(obj: Skill) {
    return this.http.delete(this.serviceUrl + "/" + obj['id']);
  }

}
