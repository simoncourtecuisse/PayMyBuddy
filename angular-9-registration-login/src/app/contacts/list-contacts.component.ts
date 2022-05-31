import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { AccountService } from '@app/_services';

@Component({ templateUrl: 'list-contacts.component.html' })
export class ListContactsComponent implements OnInit {
    users = null;
    contacts = null;

    constructor(private accountService: AccountService) {}

    ngOnInit() {

        this.accountService.getAllFriendsByUserId()
            .pipe(first())
            .subscribe(contacts => this.contacts = contacts);
    }

    deleteFriend(friendUserId: string) {
        console.log(friendUserId);
        const contact = this.contacts.find(x => x.userId === friendUserId);
        contact.isDeleting = true;
        console.log(this.contacts);
        console.log(contact.userId);
        this.accountService.deleteFriend(friendUserId)
            .pipe(first())
            .subscribe(() => {
                this.contacts = this.contacts.filter(x => x.userId !== friendUserId) 
            });
        
    }
}