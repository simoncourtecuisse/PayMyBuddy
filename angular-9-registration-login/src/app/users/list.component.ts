import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { User } from '@app/_models';
import { AccountService } from '@app/_services';

@Component({ templateUrl: 'list.component.html' })
export class ListComponent implements OnInit {
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
        const bankAccount = this.bankAccounts.find(x => x.id === id);
        bankAccount.isDeleting = true;
        this.accountService.deleteBankAccount(id)
            .pipe(first())
            .subscribe(() => {
                this.bankAccounts = this.bankAccounts.filter(x => x.id !== id) 
            });
    }
}