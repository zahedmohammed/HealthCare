import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import 'rxjs/add/operator/toPromise';


@Injectable()
export class DashboardService {

  private serviceUrl = '/api/v1/dashboard';
  constructor(private http: HttpClient) { }

  getStat(name: string) {
    return this.http.get(this.serviceUrl + "/" + name);
  }

  getStatByMonth(name: string,fromDate:string,toDate:string) {
    return this.http.get(this.serviceUrl + "/" + name+"?fromDate="+fromDate+"&toDate="+toDate);
  }
  botSavings(botid: string) {
    return this.http.get(this.serviceUrl + "/bots/" + botid + "/savings");

  }

  projectSavings(projectid: string) {
    return this.http.get(this.serviceUrl + "/projects/" + projectid + "/savings");

  }

  issueTrackerSavings(trackerid: string){
    return this.http.get(this.serviceUrl + "/issuetracker/" + trackerid + "/issuetracker-savings");

  }
}
