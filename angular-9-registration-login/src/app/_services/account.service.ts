﻿import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { first, map } from 'rxjs/operators';

import { environment } from '@environments/environment';
import { User } from '@app/_models';
import { Transfer } from '@app/_models/transfer';
import { BankAccount } from '@app/_models/bankAccount';
import { BankTransaction } from '@app/_models/bankTransaction';
import { CreditWalletModel } from '@app/_models/CreditWalletModel';
import { PaymentModel } from '@app/_models/paymentModel';
import { TransactionLabel } from '@app/_models/transactionLabel';


const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
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

    // login(email, password) {
    //     return this.http.post<User>(`${environment.apiUrl}/auth/signin`, { email, password })
    //         .pipe(map(user => {
    //             // store user details and jwt token in local storage to keep user logged in between page refreshes
    //             localStorage.setItem('user', JSON.stringify(user));
    //             this.userSubject.next(user);
    //             return user;
    //         }));
    // }


    login(credentials): Observable<any> {
        console.log(credentials);
          return this.http.post<User>(`${environment.apiUrl}/auth/signin`, {
            email: credentials.value.email,
            password: credentials.value.password
          }, httpOptions)
          .pipe(map(user => {
            // store user details and jwt token in local storage to keep user logged in between page refreshes
            localStorage.setItem('user', JSON.stringify(user));
            this.userSubject.next(user);
            console.log(user);
            return user;
        }));
      }

    //   getUserInfoByEmail(email) {
    //     const userAsString = localStorage.getItem('user');
    //     const user = JSON.parse(userAsString);
    //     console.log(user);
    //     console.log(this.userValue);
    //     return this.http.get<User>(`${environment.apiUrl}/user/userInfo/${user.email}`)
    //     .pipe(map(x => {
    //         // update stored user if the logged in user updated their own record
    //         if (email == this.userValue.email) {
    //             // update local storage
    //             const user = { ...this.userValue };
    //             localStorage.setItem('user', JSON.stringify(user));

    //             // publish updated user to subscribers
    //             this.userSubject.next(user);
    //         }
    //         return x;
    //     }));
    // }

    // getUserInfoByEmail(email) {
    //     var userAsString = localStorage.getItem('user');
    //     var user = JSON.parse(userAsString);
    //     return this.http.get<string>(`${environment.apiUrl}/user/userInfo/${user.email}`)
    //         // .pipe(first())
    //         .subscribe(userInfo => {
    //             //user = userInfo
    //             this.userValue.userId = userInfo;
    //             console.log(this.userValue);
    //             console.log(userInfo);
    //         });
    // }

    getUserInfoByEmail(email) {
        var userAsString = localStorage.getItem('user');
        var user = JSON.parse(userAsString);
        return this.http.get<User>(`${environment.apiUrl}/user/userInfo/${user.email}`)
            .pipe(map(userInfo => {
                //user = userInfo
                this.userValue.userId = userInfo.userId;
                console.log(this.userValue);
                console.log(userInfo);
                localStorage.setItem('user', JSON.stringify(userInfo));
                this.userSubject.next(userInfo);
                console.log(userInfo)
                return userInfo;
            })).subscribe( userInfo =>{
                user = userInfo;
            });
    }

    logout() {
        // remove user from local storage and set current user to null
        localStorage.removeItem('user');
        this.userSubject.next(null);
        this.router.navigate(['/account/login']);
    }

    register(user): Observable<any> {
        return this.http.post(`${environment.apiUrl}/auth/signup`, {
          firstName: user.firstName,
          lastName: user.lastName,
          email: user.email,
          password: user.password
      }, httpOptions);
    }

        // create user
    // register(user: User) {
    //     return this.http.post(`${environment.apiUrl}/auth/signup`, user);
    // }

    // getAllTransactions() {
    //     return this.http.get<User[]>(`${environment.apiUrl}/transactions`);
    // }

    getAllUser() {
        return this.http.get<User[]>(`${environment.apiUrl}/user/users`);
    }

    getAllTransactions() {
        return this.http.get<Transfer[]>(`${environment.apiUrl}/transaction/transfers`);
    }

    getAllBankAccounts() {
        return this.http.get<BankAccount[]>(`${environment.apiUrl}/user/profile/bankAccounts`);
    }

    getAllBankTransactions() {
        return this.http.get<BankTransaction[]>(`${environment.apiUrl}/user/profile/bankTransactions`);
    }

        // get by email
    getByEmail(email: string) {
        return this.http.get<User>(`${environment.apiUrl}/user`);
    }

    getById(id: string) {
        const userAsString = localStorage.getItem('user');
        const user = JSON.parse(userAsString);
        return this.http.get<User>(`${environment.apiUrl}/user/${user.userId}`);
    }

        // update user
    updateUser(userId, params) {
        console.log(userId);
        return this.http.put(`${environment.apiUrl}/user/${userId}`, params)
            .pipe(map(x => {
                // update stored user if the logged in user updated their own record
                if (userId == this.userValue.userId) {
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
    deleteUser(userId: string) {
        return this.http.delete(`${environment.apiUrl}/user/${userId}`)
            .pipe(map(x => {
                // auto logout if the logged in user deleted their own record
                if (userId == this.userValue.userId) {
                    this.logout();
                }
                return x;
            }));
    }

    deleteTransaction(transactionId: string) {
        return this.http.delete(`${environment.apiUrl}/transaction/${transactionId}`)
    }

    

    getWalletBalanceUserById(){
        const userAsString = localStorage.getItem('user');
        const user = JSON.parse(userAsString);
        return this.http.get<string>(`${environment.apiUrl}/user/${user.userId}/walletBalance`)
            .pipe(first())
            .subscribe(walletBalance => {
                this.userValue.walletBalance = walletBalance
            });

    }

        // all friends of a user
    getAllFriendsByUserId() {
        const userAsString = localStorage.getItem('user');
        const user = JSON.parse(userAsString);
        return this.http.get<User[]>(`${environment.apiUrl}/user/contacts/${user.userId}`);
    }

    getAllUsersForContactSearch() {
        const userAsString = localStorage.getItem('user');
        const user = JSON.parse(userAsString);
        return this.http.get<User[]>(`${environment.apiUrl}/user/contacts/${user.userId}/add`);
    }

    // // add a friend
    // addFriend(friendUserId: string) {
    //     const userAsString = localStorage.getItem('user');
    //     const user1 = JSON.parse(userAsString);
    //     //return this.http.put(`${environment.apiUrl}/user/contacts/${user1.userId}/addFriend`, user);
    //     return this.http.put(`${environment.apiUrl}/user/contacts/${user1.userId}/addFriend/${friendUserId}`, {});
    // }

    addFriend(friendUserId: string, params) {
        const userAsString = localStorage.getItem('user');
        const user = JSON.parse(userAsString);
        return this.http.put(`${environment.apiUrl}/user/contacts/${user.userId}/addFriend`, params);
    }

        // remove a friend
    deleteFriend(friendUserId: string) {
        const userAsString = localStorage.getItem('user');
        const user = JSON.parse(userAsString);
        
        return this.http.delete(`${environment.apiUrl}/user/contacts/${user.userId}/removeFriend/${friendUserId}`)
        
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
        return this.http.get<User[]>(`${environment.apiUrl}/user/profile/bankAccounts/${user.userId}`);
    }

    payment(paymentModel: PaymentModel) {
        const transfer = new Transfer();
        transfer.creditor = new User();
        transfer.creditor.userId = paymentModel.creditorId.toString();
        transfer.amount = paymentModel.amount;
        transfer.description = paymentModel.description;

        const userAsString = localStorage.getItem('user');
        const user = JSON.parse(userAsString);
        console.log(transfer);
        return this.http.post(`${environment.apiUrl}/transaction/transfers/${user.userId}/payment`, transfer);
    }

    creditAccount(creditWalletModel: CreditWalletModel) {
        const bankTransaction = new BankTransaction();
        bankTransaction.bankAccount=new BankAccount();
        bankTransaction.bankAccount.bankAccountId=creditWalletModel.bankAccountId.toString();
        bankTransaction.amount=creditWalletModel.amount;
        const userAsString = localStorage.getItem('user');
        const user = JSON.parse(userAsString);

        console.log(bankTransaction);
        return this.http.post(`${environment.apiUrl}/user/profile/${user.userId}/credit`, bankTransaction);
    }

    withdrawAccount(creditWalletModel: CreditWalletModel) {
        const bankTransaction = new BankTransaction();
        bankTransaction.bankAccount = new BankAccount();
        bankTransaction.bankAccount.bankAccountId = creditWalletModel.bankAccountId.toString();
        bankTransaction.amount = creditWalletModel.amount;
        const userAsString = localStorage.getItem('user');
        const user = JSON.parse(userAsString);

        console.log(bankTransaction);
        return this.http.post(`${environment.apiUrl}/user/profile/${user.userId}/withdraw`, bankTransaction);
    }

        // delete a bank account
    deleteBankAccount(bankAccountId: string) {
        const userAsString = localStorage.getItem('user');
        const user = JSON.parse(userAsString);
        // const bAccAsString = localStorage.getItem('bankAccount');
        // const bAcc = JSON.parse(bAccAsString);
        return this.http.delete(`${environment.apiUrl}/user/profile/${user.userId}/removeBankAccount/${bankAccountId}`);
    }       
    
        // all bank transactions of a user
    getAllBankTransactionsByUser() {
        const userAsString = localStorage.getItem('user');
        const user = JSON.parse(userAsString);
        return this.http.get<User[]>(`${environment.apiUrl}/user/profile/${user.userId}`);
    }

        // add a bank account
    addBankAccount(bankAccount: BankAccount) {
        const userAsString = localStorage.getItem('user');
        const user = JSON.parse(userAsString);
        return this.http.put(`${environment.apiUrl}/user/profile/${user.userId}/addBankAccount`, bankAccount);
    }
}