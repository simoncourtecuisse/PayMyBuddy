import { Component } from '@angular/core';

//import { User } from '@app/model';
import { AccountService } from '@app/___services';
import { User } from '../models/user';

@Component({ templateUrl: 'home.component.html' })
export class HomeComponent {
    user: User;

    constructor(private accountService: AccountService) {
        this.user = this.accountService.userValue;
    }
}