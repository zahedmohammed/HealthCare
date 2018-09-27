import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import 'rxjs/add/operator/toPromise';
import { Account } from '../models/account.model';

@Injectable()
export class AccountService {

  private serviceUrl = '/api/v1/accounts'
  constructor(private http: HttpClient) {
  }

  /**
   * Get observable from endpoint
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

  create(obj: Account) {
    return this.http.post(this.serviceUrl, obj);
  }

  update(obj: Account) {
    return this.http.put(this.serviceUrl + "/" + obj['id'], obj);
  }

  delete(obj: Account) {
    return this.http.delete(this.serviceUrl + "/" + obj['id']);
  }

  getAccountByAccountType(accountType: string) {
    return this.http.get(this.serviceUrl + "/account-type/" + accountType);
  }

  searchAccount(keyword: string, page, pageSize){
    let params = new HttpParams();
    params = params.append('keyword', keyword);
    params = params.append('page', page);
    params = params.append('pageSize', pageSize);
    return this.http.get(this.serviceUrl + "/search", {params});    

  }
}
