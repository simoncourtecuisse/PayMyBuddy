import { Component, OnInit } from '@angular/core';

import { User } from '@app/_models';
import { AccountService } from '@app/_services';
import { AuthService } from '@app/_services/auth.service';

@Component({ templateUrl: 'home.component.html' })
export class HomeComponent implements OnInit {
    user: User;

    constructor(
        private authService: AuthService,
        private accountService: AccountService) {
        this.user = this.authService.userValue;
    }
    ngOnInit() {
        console.log(this.user.userId);
        console.log(this.user.email);
        console.log(this.user.firstName);
        this.accountService.getById(this.user.userId);
        this.accountService.getByEmail(this.user.email);
    }
}