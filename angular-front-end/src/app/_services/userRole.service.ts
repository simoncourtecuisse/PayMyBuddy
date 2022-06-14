import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@environments/environment";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class UserRoleService {
    
    constructor(private http: HttpClient) { }
    
    getPublicContent(): Observable<any> {
        return this.http.get(`${environment.apiUrl}/api/test/all`, { responseType: 'text'});
    }

    getUserBoard(): Observable<any> {
        return this.http.get(`${environment.apiUrl}/api/test/user`, { responseType: 'text'});
    }

    getAdminBoard(): Observable<any> {
        return this.http.get(`${environment.apiUrl}/api/test/admin`, { responseType: 'text'});
    }

}