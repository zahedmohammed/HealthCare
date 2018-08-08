import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import 'rxjs/add/operator/toPromise';
import { Org, OrgUser, Member } from '../models/org.model';


@Injectable()
export class OrgService {
  private serviceUrl = '/api/v1/orgs';
  constructor(private http: HttpClient) {
  }
public messages:any;
  getByUser() {
    return this.http.get(this.serviceUrl + "/by-user");
  }

  /**
   * Get the jobs in observable from endpoint
   */
  get(page, pageSize) {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl, {params});
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

  getOrgUsers(orgId: string, id: string) {
    return this.http.get(this.serviceUrl + "/" + orgId + "/org-user/" + id);
  }

  getLoggedInUser() {
    return this.http.get(this.serviceUrl + "/login-status");
  }

  createOrgUser(orgUser: OrgUser) {
    return this.http.post(this.serviceUrl + "/org-user", orgUser);
  }

  updateOrgUser(orgId: string, id: string, orgUser: OrgUser) {
    return this.http.put(this.serviceUrl + "/" + orgId + "/users/" + id, orgUser);
  }

  addMember(orgId: string, member: Member) {
    return this.http.post(this.serviceUrl + "/" + orgId + "/users/add-member", member);
  }

  resetPassword(orgId: string, userId: string, member: Member) {
    return this.http.post(this.serviceUrl + "/" + orgId + "/users/" + userId + "/reset-password", member);
  }

}
