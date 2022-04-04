import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { AccountService } from '@app/_services';
import { ActivatedRoute } from '@angular/router';

@Component({ templateUrl: 'list-transfers.component.html' })
export class ListTransfersComponent implements OnInit {
    transfers = null;
    id: string;
    


    constructor(
        private accountService: AccountService,
        private route: ActivatedRoute,) {}

    ngOnInit() {
        this.id = this.route.snapshot.params['id'];
        this.accountService.getAllTransfersByUserId(this.id)
            .pipe(first())
            .subscribe(transfers => this.transfers = transfers);
    }

}