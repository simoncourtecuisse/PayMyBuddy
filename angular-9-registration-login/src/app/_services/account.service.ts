﻿import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '@environments/environment';
import { User } from '@app/_models';
import { Transfer } from '@app/_models/transfer';
import { BankAccount } from '@app/_models/bankAccount';

@Injectable({ providedIn: 'root' })
export class AccountService {
    private userSubject: BehaviorSubject<User>;
    public user: Observable<User>;
    public transfer: Observable<Transfer>;

    constructor(
        private router: Router,
        private http: HttpClient
    ) {
        this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('user')));
        this.user = this.userSubject.asObservable();
    }

    public get userValue(): User {
        return this.userSubject.value;
    }

    login(email, password) {
        return this.http.post<User>(`${environment.apiUrl}/user/authenticate`, { email, password })
            .pipe(map(user => {
                // store user details and jwt token in local storage to keep user logged in between page refreshes
                localStorage.setItem('user', JSON.stringify(user));
                this.userSubject.next(user);
                return user;
            }));
    }

    logout() {
        // remove user from local storage and set current user to null
        localStorage.removeItem('user');
        this.userSubject.next(null);
        this.router.navigate(['/account/login']);
    }

        // create user
    register(user: User) {
        return this.http.post(`${environment.apiUrl}/user`, user);
    }

    // getAllTransactions() {
    //     return this.http.get<User[]>(`${environment.apiUrl}/transactions`);
    // }

    getAll() {
        return this.http.get<User[]>(`${environment.apiUrl}/user/users`);
    }

        // get by email
    getByEmail(email: string) {
        return this.http.get<User>(`${environment.apiUrl}/user`);
    }

    getById(id: string) {
        return this.http.get<User>(`${environment.apiUrl}/user/${id}`);
    }

        // update user
    update(id, params) {
        return this.http.put(`${environment.apiUrl}/user/${id}`, params)
            .pipe(map(x => {
                // update stored user if the logged in user updated their own record
                if (id == this.userValue.id) {
                    // update local storage
                    const user = { ...this.userValue, ...params };
                    localStorage.setItem('user', JSON.stringify(user));

                    // publish updated user to subscribers
                    this.userSubject.next(user);
                }
                return x;
            }));
    }
       
        // delete user
    delete(id: string) {
        return this.http.delete(`${environment.apiUrl}/user/${id}`)
            .pipe(map(x => {
                // auto logout if the logged in user deleted their own record
                if (id == this.userValue.id) {
                    this.logout();
                }
                return x;
            }));
    }

        // all friends of a user
    getAllFriendsByUserId() {
        const userAsString = localStorage.getItem('user');
        const user = JSON.parse(userAsString);
        return this.http.get<User[]>(`${environment.apiUrl}/user/contacts/${user.userId}`);
    }

        // remove a friend
    deleteFriend(id: string) {
        const userAsString = localStorage.getItem('user');
        const user = JSON.parse(userAsString);
        
        return this.http.delete(`${environment.apiUrl}/user/conctacts/${user.userId}/removeFriend/${user.friendUserId}`)
        
    }

        // all transactions of a user
    getAllTransfersByUserId() {
        const userAsString = localStorage.getItem('user');
        const user = JSON.parse(userAsString);
        // return this.http.get<Transfer[]>(`${environment.apiUrl}/transaction/transfers/${id}`);
        return this.http.get<Transfer[]>(`${environment.apiUrl}/transaction/transfers/${user.userId}`);
    }

        // all bankAccount of a user
        getAllBankAccountByUserId() {
            const userAsString = localStorage.getItem('user');
            const user = JSON.parse(userAsString);
            return this.http.get<User[]>(`${environment.apiUrl}/user/profile/${user.userId}`);
        }

        deleteBankAccount(id: string) {
            return this.http.delete(`${environment.apiUrl}/user/profile/bankAccount/${id}`);
        }        
}