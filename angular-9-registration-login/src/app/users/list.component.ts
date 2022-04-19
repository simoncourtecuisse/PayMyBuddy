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

    deleteBankAccount(bankAccountId: string) {
        console.log(bankAccountId);
        const bankAccount = this.bankAccounts.find(x => x.userId === bankAccountId);
        bankAccount.isDeleting = true;
        console.log(this.bankAccounts);
        this.accountService.deleteBankAccount(bankAccountId)
            .pipe(first())
            .subscribe(() => {
                this.bankAccounts = this.bankAccounts.filter(x => x.userId !== bankAccountId) 
            });
    }
}