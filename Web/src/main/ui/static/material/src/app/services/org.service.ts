import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';
import { Org, OrgUser, Member } from '../models/org.model';


@Injectable()
export class OrgService {
  private serviceUrl = '/api/v1/orgs';
  constructor(private http: HttpClient) {
  }

  getByUser() {
    return this.http.get(this.serviceUrl + "/by-user");
  }

  /**
   * Get the jobs in observable from endpoint
   */
  get() {
      return this.http.get(this.serviceUrl);
  }

  getById(id: string) {
    return this.http.get(this.serviceUrl + "/" + id);
  }

  create(org: Org) {
    return this.http.post(this.serviceUrl, org);
  }

  update(org: Org) {
    return this.http.put(this.serviceUrl + "/" + org['id'], org);
  }

  delete(org: Org) {
    return this.http.delete(this.serviceUrl + "/" + org['id']);
  }

  getOrgUsersById(id: string) {
    return this.http.get(this.serviceUrl + "/" + id + "/users");
  }

  getOrgUsers(id: string) {
    return this.http.get(this.serviceUrl + "/org-user/" + id);
  }

  createOrgUser(orgUser: OrgUser) {
    return this.http.post(this.serviceUrl + "/org-user", orgUser);
  }

  updateOrgUser(orgUser: OrgUser) {
    return this.http.put(this.serviceUrl + "/org-user/" + orgUser['id'], orgUser);
  }

  addMember(member: Member) {
    return this.http.post(this.serviceUrl + "/add-member", member);
  }

}