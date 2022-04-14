import { User } from '@app/_models';
import { AccountService } from '@app/_services';
import { Component } from '@angular/core';

@Component({ templateUrl: 'layout.component.html' })
export class LayoutComponent { 
    user: User;
    constructor(private accountService: AccountService) {
        this.user = this.accountService.userValue;
    }
}