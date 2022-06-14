import { Component, OnInit } from '@angular/core';
import { AccountService } from '@app/_services';
import { first } from 'rxjs/operators';

@Component({
  templateUrl: './board-admin.component.html'
})
export class BoardAdminComponent implements OnInit {
  users = null;
  transfers = null;
  content = '';
  constructor( private accountService: AccountService ) { }
  
  ngOnInit() {
    this.accountService.getAllUser()
      .pipe(first())
      .subscribe(users => this.users = users);

    this.accountService.getAllTransactions()
      .pipe(first())
      .subscribe(transfers => this.transfers = transfers);
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