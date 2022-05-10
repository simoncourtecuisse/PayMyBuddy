import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '@environments/environment';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class AuthService {
    constructor(private http: HttpClient) { }
    login(credentials): Observable<any> {
        return this.http.post(`${environment.apiUrl}/auth/signin`, {
          email: credentials.email,
          password: credentials.password
        }, httpOptions);
    }

    register(user): Observable<any> {
      return this.http.post(`${environment.apiUrl}/auth/signup`, {
        firstName: user.firstName,
        lastName: user.lastName,
        email: user.email,
        password: user.password
    }, httpOptions);
  }
}
