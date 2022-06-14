import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { AccountService } from '@app/_services';


@Component({ templateUrl: 'list-transfers.component.html' })
export class ListTransfersComponent implements OnInit {
    transfers = null;
    
    constructor(
        private accountService: AccountService,
       ) {}

    ngOnInit() {
        this.accountService.getAllTransfersByUserId()
            .pipe(first())
            .subscribe(transfers => this.transfers = transfers);
    }

}