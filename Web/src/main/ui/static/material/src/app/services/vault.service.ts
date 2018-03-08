import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';
import { Vault } from '../models/vault.model';

@Injectable()
export class VaultService {
  private serviceUrl = '/api/v1/vault'
  constructor(private http: HttpClient) {
  }

  /**
   * Get observable from endpoint
   */
  get() {
      return this.http.get(this.serviceUrl);
  }

  getById(id: string) {
    return this.http.get(this.serviceUrl + "/" + id);
  }

  create(obj: Vault) {
    return this.http.post(this.serviceUrl, obj);
  }

  update(obj: Vault) {
    return this.http.put(this.serviceUrl + "/" + obj['id'], obj);
  }

  delete(obj: Vault) {
    return this.http.delete(this.serviceUrl + "/" + obj['id']);
  }

}

