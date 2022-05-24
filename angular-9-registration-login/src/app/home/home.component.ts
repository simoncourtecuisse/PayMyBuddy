import { Component, OnInit } from '@angular/core';

import { User } from '@app/_models';
import { AccountService } from '@app/_services';
import { AuthService } from '@app/_services/auth.service';
import { TokenStorageService } from '@app/_services/token-storage.service';

@Component({ templateUrl: 'home.component.html' })
export class HomeComponent  {
     user: User;
    // user: any;

    constructor(
        private token: TokenStorageService,
        private accountService: AccountService,
        private authService: AuthService) {
        this.user = this.authService.userValue;
        // this.user = this.accountService.userValue;
    }

    // ngOnInit() {

    //     console.log(this.user);
    //     // this.user = this.token.getUser;
    // }
}