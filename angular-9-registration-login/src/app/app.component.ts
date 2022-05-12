import { Component, OnInit } from '@angular/core';

import { AccountService } from './_services';
import { User } from './_models';
import { TokenStorageService } from './_services/token-storage.service';

@Component({ selector: 'app', templateUrl: 'app.component.html' })
export class AppComponent implements OnInit {
    private roles: string[];
    isLoggedIn = false;
    showAdminBoard = false;
    email: string;
    user: User;

    constructor(
        private tokenStorageService: TokenStorageService,
        private accountService: AccountService) {
        this.accountService.user.subscribe(x => this.user = x);
    }

    ngOnInit() {
        this.isLoggedIn = !!this.tokenStorageService.getToken();
        if (this.isLoggedIn) {
            const user = this.tokenStorageService.getUser();
            this.roles = user.roles;
            this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
            this.email = user.email;
        }
    }

    logout() {
        this.accountService.logout();
        this.tokenStorageService.signOut();
        window.location.reload();
    }
}