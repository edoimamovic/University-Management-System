import { Injectable } from '@angular/core';
import { JwtHelper } from 'angular2-jwt';
import { Router } from '@angular/router';
import {HttpClient } from '@angular/common/http';
import { Subject }    from 'rxjs/Subject';
import { Observable }    from 'rxjs/Observable';

@Injectable()
export class AuthService {
  constructor(public jwtHelper: JwtHelper, public router: Router, public httpClient: HttpClient) { }

  public username = new Observable<string>();
  
  public getUsername() : string {
    return localStorage.getItem('user');    
  }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    return !!token && !this.jwtHelper.isTokenExpired(token);
  }

  public getRole(): string {
    const token = localStorage.getItem('token');
    if (!!token || !this.jwtHelper.isTokenExpired(token)){
      return localStorage.getItem('role');
    }
    else {
      return undefined;
    }
  }

  public signIn(username, password, successCallback, errorCallback): void {
    var that = this;
    this.httpClient.post('http://localhost:3000/api/users/authenticate', {username: username, password: password})
    .subscribe(successCallback, errorCallback);
  }

  public signOut() : void {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('role');
    this.router.navigate(['']);
  }
}
