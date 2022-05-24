import { Component, OnInit } from '@angular/core';

import { User } from '@app/_models';
import { AccountService } from '@app/_services';
// import { AuthService } from '@app/_services/auth.service';

@Component({ templateUrl: 'home.component.html' })
export class HomeComponent implements OnInit {
    user: any;

    constructor(
        //private authService: AuthService,
        private accountService: AccountService) {
        this.user = this.accountService.userValue;
    }
    ngOnInit() {
        // console.log(this.user.userId);
        // console.log(this.user.email);
        // console.log(this.user.firstName);
        // this.accountService.getById(this.user.userId);
        // this.accountService.getByEmail(this.user.email);
        this.accountService.getUserInfoByEmail(this.user.email);
        console.log(this.accountService.userValue);
        console.log(this.user.email);
        console.log(this.user.userId);
    }
}