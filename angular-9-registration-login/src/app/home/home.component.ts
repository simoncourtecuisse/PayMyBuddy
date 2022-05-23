import { Component, OnInit } from '@angular/core';

import { User } from '@app/_models';
import { AccountService } from '@app/_services';
import { TokenStorageService } from '@app/_services/token-storage.service';

@Component({ templateUrl: 'home.component.html' })
export class HomeComponent implements OnInit {
    // user: User;
    user: any;

    constructor(
        private token: TokenStorageService,
        private accountService: AccountService) {
        // this.user = this.accountService.userValue;
    }

    ngOnInit() {
        console.log(this.user);
        this.user = this.token.getUser;
    }
}