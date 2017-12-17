import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable()
export class ProfAuthGuardService implements CanActivate  {

  constructor(public auth: AuthService, public router: Router) { }

  canActivate(): boolean {
    if (this.auth.isAuthenticated() && this.auth.getRole() === "profesor"){
      this.router.navigate(['dashboard-prof']);
      return false;
    }
    if (this.auth.isAuthenticated() && this.auth.getRole() === "studentska"){
      this.router.navigate(['dashboard-ssluzba']);
      return false;
    }
    if (this.auth.isAuthenticated() && this.auth.getRole() === "student"){
      this.router.navigate(['dashboard-stud']);
      return false;
    }

    return true;
  }

}
