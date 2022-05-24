import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '@app/_models';
import { BehaviorSubject, Observable } from 'rxjs';
const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  private userSubject: BehaviorSubject<User>;
    public user: Observable<User>;
  
  constructor(private router: Router) {
    this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('user')));
    this.user = this.userSubject.asObservable();
   }
  
   public get userValue(): User {
    return this.userSubject.value;
}

  signOut() {
    localStorage.removeItem('user');
    this.userSubject.next(null);
    this.router.navigate(['/account/login']);
  }
  public saveToken(token: string) {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.setItem(TOKEN_KEY, token);
  }
  public getToken(): string {
    return localStorage.getItem(TOKEN_KEY);
  }
  public saveUser(user) {
    localStorage.removeItem(USER_KEY);
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  }
  public getUser() {
    return JSON.parse(localStorage.getItem(USER_KEY));
 }

  // signOut() {
  //   window.sessionStorage.clear();
  // }
  // public saveToken(token: string) {
  //   window.sessionStorage.removeItem(TOKEN_KEY);
  //   window.sessionStorage.setItem(TOKEN_KEY, token);
  // }
  // public getToken(): string {
  //   return sessionStorage.getItem(TOKEN_KEY);
  // }
  // public saveUser(user) {
  //   window.sessionStorage.removeItem(USER_KEY);
  //   window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  // }
  // public getUser() {
  //   return JSON.parse(sessionStorage.getItem(USER_KEY));
  // }
}