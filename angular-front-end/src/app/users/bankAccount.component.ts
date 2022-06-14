import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { User } from '@app/_models';
import { AccountService } from '@app/_services';

@Component({ templateUrl: 'bankAccount.component.html' })
export class BankAccountComponent implements OnInit {
    users = null;
    user: User;
    bankAccounts = null;

    constructor(private accountService: AccountService) {
        this.user = this.accountService.userValue;
    }

    ngOnInit() {
        this.accountService.getAllBankAccountByUserId()
            .pipe(first())
            .subscribe(bankAccounts => this.bankAccounts = bankAccounts);
    }

    deleteBankAccount(id: string) {
        console.log(id);
        const bankAccount = this.bankAccounts.find(x => x.bankAccountId === id);
        bankAccount.isDeleting = true;
        console.log(this.bankAccounts);
        this.accountService.deleteBankAccount(id)
            .pipe(first())
            .subscribe(() => {
                this.bankAccounts = this.bankAccounts.filter(x => x.bankAccountId !== id) 
            });
    }
}