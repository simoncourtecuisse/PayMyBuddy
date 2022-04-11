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

    deleteFriend(id: string) {
        const contact = this.contacts.find(x => x.id === id);
        contact.isDeleting = true;
        this.accountService.deleteFriend(id)
            .pipe(first())
            .subscribe(() => {
                this.contacts = this.contacts.filter(x => x.id !== id) 
            });
    }
}

// @Component({ templateUrl: 'list-contacts.component.html' })
// export class ListContactsComponent implements OnInit {
//     users = null;

//     constructor(private accountService: AccountService) {}

//     ngOnInit() {

//         this.accountService.getAllFriendsByUserId()
//             .pipe(first())
//             .subscribe(users => this.users = users);
//     }

//     deleteFriend(userFriendId: string) {
//         const userFriend = this.users.find(x => x.userFriendId === userFriendId);
//         userFriend.isDeleting = true;
//         this.accountService.deleteFriend(userFriendId)
//             .pipe(first())
//             .subscribe(() => {
//                 this.users = this.users.filter(x => x.userFriendId !== userFriendId) 
//             });
//     }
//}