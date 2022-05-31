import { Component, OnInit } from '@angular/core';
import { AccountService } from '@app/_services';
//import { UserRoleService } from '@app/_services/userRole.service';
import { first } from 'rxjs/operators';

@Component({
  templateUrl: './board-admin.component.html'
})
export class BoardAdminComponent implements OnInit {
  users = null;
  transfers = null;
  bankAccounts = null;
  bankTransactions = null;
  content = '';
  constructor(
    private accountService: AccountService,
    // private userRoleService: UserRoleService
  ) { }
  ngOnInit() {
    // this.userRoleService.getAdminBoard().subscribe(
    //   data => {
    //     this.content = data;
    //   },
    //   err => {
    //     this.content = JSON.parse(err.error).message;
    //   }
    // );

    this.accountService.getAllUser()
      .pipe(first())
      .subscribe(users => this.users = users);

    this.accountService.getAllTransactions()
      .pipe(first())
      .subscribe(transfers => this.transfers = transfers);

    // this.accountService.getAllBankAccounts()
    //   .pipe(first())
    //   .subscribe(bankAccounts => this.bankAccounts = bankAccounts);

    // this.accountService.getAllBankTransactions()
    //   .pipe(first())
    //   .subscribe(bankTransactions => this.bankTransactions = bankTransactions);
  }

  deleteUser(deleteId: string) {
    const user = this.users.find(x => x.userId !== deleteId);
    user.isDeleting = true;
    console.log(user.userId);
    this.accountService.deleteUser(deleteId)
      .pipe(first())
      .subscribe(() => {
        this.users = this.users.filter(x => x.userId !== deleteId)
      });
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

  deleteTransaction(id: string) {
    console.log(id);
    const transfer = this.transfers.find(x => x.transactionId === id);
    transfer.isDeleting = true;
    console.log(this.transfers);
    this.accountService.deleteTransaction(id)
      .pipe(first())
      .subscribe(() => {
        this.transfers = this.transfers.filter(x => x.transactionId !== id)
      });
  }
}