import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

import { environment } from '@environments/environment';
import { Transfer } from '@app/_models/transfer';
import { User } from '@app/_models/user';
import { map } from 'rxjs/operators';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AccountService } from './account.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class AuthService implements CanActivate {
    private userSubject: BehaviorSubject<User>;
    public user: Observable<User>;
    public transfer: Observable<Transfer>;

    
    constructor(
      private router: Router,
      private accountService: AccountService,
      private http: HttpClient) { 
      this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('user')));
      this.user = this.userSubject.asObservable();
    }

    public get userValue(): User {
      return this.userSubject.value;
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const user = this.accountService.userValue;
    if (user) {
        // authorised so return true
        return true;
    }

    // not logged in so redirect to login page with the return url
    this.router.navigate(['/account/login'], { queryParams: { returnUrl: state.url }});
    return false;
}

    login(credentials): Observable<any> {
      console.log(credentials);
      console.log(credentials.password);
        return this.http.post(`${environment.apiUrl}/auth/signin`, {
          email: credentials.email,
          password: credentials.password
        }, httpOptions);
    }

  //   login(email: string, password: string) {
  //     return this.http.post<any>(`${environment.apiUrl}/auth/signin`, { email, password }, { withCredentials: true })
  //         .pipe(map(user => {
  //             this.userSubject.next(user);
              
  //             return user;
  //         }));
  // }

    register(user): Observable<any> {
      console.log(user)
      return this.http.post(`${environment.apiUrl}/auth/signup`, {
        firstName: user.firstName,
        lastName: user.lastName,
        email: user.email,
        password: user.password
    }, httpOptions);
  }
}
