import { Component, NgZone, OnInit, ChangeDetectorRef } from '@angular/core';
import { first, map } from 'rxjs/operators';


import { User } from '@app/_models';
import { AccountService } from '@app/_services';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

@Component({ templateUrl: 'list.component.html' })
export class ListComponent implements OnInit {
    users = null;
    user: any;
    bankAccounts = null;
    bankTransactions = null;
    

    constructor(
        private cdRef: ChangeDetectorRef,
        private ngZone: NgZone,
        private accountService: AccountService) {
        this.user = this.accountService.userValue;
        this.user.walletBalance =  this.accountService.userValue.walletBalance;
    }

    
    // getUpdateWallet(): void {
    //     this.accountService.getById(this.user.id).subscribe(user => {
    //         user = this.user;
    //         this.user=this.accountService.getById(this.user.id)
    //         this.cdRef.detectChanges();
    //     });
    // }
    

    ngOnInit() {
        
       //this.getUpdateWallet();
        this.accountService.getWalletBalanceUserById()
              .pipe(first())
              .subscribe(users => this.users = this.users); 

        console.log(this.accountService.userValue.walletBalance);
        // this.ngZone.run(() =>{
        //     this.accountService.getById(this.user.id)
        // });
        this.accountService.getAllBankTransactionsByUser()
            .pipe(first())
            .subscribe(bankTransactions => this.bankTransactions = bankTransactions);
    }

    // deleteBankAccount(id: string) {
    //     console.log(id);
    //     const bankAccount = this.bankAccounts.find(x => x.bankAccountId === id);
    //     bankAccount.isDeleting = true;
    //     console.log(this.bankAccounts);
    //     this.accountService.deleteBankAccount(id)
    //         .pipe(first())
    //         .subscribe(() => {
    //             this.bankAccounts = this.bankAccounts.filter(x => x.bankAccountId !== id) 
    //         });
    // }
}